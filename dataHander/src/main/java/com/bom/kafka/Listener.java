package com.bom.kafka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/*
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
*/

import java.util.Optional;

public class Listener {
//    @Autowired
//    private DeviceDataService deviceDataService;
//
//    @Autowired
//    private MemCachedFactory mc;
    private Log log= LogFactory.getLog(Listener.class);

   /* //同时监听多个主题
    @KafkaListener(topics = {"testTopic1",
            "testTopic2",
            "testTopic3"
    })
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());

        String topic = record.topic();
        if (topic != null && !"".equals(topic)) {
            String message = kafkaMessage.get().toString();
           if(message!=null&&!"".equals(message)) {
               log.info(message);
               if (topic.equals("testTopic1")) {
                    //testTopic1主题来了数据，相应service处理方法
               } else if (topic.equals("testTopic2")) {
                    //testTopic2主题来了数据，相应service处理方法
               } else if (topic.equals("testTopic3")) {
                   //testTopic3主题来了数据，相应service处理方法
               } else {
                   log.info(message);
               }
           }
        }



    }*/
}