package com.lierl.spider.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by lierl on 2017/9/2.
 */
public class JavaKafkaConsumer {

    private final static String TOPIC = "test";

    public static void main(String s[]) throws Exception{

//        Properties props = new Properties();

//        props.put("zookeeper.connect", "192.168.1.8:2181");
//        props.put("group.id", "0");
//        props.put("zookeeper.session.timeout.ms", "500");
//        props.put("zookeeper.sync.time.ms", "250");
//        props.put("auto.commit.interval.ms", "1000");
//        props.put("enable.auto.commit", "true");
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");


        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.1.8:9092");
        props.put("group.id", "0");
        props.put("enable.auto.commit", "true");
        // 从何处开始消费,latest 表示消费最新消息,earliest 表示从头开始消费,none表示抛出异常,默认latest
        props.put("auto.offset.reset", "earliest");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        Consumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList(TOPIC));

        boolean flag = true;
        while (flag) {
            //表示每1秒consumer就有机会去轮询一下订阅状态是否需要变更
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records){
                System.out.println(record.offset() + "---------" + record.value());

            }
        }

        consumer.close();
    }
}
