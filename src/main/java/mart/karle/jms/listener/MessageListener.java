package mart.karle.jms.listener;

import lombok.RequiredArgsConstructor;
import mart.karle.jms.config.JmsConfig;
import mart.karle.jms.model.HelloMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

// @Component
@RequiredArgsConstructor
public class MessageListener {

  private final JmsTemplate jmsTemplate;

  @JmsListener(destination = JmsConfig.MY_QUEUE)
  public void listen(
      @Payload final HelloMessage helloMessage,
      @Headers final MessageHeaders headers,
      final Message message) {
    //    System.out.println("Got message...");
    //    System.out.println(helloMessage);
  }

  @JmsListener(destination = JmsConfig.MY_SEND_RECEIVE_QUEUE)
  public void listenForHello(
      @Payload final HelloMessage helloMessage,
      @Headers final MessageHeaders headers,
      final Message message,
      final org.springframework.messaging.Message springMessage)
      throws JMSException {
    final HelloMessage replyMessage =
        HelloMessage.builder().id(UUID.randomUUID()).message("World!").build();
    jmsTemplate.convertAndSend(message.getJMSReplyTo(), replyMessage);
  }
}
