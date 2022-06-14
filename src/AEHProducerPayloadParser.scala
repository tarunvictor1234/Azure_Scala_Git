

import java.time.LocalDateTime

import com.azure.messaging.eventhubs.EventData
import com.azure.messaging.eventhubs.EventDataBatch
import com.azure.messaging.eventhubs.EventHubClientBuilder
import com.azure.messaging.eventhubs.EventHubProducerClient
import com.fasterxml.jackson.databind.ObjectMapper
import com.azure.messaging.eventhubs.EventData.SystemProperties
import com.azure.messaging.eventhubs.EventData.SystemProperties
import com.azure.messaging.eventhubs.EventData.SystemProperties
import com.azure.messaging.eventhubs.EventData.SystemProperties
import com.azure.messaging.eventhubs.models.CreateBatchOptions
import com.microsoft.azure.eventhubs.BatchOptions

object AEHProducerPayloadParser {

  def main(args: Array[String]) {

    val ccdetails = new CreditCardDetails();
    ccdetails.bank = "GB";
    ccdetails.creditcard = 12345
    ccdetails.account = 98765;
    ccdetails.timestamp = LocalDateTime.now().toString();

    AEHProducerPayloadParser.producer(ccdetails, "accounttype")
    System.exit(0)

  }

  def producer(ccdetails: IProducer, producertype: String) {

    val connectionString = "Endpoint=sb:";
    val eventHubName = "taruneh";

    val producer: EventHubProducerClient = new EventHubClientBuilder().connectionString(connectionString, eventHubName).buildProducerClient
    var batchOptions = new CreateBatchOptions
    batchOptions.setPartitionId("0")
    //batchOptions.setPartitionKey("");
    batchOptions.setMaximumSizeInBytes(1 * 1024);

    var eventDataBatch: EventDataBatch = producer.createBatch(batchOptions)

    producertype match {
      case "accounttype" => {
        val mapper = new ObjectMapper();
        val ccJSON = mapper.writeValueAsString(ccdetails);
        println(ccJSON)

        val eventData: EventData = new EventData(ccJSON);

        println(eventDataBatch.getMaxSizeInBytes);
        //println(eventData.getSystemProperties);
        //println(eventDataBatch.getCount);

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

      }
      case _ => println("no type found")
    }

  }
}