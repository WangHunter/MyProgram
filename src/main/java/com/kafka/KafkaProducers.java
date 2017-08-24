package com.kafka;

/**
 * Created by Administrator on 2017/8/21.
 */
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

public class KafkaProducers {

    private static final String TOPIC = ResourcesManager.getProp("kafka.topic"); //kafka创建的topic

    public static void sendMsg(String msg) {
            Producer<String, String> producer = (Producer<String, String>) new KafkaTools().getKafka();
            KeyedMessage<String, String> message =
                    new KeyedMessage<String, String>(TOPIC, msg);
            producer.send(message);
    }
}
