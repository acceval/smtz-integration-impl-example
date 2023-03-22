package route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smarttradzt.integration.spec.model.IntegrationTask;
import com.smarttradzt.integration.spec.route.CommonSOAPConsumerRouteBuilder;

import getinvoiceshandler.GetInvoicesHandlerPortType;
import getinvoiceshandler.GetInvoicesResponse;

public class KPMGInvoiceRoute extends CommonSOAPConsumerRouteBuilder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public KPMGInvoiceRoute(IntegrationTask task) {
        super(task);
    }
    
	@Override
	protected Class<?> getServiceClassFQN() {
		return GetInvoicesHandlerPortType.class;
	}


	@Override
	protected Class<?> getResponseClass() {
		return GetInvoicesResponse.class;
	}
}
