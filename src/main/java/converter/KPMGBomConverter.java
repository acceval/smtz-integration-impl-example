package converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.smarttradzt.integration.spec.converter.BomDataConverter;
import com.smarttradzt.integration.spec.model.BomRequest;
import com.smarttradzt.integration.spec.model.BomResponse;

import getbom.BomHeaderOutput;
import getbom.BomItemOutput;
import getbom.GetBOM;
import getbom.GetBOMResponse;

public class KPMGBomConverter implements BomDataConverter<GetBOM, GetBOMResponse> {

	@Override
	public GetBOM convertOutbound(BomRequest request) {
		GetBOM input = new GetBOM();

		String materialCode = StringUtils.leftPad(request.getProduct(), 18, '0');
		input.setMaterialCode(materialCode);
		input.setPlantCode(request.getPlant());

		return input;
	}

	@Override
	public BomResponse convertInbound(GetBOMResponse output) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		BomResponse response = new BomResponse();
		response.setHeaders(new ArrayList<>());
		response.setItems(new ArrayList<>());

		// header
		for (BomHeaderOutput temp : output.getHeaderOutput()) {
			if (temp == null)
				continue;

			BomResponse.Header header = new BomResponse.Header();
			header.setMaterialNumber(StringUtils.stripStart(temp.getMaterialNumber(), "0"));
			header.setConfirmedQuantity(Double.parseDouble(temp.getConfirmedQuantity()));
			header.setUnitOfMeasure(temp.getUnitOfMeasure());
			header.setPlant(temp.getPlant());
			header.setAssetClass(temp.getAssetClass());
			header.setAlternativeBOM(temp.getAlternativeBOM());
			header.setValidFromDate(LocalDate.parse(temp.getValidFromDate(), formatter));

			response.getHeaders().add(header);
		}

		// item
		for (BomItemOutput temp : output.getItemOutput()) {
			if (temp == null)
				continue;

			BomResponse.Item item = new BomResponse.Item();

			item.setBomComponent(StringUtils.stripStart(temp.getBOMComponent(), "0"));
			item.setMaterialDescription(temp.getMaterialDescription());
			item.setComponentQuantity(Double.parseDouble(temp.getComponentQuantity()));
			item.setBaseUnitOfMeasure(temp.getBaseUnitOfMeasure());
			item.setDeletionIndicator(temp.getDeletionIndicator());
			item.setAlternativeBOM(temp.getAlternativeBOM());

			response.getItems().add(item);
		}

		return response;
	}

}
