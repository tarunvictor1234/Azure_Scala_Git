



import java.time.LocalDateTime

import com.azure.messaging.eventhubs.EventData
import com.azure.messaging.eventhubs.EventDataBatch
import com.azure.messaging.eventhubs.EventHubClientBuilder
import com.azure.messaging.eventhubs.EventHubProducerClient
import com.fasterxml.jackson.databind.ObjectMapper

object AEHProducerDetails {

  def main(args: Array[String]) {


	  
    val connectionString = "";
		val eventHubName = "taruneh";
		
    val producer: EventHubProducerClient = new EventHubClientBuilder().connectionString(connectionString, eventHubName).buildProducerClient
    var eventDataBatch: EventDataBatch = producer.createBatch()
    
   
   val ccdetails = new CreditCardDetails();
    ccdetails.bank="GB";
    ccdetails.creditcard = 12345
    ccdetails.account= 98765;
    ccdetails.timedetails=LocalDateTime.now().toString();
    
    val mapper = new ObjectMapper(); 
    val ccJSON =  mapper.writeValueAsString(ccdetails);
    println(ccJSON)
    
    val eventData: EventData = new EventData(ccJSON);

    if (!eventDataBatch.tryAdd(eventData)) {
       println("sending data...");
        producer.send(eventDataBatch);
        eventDataBatch = producer.createBatch();

      if (!eventDataBatch.tryAdd(eventData)) {
        throw new IllegalArgumentException("Event is too large for an empty batch. Max size: "
          + eventDataBatch.getMaxSizeInBytes());
      }
    }
    if (eventDataBatch.getCount() > 0) {
      println("sending data...");
      producer.send(eventDataBatch);
    }

    producer.close();
    println("close...")
    System.exit(0)

  }

}