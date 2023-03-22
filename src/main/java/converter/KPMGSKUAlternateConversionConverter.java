package converter;

import com.smarttradzt.integration.spec.converter.SKUAlternateConversionDataConverter;
import com.smarttradzt.integration.spec.model.SKUAlternateConversionResponse;

import getsku.GetSKU;
import getsku.GetSKUResponse;

public class KPMGSKUAlternateConversionConverter
		implements SKUAlternateConversionDataConverter<GetSKU, GetSKUResponse> {

	@Override
	public GetSKU convertOutbound(String product) {
		return null;
	}

	@Override
	public SKUAlternateConversionResponse convertInbound(GetSKUResponse output, String product) {

		return null;
	}

}
