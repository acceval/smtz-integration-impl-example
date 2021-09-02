package com.acceval.core.cache.processor;

import com.acceval.core.cache.CacheException;
import com.acceval.core.cache.impl.ExchangeRateCache;
import com.acceval.core.cache.impl.MasterDataCache;
import com.acceval.core.cache.model.Currency;
import com.acceval.core.cache.model.ExchangeRate;
import com.acceval.core.microservice.ObjectNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service("cacheCurrencyConversionUtil")
@ConditionalOnProperty(name = "microservice.cache", havingValue = "true")
public class CurrencyConversionUtil {

	// getConvertedAmount if this is called from Contract or Quote, pass the
	// pricing date as the effective date. This is different from the validity
	// dates.
	// getConvertedAmount if this is called from Margin Analytics, pass the
	// exchange rate effective date available from calendar date selector.
	// getConvertedAmount if there is no exchange rate type, then it reads the
	// default value from system configuration
	// getConvertedAmount if there is no exchange rate publication, then it
	// reads the default value from system configuration
	public final static String EXCHANGE_RATE_TYPE_ID = "exchangeRateTypeID";
	public final static String EXCHANGE_RATE_PUBLICATION_ID = "exchangeRatePublicationID";
	public final static String EXCHANGE_RATE_RETRIEVER = "exchangeRateRetriever";
	private static Logger logger = LoggerFactory.getLogger(CurrencyConversionUtil.class);

	@Autowired
    private ExchangeRateCache exchangeRateCache;

	@Autowired
    private MasterDataCache masterDataCache;

//	@Autowired
//	private CurrencyService currencyService;
//	@Autowired
//	private ExchangeRateService exchangeRateService;

	public float getExchangeRate(long fromCurrencyID, long toCurrencyID, LocalDateTime effectiveDate,
			Long exchangeRateTypeID) {
		
		float fExchangeRate = 1;
		
		Collection result = getExchangeRates(fromCurrencyID, toCurrencyID, effectiveDate, exchangeRateTypeID);
		if (result.isEmpty() == false) {
			ExchangeRate exchangeRateObj = (ExchangeRate) result.iterator().next();
			fExchangeRate = zeroIfNulll(exchangeRateObj.getRate()).floatValue();
		}
		return fExchangeRate;
	}

	public Collection getExchangeRates(long fromCurrencyID, long toCurrencyID, LocalDateTime effectiveDate,
			Long exchangeRateTypeID) {
		Collection<ExchangeRate> rates = exchangeRateCache.getExchangeRates(fromCurrencyID, toCurrencyID,
				effectiveDate, exchangeRateTypeID);

		return rates;
	}

	public Double getConvertedAmount(double amountToConvert, long fromCurrencyID, long toCurrencyID,
			LocalDateTime effectiveDate, Long exchangeRateTypeID) {
		
		if(fromCurrencyID == toCurrencyID) {
			return amountToConvert;
		}
		double convertedAmount = amountToConvert;
		
		// Multiplication Logic, FROM_CURRENCY exists
		Collection result = getExchangeRates(fromCurrencyID, toCurrencyID, effectiveDate, exchangeRateTypeID);

		if (!result.isEmpty()) {

			logger.info("CurrencyConversionUtil", "Multiplication Logic");
			// Only get the "latest effective date record"
			ExchangeRate exchangeRateObj = (ExchangeRate) result.iterator().next(); 
			
			float fExchangeRate = zeroIfNulll(exchangeRateObj.getRate()).floatValue();
			convertedAmount = amountToConvert * fExchangeRate;
			logger.info("Exchange Rate EffectiveDate: " + exchangeRateObj.getEffectiveDate());			

		} else {
			// Division Logic, because FROM_CURRENCY doesn't exist but it is in
			// TO_CURRENCY
			result = getExchangeRates(toCurrencyID, fromCurrencyID, effectiveDate, exchangeRateTypeID);
			if (!result.isEmpty()) {
				// Only get the "latest effective date record"
				ExchangeRate exchangeRateObj = (ExchangeRate) result.iterator().next();
				float fExchangeRate = zeroIfNulll(exchangeRateObj.getRate()).floatValue();
				logger.info("CurrencyConversionUtil", "Division Logic");
				convertedAmount = amountToConvert / fExchangeRate;				
			} else {
				String fromText = "";
				String toText = "";
				
				try {
					Currency fromCurrency = masterDataCache.getCurrencyByID(fromCurrencyID);
					fromText = fromCurrency.getCode();
					Currency toCurrency = masterDataCache.getCurrencyByID(toCurrencyID);
					toText = toCurrency.getCode();
				} catch (ObjectNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				throw new CacheException(this.getClass(), "Exchange Rate [" + fromText
					+ " to " + toText + "] or vice versa is not found.");
			}

		}

		return convertedAmount;
	}

	public long getCurrencyIDByCode(String currencyCode) {
		Currency currency = null;
		try {
			currency = masterDataCache.getCurrencyByCode(currencyCode);
		} catch (ObjectNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (currency != null) {
			return currency.getCurrencyID();
		}
		return 0;
	}

	public Double zeroIfNulll(Double number) {
		if (number == null)
			return new Double(0);
		return number;
	}

}
