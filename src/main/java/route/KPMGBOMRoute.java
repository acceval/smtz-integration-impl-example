package route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smarttradzt.integration.spec.model.IntegrationTask;
import com.smarttradzt.integration.spec.route.CommonSOAPConsumerRouteBuilder;

import getbom.GetBOMPortType;
import getbom.GetBOMResponse;

public class KPMGBOMRoute extends CommonSOAPConsumerRouteBuilder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public KPMGBOMRoute(IntegrationTask task) {
        super(task);
    }

	@Override
	protected Class<?> getServiceClassFQN() {
		return GetBOMPortType.class;
	}

	@Override
	protected Class<?> getResponseClass() {
		return GetBOMResponse.class;
	}
}
