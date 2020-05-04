package springcloud.service;


import springcloud.config.MqConfig;
import com.atguigu.springcloud.entities.MiaoShaMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqSendService {
    @Autowired
    AmqpTemplate amqpTemplate;
    public void sendMiaoshaMessage(MiaoShaMessage miaoShaMessage){
        amqpTemplate.convertAndSend(MqConfig.MIAOSHA_QUEUE_NAME, miaoShaMessage);
    }
}
