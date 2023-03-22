package route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smarttradzt.integration.spec.model.IntegrationTask;
import com.smarttradzt.integration.spec.route.CommonSOAPConsumerRouteBuilder;

import getcustomercreditinfo.CustomerCreditOutput;
import getcustomercreditinfo.GetCustomerCreditInfoPortType;

public class KPMGCustomerCreditInfoRoute extends CommonSOAPConsumerRouteBuilder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public KPMGCustomerCreditInfoRoute(IntegrationTask task) {
        super(task);
    }


	@Override
	protected Class<?> getServiceClassFQN() {
		return GetCustomerCreditInfoPortType.class;
	}


	@Override
	protected Class<?> getResponseClass() {
		return CustomerCreditOutput.class;
	}
}
