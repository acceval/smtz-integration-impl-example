package converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.smarttradzt.integration.spec.converter.SAPMarketPriceDataConverter;
import com.smarttradzt.integration.spec.model.SAPMarketPriceRequest;
import com.smarttradzt.integration.spec.model.SAPMarketPriceResponse;

import getmarketpricedata.GetMarketPriceDataResponse;
import getmarketpricedata.MarketPriceInput;
import getmarketpricedata.MarketPriceOutput;

public class KPMGMarketPriceConverter implements SAPMarketPriceDataConverter<MarketPriceInput, GetMarketPriceDataResponse> {

	@Override
	public MarketPriceInput convertOutbound(SAPMarketPriceRequest request, String sapCode) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		MarketPriceInput input = new MarketPriceInput();
        input.setZZDTFROM(request.getDateFrom().format(formatter));
        input.setZZDTTO(request.getDateTo().format(formatter));
        input.setQUOTNO(sapCode);

        return input;
	}

	@Override
	public SAPMarketPriceResponse convertInbound(GetMarketPriceDataResponse output) {
		if (output == null || output.getMarketPrice() == null || output.getMarketPrice().getOutput() == null)
			return null;
		 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		SAPMarketPriceResponse response = new SAPMarketPriceResponse();
		response.setItems(new ArrayList<>());

		for (MarketPriceOutput mpo : output.getMarketPrice().getOutput()) {
			if (mpo == null)
				continue;
			SAPMarketPriceResponse.SAPMarketPriceResponseItem item = new SAPMarketPriceResponse.SAPMarketPriceResponseItem();

			item.setSource(mpo.getQUOSRC());
			item.setSapCode(mpo.getQUOTNO());
			item.setDate(LocalDate.parse(mpo.getQUOTDATE(), formatter));
			item.setHigh(Double.parseDouble(mpo.getPRICEH()));
			item.setLow(Double.parseDouble(mpo.getPRICEL()));

			response.getItems().add(item);
		}

		return response;
	}

}
