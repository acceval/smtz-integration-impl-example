# smtz-integration-impl-example

## Route implementation
## Customized Route
- Customized route will have to extends either **CommonSOAPConsumerRouteBuilder** or **CommonSOAPProducerRouteBuilder** depends on which type the new customized route is

### CommonSOAPConsumerRouteBuilder
- Consumer route
- Have the option to override configure function which is defaulted in **CommonSOAPConsumerRouteBuilder** depends on new logic required
- **getResponseClass** is the **output message** java class generated from wsdl file associated with the new customized route

### CommonSOAPProducerRouteBuilder
- Producer route
- configure method is needed to ensure all the webservice function specified gets its returned result accordingly

### Common method
- **getServiceClassFQN** is the **portType** java class generated from wsdl file associated with the new customized route

## Data Converters
- It is optional to create customized converter as there is already default logic for each integration
- For new customized data converters it is required to implement **interface** of the respective integration which is created beforehand

## SOAP wsdl
- It is required to create wsdl files under **resources/wsdl** folder so that java class of the respective integration is generated accordingly and can be used in **route** class and **converter** class
- pom.xml is required to be changed as well if there is any new **.wsdl** files **added** or **renamed**
- Build the project and wsdl java class will be generated into the folders specified in pom.xml
