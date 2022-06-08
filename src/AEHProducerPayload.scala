

import com.azure.messaging.eventhubs._;
import java.util.Arrays;
import java.util.List;
import com.azure.messaging.eventhubs.EventData
import org.apache.commons.lang3.RandomStringUtils
import java.lang.System
import net.liftweb.json._
import net.liftweb.json.Serialization.write

object AEHProducerPayload {

  def main(args: Array[String]) {

   val connectionString = "";
		val  eventHubName = "taruneh";
	  

    val producer: EventHubProducerClient = new EventHubClientBuilder().connectionString(connectionString, eventHubName).buildProducerClient
    var eventDataBatch: EventDataBatch = producer.createBatch()
    
    
   case class Message(creditcard: Int, account: Int, bank: String)
   val val1 = Message(12345, 34534, "GB")
   val val2 = Message(3434, 6767, "wp")
   implicit val formats = DefaultFormats
   val jval1 = write(val1)
   val jval2 = write(val2)
    
    //eventData.getProperties().put(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
    //eventData.getProperties().put(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
    //eventDataBatch.tryAdd(eventData);
   
   val eventData: EventData = new EventData(jval1);

    if (!eventDataBatch.tryAdd(eventData)) {
        producer.send(eventDataBatch);
        eventDataBatch = producer.createBatch();

       // Try to add that event that couldn't fit before.
      if (!eventDataBatch.tryAdd(eventData)) {
        throw new IllegalArgumentException("Event is too large for an empty batch. Max size: "
          + eventDataBatch.getMaxSizeInBytes());
      }
    }
    if (eventDataBatch.getCount() > 0) {
      producer.send(eventDataBatch);
    }

    producer.close();
    println("close...")
    System.exit(0)

  }

}