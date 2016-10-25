package com.iot.baobiao.rabbitmq;

import com.iot.baobiao.pojo.BaobiaoOrder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

/**
 * Created by jia on 2016/10/16.
 */
public class PaySuccessReceiver {

    private static Log log = LogFactory.getLog(PaySuccessReceiver.class);

    @Autowired
    private SimpMessageSendingOperations operations;

    public void handleMessage(BaobiaoOrder order) {
        log.info("收到来自RabbitMQ的消息:" + order.getOuttradeno());
        operations.convertAndSend("/topic/pay-result", order);
    }
}
