# smtz-integration-impl-example

### Consumer Type Integration
- Step 1 - Prepare the wsdl classes
- Step 2 - Create a new ConsumerRoute class which extends **CommonSOAPConsumerRouteBuilder** override **getServiceClassFQN** and **getResponseClass** with the appropriate **input message** java class and **output message** java class.
- Step 3 - Create a new Converter class which extends **interface** of the respective integration and apply the business logic here and transform the **input message** into a SmartTradzt understandable structure.

### Product Type Integration
- Step 1 - Create WebService services and requests (which is under **ws** package of example). **Request** class will be the **input message** used in configure method for the exchange message and services will inform SmartTradzt on the integration status.
- Step 2 - Create a new ConsumerRoute class which extends **CommonSOAPProducerRouteBuilder** override **getServiceClassFQN** with the appropriate **input message** java class. Override **configure** method with the appropriate **input message** and apply data transformation into SmartTradzt understandable structure. Queue sender will be required here to send the response back to SmartTradzt for any data insert or update.

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
- Request is the **input message** java class from SAP and to be transformed into SmartTradzt java class.
- Services is the response message sent back to SmartTradzt to inform if the integration is successful.

