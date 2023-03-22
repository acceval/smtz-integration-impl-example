package converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.smarttradzt.integration.spec.converter.InvoiceDataConverter;
import com.smarttradzt.integration.spec.model.InvoiceRequest;
import com.smarttradzt.integration.spec.model.InvoiceResponse;

import getinvoiceshandler.Divisions;
import getinvoiceshandler.GetInvoiceInput;
import getinvoiceshandler.GetInvoiceOutput;
import getinvoiceshandler.GetInvoicesResponse;
import getinvoiceshandler.SalesOrganizations;

public class KPMGInvoiceConverter implements InvoiceDataConverter<GetInvoiceInput, GetInvoicesResponse> {

	@Override
	public GetInvoiceInput convertOutbound(InvoiceRequest request) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		List<SalesOrganizations> sapSalesOrgs = new ArrayList<>();

		for (String so : request.getSalesOrganisations()) {
			SalesOrganizations sapSo = new SalesOrganizations();
			sapSo.setSalesOrganization(so);
			sapSalesOrgs.add(sapSo);
		}

		List<Divisions> sapDivisions = new ArrayList<>();

		for (String div : request.getDivisions()) {
			Divisions sapSd = new Divisions();
			sapSd.setDivision(div);
			sapDivisions.add(sapSd);
		}

		GetInvoiceInput input = new GetInvoiceInput();
		input.setDateFrom(request.getDateFrom().format(formatter));
		input.setDateTo(request.getDateTo().format(formatter));

		input.getSalesOrganizations().addAll(sapSalesOrgs);
		input.getDivisions().addAll(sapDivisions);
		return input;
	}

	@Override
	public InvoiceResponse convertInbound(GetInvoicesResponse output) {
		InvoiceResponse resp = new InvoiceResponse();
		resp.setItems(new ArrayList<>());

		for (GetInvoiceOutput out : output.getInvoices()) {
			if (out == null)
				continue;
			resp.getItems().add(buildItem(out));
		}

		return resp;
	}

	private InvoiceResponse.Item buildItem(GetInvoiceOutput output) {
		InvoiceResponse.Item item = new InvoiceResponse.Item();

		item.setEntryTime(output.getEntryTime());
		item.setEntryDate(convertDate(output.getEntryDate()));
		item.setSalesOrganization(output.getSalesOrganization());
		item.setDistributionChannel(output.getDistributionChannel());
		item.setDivision(output.getDivision());
		item.setSalesOffice(output.getSalesOffice());
		item.setBillingDocument(output.getBillingDocument());
		item.setBillingItem(output.getBillingItem());
		item.setBillingType(output.getBillingType());
		item.setPricingDate(convertDate(output.getPricingDate()));
		item.setSoldtoParty(output.getSoldtoParty());
		item.setShipTo(output.getShipTo());
		item.setBillTo(output.getBillTo());
		item.setPayer(output.getPayer());
		item.setCountryDestination(output.getCountryDestination());
		item.setTermsPaymentKey(output.getTermsPaymentKey());
		item.setDocumentCurrency(output.getDocumentCurrency());
		item.setExchangeRateFIPostings(convertExchangeRate(output.getExchangeRateFIPostings()));
		item.setActualInvoicedQuantity(convertNumber(output.getActualInvoicedQuantity()));
		item.setSalesUnit(output.getSalesUnit());
		item.setMaterialNumber(StringUtils.stripStart(output.getMaterialNumber(), "0"));
		item.setPlant(output.getPlant());
		item.setSalesGroup(output.getSalesGroup());
		item.setInvoicePrice(convertNumber(output.getInvoicePrice()));
		item.setConditionUnitInDocument(output.getConditionUnitInDocument());
		item.setInvoicePriceQuantity(convertNumber(output.getInvoicePriceQuantity()));
		item.setInvoicePriceCurrency(output.getInvoicePriceCurrency());
		item.setReferenceDocumentID(output.getReferenceDocumentID());
		item.setReferenceDocumentItemID(output.getReferenceDocumentItemID());
		item.setRequestedDeliveryDate(convertDate(output.getRequestedDeliveryDate()));
		item.setBillingDate(convertDate(output.getBillingDate()));
		item.setShippingConditions(output.getShippingConditions());
		item.setShipToCity(output.getShipToCity());
		item.setIncoterms(output.getIncoterms());
		item.setDestinationPort(output.getDestinationPort());
		item.setSalesOrderDate(convertDate(output.getSalesOrderDate()));
		item.setOriginPort(output.getOriginPort());
		item.setRegionPlant(output.getRegionPlant());
		item.setSalesOrderCreationDate(convertDate(output.getSalesOrderCreationDate()));
		item.setSalesContractNo(output.getSalesContractNo());
		item.setDocumentNo(output.getDocumentNo());
		item.setDocumentItemNo(StringUtils.stripStart(output.getDocumentItemNo(), "0"));
		item.setEta(convertDate(output.getETA()));
		item.setStorageLocation(output.getStorageLocation());
		item.setDocumentQuantity(convertNumber(output.getDocumentQuantity()));
		item.setDocumentValue(convertNumber(output.getDocumentValue()));
		item.setBillingItemValue(convertNumber(output.getBillingItemValue()));
		item.setPalletize(output.getPalletize());
		item.setSdDocumentCategory(output.getSDDocumentCategory());
		item.setCancelledBillingDocNo(output.getCancelledBillingDocNo());
		item.setMaterialEntered(output.getMaterialEntered());
		item.setConditionExchangeRate(convertExchangeRate(output.getConditionExchangeRate()));
		item.setNetWeight(output.getNetWeight());
		item.setPurchaseOrderDate(convertDate(output.getPurchaseOrderDate()));
		item.setPriceListType(output.getPriceListType());
		item.setGoodsMovementDate(convertDate(output.getGoodsMovementDate()));
		item.setSalesOrderNo(output.getSalesOrderNo());
		item.setTaxAmount(convertNumber(output.getTaxAmount()));
		item.setTaxCurrency(output.getTaxCurrency());
		item.setEndOfLoadingDate(convertDate(output.getEndOfLoadingDate()));
		item.setItemCategory(output.getItemCategory());
		item.setOrderReason(output.getOrderReason());

		return item;
	}

	LocalDate convertDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		if (StringUtils.isNotBlank(date)) {
			try {
				return LocalDate.parse(date, formatter);
			} catch (DateTimeParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	Double convertExchangeRate(String value) {
		if (StringUtils.isNotBlank(value)) {

			// if exchange rate is start with -, then is 1 divided by the value
			double excRate = 0;
			try {
				excRate = Double.parseDouble(value);

				if (excRate < 0) {
					excRate = -1 / excRate;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

			return excRate;
		} else {
			return null;
		}
	}

	Double convertNumber(String value) {
		if (StringUtils.isNotBlank(value)) {
			try {
				return Double.parseDouble(value);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			return 0.0;
		} else {
			return null;
		}
	}

}
