package fr.michaelchlon.springboot;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WikimediaChangesProducer {

  private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);
  private KafkaTemplate<String, String> kafkaTemplate;

  // * INFO: ══ CONSTRUCTOR ═════════════════════════════════════════════════════
  public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  // ______________________________________________________________________
  public void sendMessage() throws InterruptedException {
    String topic = "wikimedia_recentchange";

    // To read real time string from wikimedia, we use event source
    EventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topic);
    String url = "https://stream.wikimedia.org/v2/stream/recentchange";
    EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
    EventSource eventSource = builder.build();
    eventSource.start();

    TimeUnit.MINUTES.sleep(10); // Keep the connection open for 10 seconds
  }
}
