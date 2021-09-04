package com.acceval.core.cache.impl;

import com.acceval.core.cache.CacheIF;
import com.acceval.core.cache.CacheInfo;
import com.acceval.core.cache.HazelcastCacheInstance;
import com.acceval.core.cache.model.Currency;
import com.acceval.core.cache.model.GlobalUomConversion;
import com.acceval.core.cache.model.SkuProductAltUom;
import com.acceval.core.cache.model.Uom;
import com.acceval.core.microservice.ObjectNotFoundException;
import com.acceval.core.security.PrincipalUtil;
import com.hazelcast.core.ReplicatedMap;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Cache structure
 *
 * MASTERDATA_CACHE|[Company ID]|ALL_UOM                    > DEFAULT > All UOM
 *                              |ALL_CURRENCY               > DEFAULT > All Currency
 *                              |ALL_GLOBAL_UOM_CONVERSION  > DEFAULT > All Global Uom Conversion
 *                              |ALL_SKU_PRODUCT_ALT_UOM    > DEFAULT > All SKU Product Alt Uom
 *                              |PRODUCT_BASEUOM            > [PRODUCT_ID] > Base UOM
 */
@Component
@ConditionalOnProperty(name = "microservice.cache", havingValue = "true")
public class MasterDataCache implements CacheIF {
    private static final Logger logger = LoggerFactory.getLogger(MasterDataCache.class);

    public final static String CACHE_NAME = "MASTERDATA_CACHE";

    public final static String KEY_ALL_UOM = "ALL_UOM";
    public final static String KEY_ALL_CURRENCY = "ALL_CURRENCY";
    public final static String KEY_ALL_GLOBAL_UOM_CONVERSION = "ALL_GLOBAL_UOM_CONVERSION";
    public final static String KEY_ALL_SKU_PRODUCT_ALT_UOM = "ALL_SKU_PRODUCT_ALT_UOM";
    public final static String KEY_PRODUCT_BASEUOM = "PRODUCT_BASEUOM";

    public final static String KEY_DEFAULT = "DEFAULT";

    @Autowired
    private HazelcastCacheInstance hazelcastCacheInstance;


    private ReplicatedMap<String, Object> getTopMap(String companyID, String code) {
        String key = CACHE_NAME + "|" + companyID + "|" + code;
        return this.hazelcastCacheInstance.getHazelcastInstance().getReplicatedMap(key);
    }

    /**
     * Global Uom Conversion Cache
     */
    public List<GlobalUomConversion> getAllGlobalUomConversion() {
        String companyID = PrincipalUtil.getCompanyID().toString();

        return (List<GlobalUomConversion>) getTopMap(companyID, KEY_ALL_GLOBAL_UOM_CONVERSION).get(KEY_DEFAULT);
    }

    public void refreshAllGlobalUomConversions(String companyID, List<GlobalUomConversion> conversions) {
        getTopMap(companyID, KEY_ALL_GLOBAL_UOM_CONVERSION).put(KEY_DEFAULT, conversions);
    }

    public List<GlobalUomConversion> getGlobalUomConversion(long fromUomID, long toUomID) {
        List<GlobalUomConversion> rates = getAllGlobalUomConversion();

        if (CollectionUtils.isEmpty(rates)) return Collections.emptyList();

        return rates.stream().filter(data -> {
            return data.getFromUom().getUomID() == fromUomID &&
                    data.getToUom().getUomID() == toUomID &&
                    data.getSalesDivisionID() == 0;
        }).collect(Collectors.toList());
    }

    public List<GlobalUomConversion> getGlobalUomConversion(long fromUomID, long toUomID, long salesDivisionID) {
        List<GlobalUomConversion> rates = getAllGlobalUomConversion();

        if (CollectionUtils.isEmpty(rates)) return Collections.emptyList();

        return rates.stream().filter(data -> {
            return data.getFromUom().getUomID() == fromUomID &&
                    data.getToUom().getUomID() == toUomID &&
                    data.getSalesDivisionID() == salesDivisionID;
        }).collect(Collectors.toList());
    }

    /**
     * SKU Product Alt Uom Cache
     * @return
     */
    public List<SkuProductAltUom> getAllSKUProductAltUom() {
        String companyID = PrincipalUtil.getCompanyID().toString();

        return (List<SkuProductAltUom>) getTopMap(companyID, KEY_ALL_SKU_PRODUCT_ALT_UOM).get(KEY_DEFAULT);
    }

