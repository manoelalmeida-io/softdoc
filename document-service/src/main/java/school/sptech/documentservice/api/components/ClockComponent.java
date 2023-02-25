package school.sptech.documentservice.api.components;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClockComponent {
  
  @Bean
  Clock clock() {
    return Clock.systemDefaultZone();
  }
}
