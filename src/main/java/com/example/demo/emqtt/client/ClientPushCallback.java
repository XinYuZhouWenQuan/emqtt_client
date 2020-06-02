package com.example.demo.emqtt.client;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhouwenquan
 * @date 2020/6/1 17:17
 * description 客户端mq回调
 */
@Component("clientPushCallback")
public class ClientPushCallback implements MqttCallback {

    private final static Logger logger = LoggerFactory.getLogger(ClientPushCallback.class);

    @Resource(name = "clientMqtt")
    private ClientMqtt clientMqtt;

    /**
     * 连接断开时的回调
     * @param throwable throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        logger.error("连接断开, 正在尝试重新连接 -> ",throwable);
        clientMqtt.startReconnect();
    }

    /**
     * 接收消息时的回调
     * @param topic 主题
     * @param message 消息对象
     * @throws Exception exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        logger.info("接收消息主题: {}",topic);
        logger.info("接收消息Qos: {}",message.getQos());
        logger.info("接收消息内容: {}", new String(message.getPayload()));
    }

    /**
     * 消息发送成功时的回调
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        logger.info("消息发送成功: {}", token.isComplete());
    }
}
