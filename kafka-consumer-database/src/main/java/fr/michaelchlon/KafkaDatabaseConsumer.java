package fr.michaelchlon;

import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatabaseConsumer {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

    @KafkaListener(topics = "wikimedia_recentchange", groupId = "myGroup")
    public void consume(String eventMessage) {
        // Log the received message
        LOGGER.info(String.format("Event message received -> %s", eventMessage));

        // Here you would typically process the message and save it to the database
        // For example, you could parse the message and save it to a repository
        // repository.save(new Entity(eventMessage));

        // Simulate processing time
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOGGER.error("Error while processing eventMessage", e);
            Thread.currentThread().interrupt();
        }
    }

}
