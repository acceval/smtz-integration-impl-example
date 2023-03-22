package route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smarttradzt.integration.spec.model.IntegrationTask;
import com.smarttradzt.integration.spec.route.CommonSOAPConsumerRouteBuilder;

import createsalescontractpmt2.CreateSalesContractPMT2PortType;
import createsalescontractpmt2.SalesContractOutputPMT2;

public class KPMGSalesContractCreateRoute extends CommonSOAPConsumerRouteBuilder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public KPMGSalesContractCreateRoute(IntegrationTask task) {
        super(task);
    }

	@Override
	protected Class<?> getServiceClassFQN() {
		return CreateSalesContractPMT2PortType.class;
	}

	@Override
	protected Class<?> getResponseClass() {
		return SalesContractOutputPMT2.class;
	}
}
