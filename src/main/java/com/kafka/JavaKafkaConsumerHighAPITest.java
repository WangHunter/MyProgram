package com.kafka;

/**
 * Created by Administrator on 2017/8/22.
 */
/**
 * Created by ibf on 12/21.
 */
public class JavaKafkaConsumerHighAPITest {
    public static void main(String[] args) {
        String zookeeper = "200.200.18.128:2181";
        String groupId = "group1";
        String topic = "shauige2";
        int threads = 1;

        KafkaConsumer example = new KafkaConsumer(topic, threads, zookeeper, groupId);
        new Thread(example).start();

        // 执行10秒后结束
        int sleepMillis = 600000;
        try {
            Thread.sleep(sleepMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 关闭
        example.shutdown();
    }
}