package mart.karle.jms.sender;

import lombok.RequiredArgsConstructor;
import mart.karle.jms.config.JmsConfig;
import mart.karle.jms.model.HelloMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Sender {

  private final JmsTemplate jmsTemplate;

  @Scheduled(fixedRate = 2000L)
  public void sendMessage() {
    System.out.println("Sending message...");
    final HelloMessage helloMessage =
        HelloMessage.builder().id(UUID.randomUUID()).message("Hello world!").build();
    jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, helloMessage);
    System.out.println("Message sent!");
  }
}
