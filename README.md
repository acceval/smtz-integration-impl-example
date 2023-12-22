# smtz-integration-impl-example

### Consumer Type Integration
- Step 1 - Prepare the wsdl file
  - Step 1.1 - Modify the pom.xml to include the wsdl path
  - Step 1.2 - Build the workspace to have the generated java file from wsdl
- Step 2 - Create a new ConsumerRoute class which extends **CommonSOAPConsumerRouteBuilder**
  - Step 2.1 - Override **getServiceClassFQN** with **input message** java class.
  - Step 2.2 - Override **getResponseClass** with **output message** java class.
- Step 3 - Create a new Converter class which extends **interface** of the respective integration and apply the business logic here.
  - Step 3.1 - Override **convertOubound** method which we are preparing criteria in the **input message** for SAP to retrieve data.
  - Step 3.2 - Override **convertInbound** method which we have the **output message** from SAP and need to be transformed into SmartTradzt understandable structure.

Sample Files for Consumer Type Integration
1. KPMGBOMRoute and KPMGBomConverter
2. KPMGCOGSRoute and KPMGCOGSConverter
3. KPMGCustomerCreditInfoRoute and KPMGCustomerCreditInfoConverter
4. KPMGExchangeRateRoute and KPMGExchangeRateConverter
5. KPMGInvoiceRoute and KPMGInvoiceConverter
6. KPMGSAPMarketPriceRoute and KPMGMarketPriceConverter
7. KPMGSalesContractCreateRoute and KPMGSalesContractCreateConverter
8. KPMGSalesContractStatusRoute and KPMGSalesContractUpdateConverter
9. KPMGSKUAlternateConversionRoute and KPMGSKUAlternateConversionConverter

### Producer Type Integration
- Step 1 - Create WebService services and requests (sample is under **ws** package). **Request** class will be the used in configure method as **input message** which contains SAP data and services will inform SmartTradzt on the integration status.
- Step 2 - Create a new ProducerRoute class which extends **CommonSOAPProducerRouteBuilder**
  - Step 2.1 - Override **getServiceClassFQN** with WebService java class.
  - Step 2.2 - Override **configure** method with logic prepared by SmartTradzt in example.
    - Step 2.2.1 - **process** method for each SAP function will require minor changes if **output message** from SAP is differ to the data structure we provided (data transformation needed).
    - Step 2.2.2 - Queue sender is for respective function will be provided and required here to send the **transformed SAP data** to pricing tool to do save or update action.

Sample Files for Producer Type Integration
1. KPMGMasterDataProducerRoute
2. KPMGPackagingTypeProducerRoute

## SOAP wsdl
- It is required to create wsdl files under **resources/wsdl** folder so that java class of the respective integration is generated accordingly and can be used in **route** class and **converter** class
- pom.xml is required to be changed as well if there is any new **.wsdl** files **added** or **renamed**
- Build the project and wsdl java class will be generated into the folders specified in pom.xml

## Route implementation
## Customized Route
- Customized route will have to extends either **CommonSOAPConsumerRouteBuilder** or **CommonSOAPProducerRouteBuilder** depends on which type the new customized route is

### CommonSOAPConsumerRouteBuilder
- Consumer route
- Have the option to override configure function which is defaulted in **CommonSOAPConsumerRouteBuilder** depends on new logic required
- **getServiceClassFQN** is the **input message** java class generated from wsdl file
- **getResponseClass** is the **output message** java class generated from wsdl file

### CommonSOAPProducerRouteBuilder
- Producer route
- configure method is needed to ensure all the webservice function specified gets its returned result accordingly, custom mapping from **input message** java class to SmartTradzt object class response.
- **getServiceClassFQN** is the **input message** java class generated from wsdl file

## Data Converters
- For new customized data converters it is required to implement **interface** of the respective integration which is created beforehand
- Apply business logic here and data transformation into SmartTradzt understandable structure

## WebService services and requests
- Request is the **input message** java class which contains SAP data that sent to SmartTradzt and to be transformed into SmartTradzt understandable structure
- Services is the response message sent back to SmartTradzt to inform the integration status
