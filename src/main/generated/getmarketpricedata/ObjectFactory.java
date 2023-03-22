
package getmarketpricedata;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the getmarketpricedata package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _MarketPriceInput_QNAME = new QName("http://getMarketPriceData", "marketPriceInput");
    private final static QName _GetMarketPriceDataResponse_QNAME = new QName("http://getMarketPriceData", "getMarketPriceDataResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: getmarketpricedata
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MarketPriceInput }
     * 
     */
    public MarketPriceInput createMarketPriceInput() {
        return new MarketPriceInput();
    }

    /**
     * Create an instance of {@link GetMarketPriceDataResponse }
     * 
     */
    public GetMarketPriceDataResponse createGetMarketPriceDataResponse() {
        return new GetMarketPriceDataResponse();
    }

    /**
     * Create an instance of {@link MarketPrice }
     * 
     */
    public MarketPrice createMarketPrice() {
        return new MarketPrice();
    }

    /**
     * Create an instance of {@link MarketPriceOutput }
     * 
     */
    public MarketPriceOutput createMarketPriceOutput() {
        return new MarketPriceOutput();
    }

    /**
     * Create an instance of {@link ETRETURN }
     * 
     */
    public ETRETURN createETRETURN() {
        return new ETRETURN();
    }

    /**
     * Create an instance of {@link RETURN }
     * 
     */
    public RETURN createRETURN() {
        return new RETURN();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MarketPriceInput }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MarketPriceInput }{@code >}
     */
    @XmlElementDecl(namespace = "http://getMarketPriceData", name = "marketPriceInput")
    public JAXBElement<MarketPriceInput> createMarketPriceInput(MarketPriceInput value) {
        return new JAXBElement<MarketPriceInput>(_MarketPriceInput_QNAME, MarketPriceInput.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMarketPriceDataResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetMarketPriceDataResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://getMarketPriceData", name = "getMarketPriceDataResponse")
    public JAXBElement<GetMarketPriceDataResponse> createGetMarketPriceDataResponse(GetMarketPriceDataResponse value) {
        return new JAXBElement<GetMarketPriceDataResponse>(_GetMarketPriceDataResponse_QNAME, GetMarketPriceDataResponse.class, null, value);
    }

}
