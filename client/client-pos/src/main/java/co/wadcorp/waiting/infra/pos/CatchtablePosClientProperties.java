package co.wadcorp.waiting.infra.pos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "client.pos")
public class CatchtablePosClientProperties {

    private String host;
}
