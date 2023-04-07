package route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smarttradzt.integration.spec.model.IntegrationTask;
import com.smarttradzt.integration.spec.route.CommonSOAPConsumerRouteBuilder;

import updatesalescontract.SalesContractChangeOutput;
import updatesalescontract.UpdateSalesContractPortType;

public class KPMGSalesContractStatusRoute extends CommonSOAPConsumerRouteBuilder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public KPMGSalesContractStatusRoute(IntegrationTask task) {
        super(task);
    }

	@Override
	protected Class<?> getServiceClassFQN() {
		return UpdateSalesContractPortType.class;
	}

	@Override
	protected Class<?> getResponseClass() {
		return SalesContractChangeOutput.class;
	}
}
