package mart.karle.jms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelloMessage implements Serializable {
  private static final long serialVersionUID = -4670559120294333686L;
  private UUID id;
  private String message;
}
