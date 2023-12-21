package converter;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.smarttradzt.integration.spec.converter.SKUAlternateConversionDataConverter;
import com.smarttradzt.integration.spec.model.SKUAlternateConversionResponse;

import getsku.GetSKU;
import getsku.GetSKUResponse;

public class KPMGSKUAlternateConversionConverter
		implements SKUAlternateConversionDataConverter<GetSKU, GetSKUResponse> {

	@Override
	public GetSKU convertOutbound(String product) {
		String materialCode = StringUtils.leftPad(product, 18, '0');

		GetSKU input = new GetSKU();
		input.setMaterialNo(materialCode);

		return input;
	}

	@Override
	public SKUAlternateConversionResponse convertInbound(GetSKUResponse output, String product) {
		SKUAlternateConversionResponse response = new SKUAlternateConversionResponse();
		response.setItems(new ArrayList<>());

		SKUAlternateConversionResponse.Item item = new SKUAlternateConversionResponse.Item();

		item.setProduct(product);
		item.setConversions(new ArrayList<>());

		response.getItems().add(item);

		for (getsku.SkuOutput so : output.getSkuOutput()) {
			if (so == null)
				continue;
			SKUAlternateConversionResponse.ItemConversion itemConversion = new SKUAlternateConversionResponse.ItemConversion();

			itemConversion.setFromUOM(so.getUnitOfMeasureDisplay());
			itemConversion.setToUOM(so.getBaseUnitOfMeasure());

			double conversion = Double.parseDouble(so.getNumeratorConversion())
					/ Double.parseDouble(so.getDenominatorConversion());
			itemConversion.setConversion(conversion);

			item.getConversions().add(itemConversion);
		}

		return response;
	}

}
