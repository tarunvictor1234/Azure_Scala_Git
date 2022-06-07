import java.sql.Timestamp;
import java.time.Duration;

import com.azure.core.util.IterableStream;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubConsumerClient;
import com.azure.messaging.eventhubs.models.EventPosition;
import com.azure.messaging.eventhubs.models.PartitionEvent;

public class AEH_Consumer {

	public static void main(String args[]) {

		String connectionString = "";
		String eventHubName = "taruneh";

		EventHubConsumerClient consumer = new EventHubClientBuilder().connectionString(connectionString, eventHubName)
				.consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME).buildConsumerClient();

		while (true) {
			IterableStream<PartitionEvent> events = consumer.receiveFromPartition("0", 100, EventPosition.earliest(),
					Duration.ofSeconds(30));
			System.out.println(new Timestamp(System.currentTimeMillis()) + " ->"+events.toString());
			for (PartitionEvent partitionEvent : events) {
				System.out.print("\n Event getSystemProperties: " + partitionEvent.getData().getSystemProperties());
				System.out.print("\n Event getPartitionKey: " + partitionEvent.getData().getPartitionKey());
				System.out.print("\n Event getBodyAsString: " + partitionEvent.getData().getBodyAsString());
				System.out.print("\n Event getProperties: " + partitionEvent.getData().getProperties());
			}

			System.out.println("\n done...");
		}
	}

}
