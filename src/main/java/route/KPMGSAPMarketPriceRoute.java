package route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smarttradzt.integration.spec.model.IntegrationTask;
import com.smarttradzt.integration.spec.model.MarketPriceResponse;
import com.smarttradzt.integration.spec.route.CommonSOAPConsumerRouteBuilder;

import getmarketpricedata.GetMarketPriceDataPortType;

public class KPMGSAPMarketPriceRoute extends CommonSOAPConsumerRouteBuilder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public KPMGSAPMarketPriceRoute(IntegrationTask task) {
        super(task);
    }

	@Override
	protected Class<?> getServiceClassFQN() {
		return GetMarketPriceDataPortType.class;
	}

	@Override
	protected Class<?> getResponseClass() {
		return MarketPriceResponse.class;
	}
}
