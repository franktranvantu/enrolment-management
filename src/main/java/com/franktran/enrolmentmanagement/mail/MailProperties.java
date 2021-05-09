package com.franktran.enrolmentmanagement.mail;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.mail")
@Data
public class MailProperties {

    private String host;
    private Integer port;
    private String username;
    private String password;

}
