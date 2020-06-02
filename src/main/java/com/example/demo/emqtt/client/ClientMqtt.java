package com.example.demo.emqtt.client;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouwenquan
 * @date 2020/6/1 16:54
 * description 客户端
 */
@Component(value = "clientMqtt")
public class ClientMqtt {

    private final static Logger logger = LoggerFactory.getLogger(ClientMqtt.class);

    private static final String TOPIC = "MQTT";

    private static MqttClient client;

    private static MqttConnectOptions options;

    @Value("${mqtt.client.host}")
    public String host;
    @Value("${mqtt.client.clientid}")
    private String clientid;
    @Value("${mqtt.client.username}")
    private String username;
    @Value("${mqtt.client.password}")
    private String password;
    @Resource(name = "clientPushCallback")
    private ClientPushCallback clientPushCallback;

    @PostConstruct
    public void start(){
        try {

            //host: 服务器地址
            //clientid: 客户端ID(连接mqtt服务的唯一标识,用来区分不同的客户端)
            //MemoryPersistence: 设置clientid的保存形式, 默认以内存保存
            client = new MqttClient(host, clientid, new MemoryPersistence());
            options = new  MqttConnectOptions();
            options.setAutomaticReconnect(true);

            //设置是否清空session
            //true: 表示服务器会保留客户端的连接记录 , false: 表示每次连接诶到服务器都以新的身份连接
            options.setCleanSession(false);

            options.setUserName(username);
            options.setPassword(password.toCharArray());

            //设置连接超时时间
            options.setConnectionTimeout(10);

            //设置会话心跳时间
            options.setKeepAliveInterval(20);

            //设置回调
            client.setCallback(clientPushCallback);
            client.connect(options);

            //订阅消息
            subscribe();
        } catch (MqttException e) {
            logger.info("mqtt启动失败 -> ",e);
            e.printStackTrace();
        }
    }

    /**
     * 订阅消息
     */
    private static void subscribe(){
        int[] qos = {1};
        String[] topic = {TOPIC};
        try {
            client.subscribe(topic,qos);
        } catch (MqttException e) {
            logger.info("mqtt订阅消息失败 -> ",e);
            e.printStackTrace();
        }
    }

    synchronized void startReconnect(){
        if (!client.isConnected()){
            while (!client.isConnected()){
                logger.info("mqtt开始尝试重连");
                try {
                    TimeUnit.SECONDS.sleep(2);
                    client.connect(options);
                    subscribe();
                    logger.info("mqtt重新连接成功");
                    break;
                } catch (Exception e) {
                    logger.info("mqtt重连失败, 继续重连中..");
                    e.printStackTrace();
                }
            }
        }else {
            logger.info("mqtt已经连接，无需重连");
        }
    }
}
