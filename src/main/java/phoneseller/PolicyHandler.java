package phoneseller;

import phoneseller.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    MarketingRepository MarketingRepository;


    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayCompleted_PayComplete(@Payload PayCompleted payCompleted){
        System.out.println("marketing_policy_wheneverPayCompleted_PayComplete");
        System.out.println(payCompleted.toJson());
        if(payCompleted.isMe()){
            System.out.println("결제 완료 후 포인트 제공하기 위해 어쩌구ㅜ 비동기ㅠ");

            Marketing marketing = new Marketing();
            marketing.setOrderId(payCompleted.getOrderId());
            marketing.setPoint((double) 300000);
            MarketingRepository.save(marketing);

            System.out.println("##### listener PayComplete : " + payCompleted.toJson());
        }
    }

}
