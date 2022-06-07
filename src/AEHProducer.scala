

import com.azure.messaging.eventhubs._;
import java.util.Arrays;
import java.util.List;
import com.azure.messaging.eventhubs.EventData

object Sender {

  def main(args: Array[String]) {

    val connectionString = "";
    val eventHubName = "taruneh";

    val producer: EventHubProducerClient = new EventHubClientBuilder().connectionString(connectionString, eventHubName).buildProducerClient
    var eventDataBatch: EventDataBatch = producer.createBatch()
    val eventData: EventData = new EventData("tarunevent");
    eventData.getProperties().put("k1", "v1");
    eventData.getProperties().put("k2", "v2");
    //eventDataBatch.tryAdd(eventData);

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

  }

}