package com.bochao.java;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public class SyncProducer {

    public static void main(String[] args) throws Exception {
        // 1- 创建一个 producer, 参数为 producer group 的名称
        DefaultMQProducer producer = new DefaultMQProducer("pg");
        // 2- 指定 nameServer 地址
        producer.setNamesrvAddr("192.168.110.228:9876");
        // 3- 设置发送失败重试次数， 默认为 2
        producer.setRetryTimesWhenSendFailed(3);
        // 4- 设置发送超时时限5s, 默认是3秒
        producer.setSendMsgTimeout(5000);

        // 5- 开启生产者
        producer.start();

        // 6- 发送消息
        for (int i = 0; i < 100; i++) {
            byte[] body = ("hello" + i).getBytes();
            Message message = new Message("someTopic", "someTag", body);
            // 为消息指定key
            message.setKeys("key-" + i);
            // 发送消息
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult);
        }

        // 关闭producer
        producer.shutdown();
    }
}
