package converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.smarttradzt.integration.spec.converter.COGDataConverter;
import com.smarttradzt.integration.spec.model.COGSRequest;
import com.smarttradzt.integration.spec.model.COGSResponse;

import getcogs.CogsCompOutput;
import getcogs.CogsInput;
import getcogs.CogsOutput;
import getcogs.GetCOGSResponse;
import getcogs.ListInputs;

public class KPMGCOGSConverter implements COGDataConverter<CogsInput, GetCOGSResponse> {

	@Override
	public CogsInput convertOutbound(COGSRequest request) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		CogsInput input = new CogsInput();

		for (COGSRequest.Item item : request.getItems()) {
			ListInputs li = new ListInputs();
			li.setPlant(item.getPlant());
			li.setValidOnDate(item.getDate().format(formatter));
			li.setMaterialCode(item.getProduct());
			input.getListInputs().add(li);
		}
		return input;
	}

	@Override
	public COGSResponse convertInbound(GetCOGSResponse output) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		COGSResponse response = new COGSResponse();
		response.setCogsItems(new ArrayList<>());
		response.setCogsComponentItems(new ArrayList<>());

		for (CogsOutput cogsOutput : output.getListItems()) {
			if (cogsOutput == null)
				continue;
			COGSResponse.ItemCOGS itemCOGS = new COGSResponse.ItemCOGS();

			itemCOGS.setPlant(cogsOutput.getPlant());

			String _materialCode = cogsOutput.getMaterialCode(); // Done
			_materialCode = StringUtils.stripStart(_materialCode, "0");
			itemCOGS.setProduct(_materialCode);

			itemCOGS.setPrice(Double.parseDouble(cogsOutput.getPrice()));
			itemCOGS.setCurrency(cogsOutput.getCurrency());
			itemCOGS.setQuantity(Double.parseDouble(cogsOutput.getQuantity()));
			itemCOGS.setUom(cogsOutput.getUom());
			itemCOGS.setValidFrom(LocalDate.parse(cogsOutput.getValidFrom(), formatter));
			itemCOGS.setValidTo(LocalDate.parse(cogsOutput.getValidTo(), formatter));

			response.getCogsItems().add(itemCOGS);
		}

		for (CogsCompOutput cogsCompOutput : output.getListComponents()) {
			if (cogsCompOutput == null)
				continue;
			COGSResponse.ItemCOGSComponent itemCOGSComponent = new COGSResponse.ItemCOGSComponent();

			itemCOGSComponent.setPlant(cogsCompOutput.getPlant());

			String _materialCode = cogsCompOutput.getMaterialCode(); // Done
			_materialCode = StringUtils.stripStart(_materialCode, "0");
			itemCOGSComponent.setProduct(_materialCode);

			itemCOGSComponent.setComponent(cogsCompOutput.getComponent());
			itemCOGSComponent.setValue(Double.parseDouble(cogsCompOutput.getValue()));

			response.getCogsComponentItems().add(itemCOGSComponent);
		}

		return response;
	}

}
