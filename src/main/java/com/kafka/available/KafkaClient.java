package com.kafka.available;


/**
 * @Date May 22, 2015
 * @Author dengjie
 * @Note To run Kafka Code
 */
public class KafkaClient {
    public static void main(String[] args) {
        JConsumer con = new JConsumer("shuaige2");
        con.start();
    }

}
