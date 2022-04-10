# Spring Event Hubs Example 1
Spring Cloud Stream Binder application with Azure Event Hubs

## Source
This project was build with the instructions provided at the following web page:
https://docs.microsoft.com/en-us/azure/developer/java/spring-framework/configure-spring-cloud-stream-binder-java-app-azure-event-hub

## Create Azure Event Hubs elements
### 1. Create Event Hub Namespace
    
    NS_NAME=goves-event-hub-namespace
    az eventhubs namespace create --name $NS_NAME

### 2. Create Event Nub Name

    HUB_NAME=goves-event-hub-name
    az eventhubs eventhub create --name $HUB_NAME --namespace-name $NS_NAME
    az eventhubs eventhub show --namespace-name $NS_NAME --name $HUB_NAME
    az eventhubs namespace authorization-rule keys list \
    --name RootManageSharedAccessKey \
    --namespace-name $NS_NAME

### 3. Create Store Account

    STORAGE_NAME=storagegoves
    az storage account create --name $STORAGE_NAME --sku Standard_RAGRS --encryption-service blob
    az storage account keys list --account-name $STORAGE_NAME
    az storage account show-connection-string -n $STORAGE_NAME

### 4. Create Storage Container

    az storage container create --name messages --connection-string <<connection-string>>
    {
    "created": true
    }

## Test the application
### 1. Execute the application
    mvn clean package -Dmaven.test.skip=true
    mvn spring-boot:run

### 2. Send messages
    curl -X POST http://localhost:8080/messages?message=hello
    INFO 13764 --- [nio-8080-exec-4] c.e.controller.EventProducerController   : Going to add message hello to event hub.                                                                                           
    INFO 13764 --- [nio-8080-exec-4] com.example.event.hub.EventHubProducer   : Manually sending message GenericMessage [payload=hello, headers={id=adf25718-6d4f-70e7-c666-7d2f58da2ca2, timestamp=1649608325969}]
    INFO 13764 --- [oundedElastic-1] c.a.s.m.e.s.c.EventHubsMessageConverter  : Message headers azure_partition_key is not supported to be set and will be ignored.                                                
    INFO 13764 --- [tition-pump-2-3] com.example.event.hub.EventHubConsumer   : New message received: 'hello', partition key: 421167133, sequence number: 1, offset: 192, enqueued time: 2022-04-10T16:32:06.003Z  
    INFO 13764 --- [ctor-http-nio-1] com.example.event.hub.EventHubConsumer   : Message 'hello' successfully checkpointed