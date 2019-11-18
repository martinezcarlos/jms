package mart.karle.jms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mart.karle.jms.config.JmsConfig;
import mart.karle.jms.model.HelloMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Sender {

  private final JmsTemplate jmsTemplate;
  private final ObjectMapper objectMapper;

  @Scheduled(fixedRate = 2000L)
  public void sendMessage() {
    //    System.out.println("Sending message...");
    final HelloMessage helloMessage =
        HelloMessage.builder().id(UUID.randomUUID()).message("Hello world!").build();
    jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, helloMessage);
    //    System.out.println("Message sent!");
  }

  @Scheduled(fixedRate = 2000L)
  public void sendAndReceiveMessage() throws JMSException {
    System.out.println("Sending message...");
    final HelloMessage helloMessage =
        HelloMessage.builder().id(UUID.randomUUID()).message("Hello").build();
    final Message received =
        jmsTemplate.sendAndReceive(
            JmsConfig.MY_SEND_RECEIVE_QUEUE,
            session -> {
              final Message textMessage;
              try {
                textMessage =
                    session.createTextMessage(objectMapper.writeValueAsString(helloMessage));
                textMessage.setStringProperty("_type", HelloMessage.class.getCanonicalName());
                System.out.println("Sending hello...");
                return textMessage;
              } catch (final JsonProcessingException e) {
                throw new JMSException("Failed");
              }
            });
    System.out.println(received.getBody(String.class));
  }
}
