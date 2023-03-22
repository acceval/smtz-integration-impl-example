package route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smarttradzt.integration.spec.model.IntegrationTask;
import com.smarttradzt.integration.spec.route.CommonSOAPConsumerRouteBuilder;

import getsku.GetSKUPortType;
import getsku.GetSKUResponse;

public class KPMGSKUAlternateConversionRoute extends CommonSOAPConsumerRouteBuilder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public KPMGSKUAlternateConversionRoute(IntegrationTask task) {
        super(task);
    }

	@Override
	protected Class<?> getServiceClassFQN() {
		return GetSKUPortType.class;
	}

	@Override
	protected Class<?> getResponseClass() {
		return GetSKUResponse.class;
	}
}