    public void refreshAllSKUProductAltUom(String companyID, List<SkuProductAltUom> conversions) {
        getTopMap(companyID, KEY_ALL_SKU_PRODUCT_ALT_UOM).put(KEY_DEFAULT, conversions);
    }

    public List<SkuProductAltUom> getSKUAlternateUom(long productID, long fromUomID, long toUomID) {

        List<SkuProductAltUom> rates = getAllSKUProductAltUom();

        if (CollectionUtils.isEmpty(rates)) return Collections.emptyList();

        return rates.stream().filter(data -> {
            return data.getAlternateFromUom().getUomID() == fromUomID &&
                    data.getAlternateToUom().getUomID() == toUomID &&
                    data.getSkuID() == productID;
        }).collect(Collectors.toList());
    }


    /**
     * Product Base Uom
     */
    public void refreshProductBaseUom(String companyID, long productID, Uom baseUom) {
        getTopMap(companyID, KEY_PRODUCT_BASEUOM).put(String.valueOf(productID), baseUom);
    }

    public Uom getProductBaseUom(long productID) throws ObjectNotFoundException {
        String companyID = PrincipalUtil.getCompanyID().toString();

        Uom baseUom = (Uom) getTopMap(companyID, KEY_PRODUCT_BASEUOM).get(String.valueOf(productID));

        if (baseUom != null) return baseUom;

        throw new ObjectNotFoundException("Product [" + productID + "] not found.");
    }

    /**
     * UOM cache
      */
    public void refreshAllUoms(String companyID, List<Uom> uoms) {
        getTopMap(companyID, KEY_ALL_UOM).put(KEY_DEFAULT, uoms);
    }

    public List<Uom> getAllUoms() {
        String companyID = PrincipalUtil.getCompanyID().toString();

        return (List<Uom>) getTopMap(companyID, KEY_ALL_UOM).get(KEY_DEFAULT);
    }

    public Uom getUomByCode(String code) throws ObjectNotFoundException {

        List<Uom> uoms = getAllUoms();

        if (uoms != null && !uoms.isEmpty()) {

            Optional<Uom> temp = uoms.stream().filter(uom -> uom.getCode().equals(code)).findFirst();

            if (temp.isPresent()) return temp.get();
        }

        throw new ObjectNotFoundException("UOM [" + code + "] not found.");
    }

    /**
     * Currency cache
     */
    public void refreshAllCurrencies(String companyID, List<Currency> currencies) {
        getTopMap(companyID, KEY_ALL_CURRENCY).put(KEY_DEFAULT, currencies);
    }

    public List<Currency> getAllCurrencies() {
        String companyID = PrincipalUtil.getCompanyID().toString();

        return (List<Currency>) getTopMap(companyID, KEY_ALL_CURRENCY).get(KEY_DEFAULT);
    }

    public Currency getCurrencyByCode(String code) throws ObjectNotFoundException {
        List<Currency> currencies = getAllCurrencies();

        if (currencies != null && !currencies.isEmpty()) {

            Optional<Currency> temp = currencies.stream().filter(currency -> currency.getCode().equals(code)).findFirst();

            if (temp.isPresent()) return temp.get();
        }

        throw new ObjectNotFoundException("Currency [" + code + "] not found.");
    }

    public Currency getCurrencyByID(Long id) throws ObjectNotFoundException {
        List<Currency> currencies = getAllCurrencies();

        if (currencies != null && !currencies.isEmpty()) {

            Optional<Currency> temp = currencies.stream().filter(currency -> currency.getCurrencyID() == id).findFirst();

            if (temp.isPresent()) return temp.get();
        }

        throw new ObjectNotFoundException("Currency [" + id + "] not found.");
    }

    @Override
    public void clearAll(String companyID) {
        // TODO dangerous to be clear all first then put in the new data.. cause cache might be blank for few seconds if data is huge
    }

    @Override
    public CacheInfo getCacheInfo(String companyID) {
        return null;
    }

