package route;

import java.io.BufferedReader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smarttradzt.integration.spec.model.IntegrationTask;
import com.smarttradzt.integration.spec.model.MarketPriceRequest;
import com.smarttradzt.integration.spec.model.MarketPriceResponse;
import com.smarttradzt.integration.spec.route.CamelUtil;
import com.smarttradzt.integration.spec.route.CommonRouteBuilder;

public class KPMGMarketPriceRoute extends CommonRouteBuilder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public KPMGMarketPriceRoute(IntegrationTask task) {
        super(task);
    }


    @Override
    public void configure() throws Exception {

        onException(Throwable.class).bean(this, "errorHandler");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("M/d/y");

        from(CamelUtil.getFromEndpointName(task)).routeId(CamelUtil.getRouteId(task))
                .process(preProcess())
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        MarketPriceRequest request = exchange.getIn().getBody(MarketPriceRequest.class);

                        StringBuffer buffer = new StringBuffer(task.getOutboundAddress() + "?symbols=");

                        // symbol
                        StringBuffer symbolBuf = new StringBuffer();
                        String delim = "";
                        for (String symbol : request.getSymbols()) {
                            symbolBuf.append(delim).append(symbol);
                            delim = ",";
                        }

                        buffer.append(URLEncoder.encode(symbolBuf.toString(), "UTF-8"));

                        // field
                        buffer.append("&fields=symbol,date,high,low,close");

                        // date
                        String startDate = request.getDateFrom().format(formatter);
                        String endDate = request.getDateTo().format(formatter);
                        buffer.append("&startdate=").append(startDate).append("&enddate=").append(endDate);

                        // format
                        buffer.append("&output=csv&includeheaders=true");

                        String uri = buffer.toString();

                        exchange.getOut().setHeader(Exchange.HTTP_URI, uri);
                    }
                })
                .to("http4://dummy?authUsername=" + task.getOutboundUsername() + "&authPassword=" + task.getOutboundPassword())
                .convertBodyTo(String.class)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String content = exchange.getIn().getBody(String.class);

                        BufferedReader br = new BufferedReader(new StringReader(content));

                        String line;

                        MarketPriceResponse response = new MarketPriceResponse();
                        response.setItems(new ArrayList<>());

                        while ((line = br.readLine()) != null) {
                            // process the line.
                            String[] datas = line.split(",");

                            if (datas.length == 5) {
                                String symbol = datas[0];
                                if (symbol.equals("symbol")) continue;

                                LocalDate effectiveDate = LocalDate.parse(datas[1].split(" ")[0], formatter2);
                                double high = Double.parseDouble(datas[2]);
                                double low = Double.parseDouble(datas[3]);
                                double close = Double.parseDouble(datas[4]);

                                // TODO handle this one at busienss side
//                                if ("#ICIS4002006".equals(symbol) || "#ICIS4002005".equals(symbol)) {
//                                    double conversion = 22.0463; // US Cents Per Pound to US Dollar Per MT
//                                    high = high * conversion;
//                                    low = low * conversion;
//                                    close = close * conversion;
//                                }


                                MarketPriceResponse.MarketPriceResponseItem item = new MarketPriceResponse.MarketPriceResponseItem();

                                item.setSymbol(symbol);
                                item.setDate(effectiveDate);
                                item.setHigh((float) high);
                                item.setLow((float) low);
                                item.setClose((float)close);

                                response.getItems().add(item);
                            }
                        }

                        exchange.getOut().setBody(response);
                    }
                })
                .process(postProcess());
    }
}
