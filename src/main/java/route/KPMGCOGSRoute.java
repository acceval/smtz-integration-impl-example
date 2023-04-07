package route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smarttradzt.integration.spec.model.IntegrationTask;
import com.smarttradzt.integration.spec.route.CommonSOAPConsumerRouteBuilder;

import getcogs.GetCOGSPortType;
import getcogs.GetCOGSResponse;

public class KPMGCOGSRoute extends CommonSOAPConsumerRouteBuilder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public KPMGCOGSRoute(IntegrationTask task) {
        super(task);
    }

	@Override
	protected Class<?> getServiceClassFQN() {
		return GetCOGSPortType.class;
	}

	@Override
	protected Class<?> getResponseClass() {
		return GetCOGSResponse.class;
	}
}
