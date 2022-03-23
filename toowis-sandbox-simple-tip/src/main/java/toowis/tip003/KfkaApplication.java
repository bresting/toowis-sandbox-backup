package toowis.tip003;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class KfkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(KfkaApplication.class, args);
    }
    
    @Bean
    CommandLineRunner demo() {
        return args -> {
            System.out.println("실행됨...");
        };
    }
    
    /**
     * kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 3 --partitions 3 --topic sandbox-events
     * 
     * @param consumerFactory
     * @return
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(1);
        
        ContainerProperties property = factory.getContainerProperties();
        property.setAckMode(AckMode.MANUAL);
        
        return factory;
    }
}

@RestController
class MyController {
    
    @Autowired
    Producer producer;
    
    @GetMapping(value="/kafka/post")
    public void sendMessage(@RequestParam("msg") String msg) {
        producer.publishToTopic(msg);
    }
}

@Service
class Producer {
    
    public static final String topic = "sandbox-events";
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    public void publishToTopic(String message) {
        System.out.println("publish to topic : " + message);
        this.kafkaTemplate.send(topic, message).addCallback(
            succss -> {
                System.out.println("===== 성공 =====");
                System.out.println(succss);
            },
            failer -> {
                System.out.println("===== 실패 =====");
                System.out.println(failer.getMessage());
            }
        );
    }
}

@Service
class Consumer {
    
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
    
    // @KafkaListener(topics = "sandbox-events", groupId = "mygroup")
    // public void consumerFromTopic(String message) {
    //     System.out.println(Thread.currentThread().getId() + " : 1 consumer message : " + message);
    // }
    
    @KafkaListener(topics = "sandbox-events", groupId = "mygroup")
    public void consumerFromTopic(String message
            , @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment
            , @Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY, required = false) String key
            , @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition
            , @Header(KafkaHeaders.OFFSET) long offset
            , @Header(KafkaHeaders.RECEIVED_TOPIC) String topic
            , @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts
    ) {
        logger.info("[consumer] Consumed key: {}, partition: {}, offset: {}, topic: {}, ts: {}, message : {}",
                key, partition, offset, topic, ts, message);
    
        System.out.println(Thread.currentThread().getId() + " : 1 consumer message : " + message);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //acknowledgment.acknowledge();
    }
}

