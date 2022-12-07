*** TO SEND REQUEST WITH POSTMAN ***

Import into postman the file(Requests.postman_collection.json) containing in the folder 



*** START THE KAFKA ENVIRONMENT ***

# Start the ZooKeeper service
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

# Start the Kafka broker service
.\bin\windows\kafka-server-start.bat .\config\server.properties



*** TRANSACTION_STATUS_NOTIFIER MODE CHOICE ***

there is in the application properties a "deploy.notifier = " 
- "rest" : if you want to use the notify method implementation with the restTemplate;
- "kafka" : if you want to use the notify method implementation with Kafka;

Change the attribute value in the application properties in both userService and transactionService side