    public String test() {

        StringBuffer buffer = new StringBuffer();
        List<Currency> temp = this.getAllCurrencies();

        if (temp == null) {
            buffer.append("Currency : No currency found in cache.");
            buffer.append("\n");
        } else {
            buffer.append("Currency : Total currency : " + temp.size());
            buffer.append("\n");
        }

        String currencyCode = "RUP";
        try {
            buffer.append("Currency : Currency By Code ["+currencyCode+"] : " + getCurrencyByCode(currencyCode).getName());
            buffer.append("\n");
        } catch (ObjectNotFoundException e) {
            buffer.append("Currency : Currency By Code ["+currencyCode+"] : " +e.getMessage());
            buffer.append("\n");
        }

        currencyCode = "USD";
        Currency currency = null;
        try {
            currency = getCurrencyByCode(currencyCode);
            buffer.append("Currency : Currency By Code ["+currencyCode+"] : " + currency.getName());
            buffer.append("\n");
        } catch (ObjectNotFoundException e) {
            buffer.append("Currency : Currency By Code ["+currencyCode+"] : " +e.getMessage());
            buffer.append("\n");
        }

        List<Uom> temp2 = this.getAllUoms();

        if (temp2 == null) {
            buffer.append("Uom : No uom found in cache.");
            buffer.append("\n");
        } else {
            buffer.append("Uom : Total uom : " + temp2.size());
            buffer.append("\n");
        }

        String uomCode = "TTT";
        try {
            buffer.append("Uom : Uom By Code ["+uomCode+"] : " + getUomByCode(uomCode).getName());
            buffer.append("\n");
        } catch (ObjectNotFoundException e) {
            buffer.append("Uom : Uom By Code ["+uomCode+"] : " +e.getMessage());
            buffer.append("\n");
        }

        uomCode = "MT";
        Uom uom = null;
        try {
            uom = getUomByCode(uomCode);
            buffer.append("Uom : Uom By Code ["+uomCode+"] : " + uom.getName());
            buffer.append("\n");
        } catch (ObjectNotFoundException e) {
            buffer.append("Uom : Uom By Code ["+uomCode+"] : " +e.getMessage());
            buffer.append("\n");
        }

        List<GlobalUomConversion> temp3 = this.getAllGlobalUomConversion();

        if (temp3 == null) {
            buffer.append("Global Uom Conversion : No global uom conversion found in cache.");
            buffer.append("\n");
        } else {
            buffer.append("Global Uom Conversion : Total global uom conversion : " + temp3.size());
            buffer.append("\n");
        }

        try {

            Uom mt = this.getUomByCode("MT");
            Uom mbt = this.getUomByCode("MBT");

            temp3 = this.getGlobalUomConversion(mt.getUomID(), mbt.getUomID());

            buffer.append("Glboal Uom Conversion : Get : Total " + temp3.size());
            buffer.append("\n");
            if (!temp3.isEmpty()) {
                GlobalUomConversion rate = temp3.get(0);

                buffer.append("Glboal Uom Conversion : Get : First : " + rate.getConversionFactor());
                buffer.append("\n");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        try {

            Uom mt = this.getUomByCode("MT");
            Uom mbt = this.getUomByCode("MBT");

            temp3 = this.getGlobalUomConversion(mt.getUomID(), mbt.getUomID(), 202101291000003l);

            buffer.append("Glboal Uom Conversion : Get By Sales Division : Total " + temp3.size());
            buffer.append("\n");
            if (!temp3.isEmpty()) {
                GlobalUomConversion rate = temp3.get(0);

                buffer.append("Glboal Uom Conversion : Get By Sales Division : First : " + rate.getConversionFactor());
                buffer.append("\n");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        List<SkuProductAltUom> temp4 = this.getAllSKUProductAltUom();

        if (temp4 == null) {
            buffer.append("Sku Product Alt Uom : No alt uom found in cache.");
            buffer.append("\n");
        } else {
            buffer.append("Sku Product Alt Uom : Total alt uom : " + temp4.size());
            buffer.append("\n");
        }

        try {

            Uom from = this.getUomByCode("DR");
            Uom to = this.getUomByCode("KG");

            temp4 = this.getSKUAlternateUom(202103230000493l, from.getUomID(), to.getUomID());

            buffer.append("Sku Product Alt Uom : Get : Total " + temp4.size());
            buffer.append("\n");
            if (!temp4.isEmpty()) {
                SkuProductAltUom rate = temp4.get(0);

                buffer.append("Sku Product Alt Uom : Get : First : " + rate.getConversionFactor());
                buffer.append("\n");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        try {
//            buffer.append("Product Base Uom : Get : " + this.getProductBaseUom(202103230000493l).getName());
            buffer.append("Product Base Uom : Get : " + this.getProductBaseUom(202109020001893l).getName());
            buffer.append("\n");
        } catch (Throwable t) {
            t.printStackTrace();
        }

        try {
            buffer.append("Product Base Uom : Get : " + this.getProductBaseUom(123).getName());
            buffer.append("\n");
        } catch (Throwable t) {
            buffer.append("Product Base Uom : Get : Not found.");
            buffer.append("\n");
        }

        return buffer.toString();
    }
}