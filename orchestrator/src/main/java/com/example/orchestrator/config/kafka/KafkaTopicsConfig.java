package com.example.orchestrator.config.kafka;

import lombok.Getter;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Конфигурация топиков Kafka для сервиса оркестратора.
 * <p>
 * Определяет топики для отправки команд и приёма событий/ответов.
 */
@Getter
@Configuration
public class KafkaTopicsConfig {

    @Value("${kafka.topics.user-create-command}")
    private String userCreateCommandTopic;

    @Value("${kafka.topics.user-update-command}")
    private String userUpdateCommandTopic;

    @Value("${kafka.topics.user-compensate-command}")
    private String userCompensateCommandTopic;

    // Геттеры для использования в SpEL
    @Value("${kafka.topics.user-created-event}")
    private String userCreatedEventTopic;

    @Value("${kafka.topics.user-update-response}")
    private String userUpdateResponseTopic;

    @Bean
    public NewTopic userCreateCommandTopic() {
        return TopicBuilder.name(userCreateCommandTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic userUpdateCommandTopic() {
        return TopicBuilder.name(userUpdateCommandTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic userCompensateCommandTopic() {
        return TopicBuilder.name(userCompensateCommandTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic userCreatedEventTopic() {
        return TopicBuilder.name(userCreatedEventTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic userUpdateResponseTopic() {
        return TopicBuilder.name(userUpdateResponseTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}