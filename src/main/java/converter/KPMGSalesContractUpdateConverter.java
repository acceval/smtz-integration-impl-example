package converter;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang3.StringUtils;

import com.smarttradzt.integration.spec.converter.SalesContractUpdateDataConverter;
import com.smarttradzt.integration.spec.model.SalesContractUpdateRequest;
import com.smarttradzt.integration.spec.model.SalesContractUpdateResponse;
import com.smarttradzt.integration.spec.util.NumberUtil;

import updatesalescontract.RETURN;
import updatesalescontract.SalesContractChangeInput;
import updatesalescontract.SalesContractChangeOutput;

public class KPMGSalesContractUpdateConverter
		implements SalesContractUpdateDataConverter<SalesContractChangeInput, SalesContractChangeOutput> {

	@Override
	public SalesContractChangeInput convertOutbound(SalesContractUpdateRequest request) {
		NumberFormat numFormatter = DecimalFormat.getInstance();
		numFormatter.setGroupingUsed(false);
		numFormatter.setMaximumFractionDigits(10);

		SalesContractChangeInput input = new SalesContractChangeInput();
		input.setContractHeader(new updatesalescontract.ContractHeader());
		input.getContractHeader().setActivity("CHG");
		input.getContractHeader().setDocumentNo(request.getSalesContractNumber());

		if (request.getExchangeRate() != 0) {
			input.getContractHeader().setExchangeRate(numFormatter.format(request.getExchangeRate()));
		}

		updatesalescontract.ContractItems item = new updatesalescontract.ContractItems();

		input.getContractItems().add(item);
		String pmtItemNumber = StringUtils.leftPad(request.getPmtItemNo(), 6, '0');
		item.setPMTItemNo(pmtItemNumber);

		String materialCode = StringUtils.leftPad(request.getProductCode(), 18, '0');
		item.setProduct(materialCode);
		item.setQuantity(numFormatter.format(request.getQuantity()));

		if (request.getInvoicePrice() != 0) {
			item.setInvoicePrice(numFormatter.format(request.getInvoicePrice()));
		}
		if (StringUtils.isNotBlank(request.getPriceCurrency())) {
			item.setPriceCurrency(request.getPriceCurrency());
		}

		if (request.isBackToBackChangeFlag()) {
			item.setClearanceCost(numFormatter.format(NumberUtil.round(request.getClearanceCost(), 3)));
			item.setClearanceCostCurr(request.getPriceCurrency());

			item.setLocShpmtCost(numFormatter.format(NumberUtil.round(request.getLocationShipmentCost(), 3)));
			item.setLocShpmtCostCurr(request.getPriceCurrency());

			item.setInbInsurance(numFormatter.format(NumberUtil.round(request.getInboundInsurance(), 3)));
			item.setInbInsuranceCurr(request.getPriceCurrency());

			item.setInbSurveryor(numFormatter.format(NumberUtil.round(request.getInboundSurveyor(), 3)));
			item.setInbSurveryorCurr(request.getPriceCurrency());
		}
		return input;
	}

	@Override
	public SalesContractUpdateResponse convertInbound(SalesContractChangeOutput output) {
		SalesContractUpdateResponse response = new SalesContractUpdateResponse();
		boolean hasError = false;
		StringBuffer buffer = new StringBuffer();
		StringBuffer sapMessage = new StringBuffer();

		for (RETURN retMsg : output.getRETURN()) {
			if (retMsg == null)
				continue;
			if ("E".equals(retMsg.getTYPE()) || "A".equals(retMsg.getTYPE()))
				hasError = true;
			buffer.append(retMsg.getTYPE()).append(":").append(retMsg.getMESSAGE());

			if ("I".equals(retMsg.getTYPE()) && "ZV001".equals(retMsg.getID())) {
				sapMessage.append(retMsg.getMESSAGE());
			}
		}

		response.setSuccess(!hasError);
		response.setMessage(buffer.toString());
		response.setSapMessage(sapMessage.toString());
		return response;
	}

}
