package converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import com.smarttradzt.integration.spec.converter.CustomerCreditInfoDataConverter;
import com.smarttradzt.integration.spec.model.CustomerCreditInfoRequest;
import com.smarttradzt.integration.spec.model.CustomerCreditInfoResponse;

import getcustomercreditinfo.CustomerCreditInput;
import getcustomercreditinfo.CustomerCreditOutput;
import getcustomercreditinfo.Header;
import getcustomercreditinfo.Result;

public class KPMGCustomerCreditInfoConverter
		implements CustomerCreditInfoDataConverter<CustomerCreditInput, CustomerCreditOutput> {

	@Override
	public CustomerCreditInput convertOutbound(CustomerCreditInfoRequest request) {
		CustomerCreditInput input = new CustomerCreditInput();
		Header header = new Header();
		header.setCustomerSoldToNo(request.getSoldTo());
		header.setCreditControlArea("P001");

		input.setHeader(header);
		return input;
	}

	@Override
	public CustomerCreditInfoResponse convertInbound(CustomerCreditOutput output) {
		Result result = output.getResult();
		CustomerCreditInfoResponse response = new CustomerCreditInfoResponse();

		response.setCurrency("USD"); // TODO hardcode for now

		response.setTotalCreditExposure(formatCustomerCreditValue(result.getTotalCreditExposure()));
		response.setTotalCreditExposurePercentage(formatCustomerCreditValue(result.getTotalCreditExposurePercentage()));
		response.setTotalCreditLimit(formatCustomerCreditValue(result.getTotalCreditLimit()));
		response.setBalanceCreditLimit(formatCustomerCreditValue(result.getBalanceCreditLimit()));
		response.setBalanceCreditLimitPercentage(formatCustomerCreditValue(result.getBalanceCreditLimitPercentage()));
		response.setCreditBlock(result.getCreditBlock());

		response.setSecurityDoc(result.getSecurityDoc());

		if (StringUtils.isNotBlank(result.getSecurityDocExpiryDate())) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			response.setSecurityDocExpiryDate(LocalDate.parse(result.getSecurityDocExpiryDate(), formatter));
		}
		return response;
	}

	Double formatCustomerCreditValue(String value) {
		if (StringUtils.isBlank(value))
			return new Double(0);

		Double retVal = new Double(0);
		try {
			retVal = Double.parseDouble(value);
		} catch (Throwable t) {
			t.printStackTrace();

		}

		return retVal;
	}

}
