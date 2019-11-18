package mart.karle.jms.listener;

import mart.karle.jms.config.JmsConfig;
import mart.karle.jms.model.HelloMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
public class MessageListener {
  @JmsListener(destination = JmsConfig.MY_QUEUE)
  public void listen(
      @Payload final HelloMessage helloMessage,
      @Headers final MessageHeaders headers,
      final Message message) {
    System.out.println("Got message...");
    System.out.println(helloMessage);
  }
}
