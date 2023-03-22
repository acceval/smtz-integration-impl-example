package converter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import com.smarttradzt.integration.spec.converter.SalesContractCreateDataConverter;
import com.smarttradzt.integration.spec.model.SalesContractCreateRequest;
import com.smarttradzt.integration.spec.model.SalesContractCreateResponse;
import com.smarttradzt.integration.spec.util.NumberUtil;

import createsalescontractpmt2.ContractHeader;
import createsalescontractpmt2.ContractItems;
import createsalescontractpmt2.ContractPartners;
import createsalescontractpmt2.SalesContractInputPMT2;
import createsalescontractpmt2.SalesContractOutputPMT2;

public class KPMGSalesContractCreateConverter
		implements SalesContractCreateDataConverter<SalesContractInputPMT2, SalesContractOutputPMT2> {

	@Override
	public SalesContractInputPMT2 convertOutbound(SalesContractCreateRequest request) {
		NumberFormat numFormatter = DecimalFormat.getInstance();
		numFormatter.setGroupingUsed(false);
		numFormatter.setMaximumFractionDigits(10);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		SalesContractInputPMT2 input = new SalesContractInputPMT2();
		input.setContractHeader(new ContractHeader());
		input.setContractPartners(new ContractPartners());

		// SECTION : HEADER
		input.getContractHeader().setSalesOrganisation(request.getContractHeader().getSalesOrganisation());
		input.getContractHeader().setDistributionChannel(request.getContractHeader().getDistributionChannel());
		input.getContractHeader().setSalesDivision(request.getContractHeader().getSalesDivision());
		input.getContractHeader().setSalesOffice(request.getContractHeader().getSalesOffice());
		input.getContractHeader().setSalesGroup(request.getContractHeader().getSalesGroup());

		input.getContractHeader().setPONumber(request.getContractHeader().getSalesDocFullNumber());
		input.getContractHeader().setPOSOC(request.getContractHeader().getCustomerPONumber());
		input.getContractHeader().setIncoterms1(request.getContractHeader().getIncoterms1());
		input.getContractHeader().setIncoterms2(request.getContractHeader().getIncoterms2());

		input.getContractHeader().setPaymentTerm(request.getContractHeader().getPaymentTerm());

		if (request.getContractHeader().getValidFrom() != null) {
			input.getContractHeader().setValidFrom(request.getContractHeader().getValidFrom().format(formatter));
		}
		if (request.getContractHeader().getValidTo() != null) {
			input.getContractHeader().setValidTo(request.getContractHeader().getValidTo().format(formatter));
		}

		input.getContractHeader().setShippingCondition(request.getContractHeader().getShippingCondition());
		input.getContractHeader().setPriceCurrency(request.getContractHeader().getPriceCurrency());
		input.getContractHeader().setPriceList(request.getContractHeader().getPriceList());
		if (request.getContractHeader().getPriceDate() != null) {
			input.getContractHeader().setPriceDate(request.getContractHeader().getPriceDate().format(formatter));
		}
		if (request.getContractHeader().getPurchaseDate() != null) {
			input.getContractHeader().setPurchaseDate(request.getContractHeader().getPurchaseDate().format(formatter));
		}
		if (request.getContractHeader().getPoDate() != null) {
			input.getContractHeader().setPODate(request.getContractHeader().getPoDate().format(formatter));
		}
		input.getContractHeader().setCustomerType(request.getContractHeader().getCustomerType());

		input.getContractHeader().setTenderType(request.getContractHeader().getTenderType());

		input.getContractPartners().setSoldTo(request.getContractHeader().getSoldTo());
		input.getContractPartners().setShipTo(request.getContractHeader().getShipTo());

		if (request.getContractHeader().getReta() != null) {
			input.getContractHeader().setRETA(request.getContractHeader().getReta().format(formatter));
		}

		if (request.getContractHeader().getExchangeRateFI() != 0) {
			input.getContractHeader()
					.setExchangeRateFI(numFormatter.format(request.getContractHeader().getExchangeRateFI()));
		}

		input.getContractHeader().setOSSalesModel(request.getContractHeader().getBusinessModel());

		if (request.getContractHeader().getExchangeRate() != 0) {
			input.getContractHeader()
					.setExchangeRate(numFormatter.format(request.getContractHeader().getExchangeRate()));
		}

		if (request.getContractHeader().getExRateType() != null) {
			input.getContractHeader().setExRateType(request.getContractHeader().getExRateType());
		}

		// SECTION : ITEM
		for (SalesContractCreateRequest.ContractItem requestItem : request.getContractItems()) {

			ContractItems item = new ContractItems();

			String materialCode = StringUtils.leftPad(requestItem.getProduct(), 18, '0');
			item.setProduct(materialCode);
			item.setPlant(requestItem.getPlant());

			item.setQuantity(numFormatter.format(requestItem.getQuantity()));
			String uomCode = convertUomSendToSAP(requestItem.getQuantityUOM());
			item.setQuantityUOM(uomCode);

			item.setInvoicePrice(numFormatter.format(requestItem.getInvoicePrice()));
			item.setPriceCurrency(requestItem.getPriceCurrency());

			if (requestItem.isBackToBackChangeFlag()) {

				item.setClearanceCost(numFormatter.format(NumberUtil.round(requestItem.getClearanceCost(), 3)));
				item.setClearanceCostCurr(requestItem.getPriceCurrency());

				item.setLocShpmtCost(numFormatter.format(NumberUtil.round(requestItem.getLocationShipmentCost(), 3)));
				item.setLocShpmtCostCurr(requestItem.getPriceCurrency());

				item.setInbInsurance(numFormatter.format(NumberUtil.round(requestItem.getInboundInsurance(), 3)));
				item.setInbInsuranceCurr(requestItem.getPriceCurrency());

				item.setInbSurveryor(numFormatter.format(NumberUtil.round(requestItem.getInboundSurveyor(), 3)));
				item.setInbSurveryorCurr(requestItem.getPriceCurrency());
			}

			String itemNumber = StringUtils.leftPad(requestItem.getItemNumber(), 6, '0');
			item.setPOItemNumber(itemNumber);

			item.setRoute(requestItem.getRoute());
			item.setPalletize(requestItem.getPalletize());

			if (requestItem.getAlphaPrice() != 0) {
				item.setAlphaPrice(numFormatter.format(NumberUtil.round(requestItem.getAlphaPrice(), 3)));
			}
			if (requestItem.getFloorPrice() != 0) {
				item.setFloorPrice(numFormatter.format(NumberUtil.round(requestItem.getFloorPrice(), 3)));
			}

			item.setStorageLocation(requestItem.getStorageLocation());

			input.getContractItems().add(item);
		}
		return input;
	}

	@Override
	public SalesContractCreateResponse convertInbound(SalesContractOutputPMT2 output) {
		SalesContractCreateResponse response = new SalesContractCreateResponse();

		String contractNumber = output.getContractNumber();

		if (StringUtils.isNotBlank(contractNumber)) {

			response.setSuccess(true);
			response.setContractNumber(contractNumber);

			response.setMessage("Successfully send out sales contract with return document number " + contractNumber);

		} else {
			response.setSuccess(false);

			StringBuffer buffer = new StringBuffer();
			StringBuffer sapMessage = new StringBuffer();

			for (createsalescontractpmt2.RETURN retMsg : output.getRETURN()) {
				buffer.append(retMsg.getTYPE()).append(":").append(retMsg.getMESSAGE());

				if ("E".equals(retMsg.getTYPE()) || "A".equals(retMsg.getTYPE())) {
					sapMessage.append(retMsg.getMESSAGE());
				}
			}

			response.setMessage(buffer.toString());
			response.setSapMessage(sapMessage.toString());
		}
		return response;
	}

	public String convertUomSendToSAP(String uom) {
		if (uom != null) {
			if (uom.equals("MT")) {
				return "TO";
			}
		}
		return uom;
	}
}
