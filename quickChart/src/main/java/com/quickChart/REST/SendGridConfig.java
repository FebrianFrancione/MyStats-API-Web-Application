package com.quickChart.REST;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfig {

    @Value("${spring.sendgrid.api-key}")
    private String appkey;

    @Bean
    public SendGrid getSentGrid(){
        return new SendGrid(appkey);
    }
}
