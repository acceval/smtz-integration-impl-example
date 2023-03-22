package route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smarttradzt.integration.spec.model.IntegrationTask;
import com.smarttradzt.integration.spec.route.CommonSOAPConsumerRouteBuilder;

import forexhandler.ForexHandlerPortType;
import forexhandler.ForexOutput;

public class KPMGExchangeRateRoute extends CommonSOAPConsumerRouteBuilder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public KPMGExchangeRateRoute(IntegrationTask task) {
        super(task);
    }

	@Override
	protected Class<?> getServiceClassFQN() {
		return ForexHandlerPortType.class;
	}

	@Override
	protected Class<?> getResponseClass() {
		return ForexOutput.class;
	}
}
