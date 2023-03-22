package converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import com.smarttradzt.integration.spec.converter.ExchangeRouteDataConverter;
import com.smarttradzt.integration.spec.model.ExchangeRateRequest;
import com.smarttradzt.integration.spec.model.ExchangeRateResponse;
import com.smarttradzt.integration.spec.util.NumberUtil;

import forexhandler.ForexInput;
import forexhandler.ForexOutput;

public class KPMGExchangeRateConverter implements ExchangeRouteDataConverter<ForexInput, ForexOutput> {

	@Override
	public ForexInput convertOutbound(ExchangeRateRequest request) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		ForexInput input = new ForexInput();
		input.setFromCurrency(request.getFromCurrency());
		input.setToCurrency(request.getToCurrency());
		input.setExchangeRateType(request.getExchangeRateType());
		String date = request.getEffectiveDate().format(formatter);
		input.setEffectiveDate(date);
		return input;
	}

	@Override
	public ExchangeRateResponse convertInbound(ForexOutput output) {
		DateTimeFormatter outFormatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");

		if (StringUtils.isBlank(output.getEffectiveDate()))
			return null;

		ExchangeRateResponse resp = new ExchangeRateResponse();

		resp.setExchangeRateType(output.getExchangeRateType());
		resp.setFromCurrency(output.getFromCurrency());
		resp.setToCurrency(output.getToCurrency());
		resp.setRate(NumberUtil.zeroIfNulll(output.getRate()));
		resp.setRatioFrom(NumberUtil.zeroIfNulll(output.getRatioFrom()));
		resp.setRatioTo(NumberUtil.zeroIfNulll(output.getRatioTo()));
		resp.setEffectiveDate(LocalDateTime.parse(output.getEffectiveDate() + " 00:00:00", outFormatter));
		return resp;
	}
}
