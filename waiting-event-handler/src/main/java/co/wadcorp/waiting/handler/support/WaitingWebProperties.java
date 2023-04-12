package co.wadcorp.waiting.handler.support;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "waiting.web")
public class WaitingWebProperties {

  private String waitingUrl;
  private String restoreUrl;

}
