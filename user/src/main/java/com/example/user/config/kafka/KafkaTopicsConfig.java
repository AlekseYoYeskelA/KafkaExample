package com.example.user.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Конфигурация Kafka топиков.
 * <p>
 * Создает необходимые топики при запуске приложения, если они не существуют.
 * Гарантирует правильное количество партиций и реплик для каждого топика.
 */
@Configuration
public class KafkaTopicsConfig {

    @Value("${kafka.topics.user-create-command}")
    private String userCreateCommandTopic;

    @Value("${kafka.topics.user-update-command}")
    private String userUpdateCommandTopic;

    @Value("${kafka.topics.user-compensate-command}")
    private String userCompensateCommandTopic;

    @Value("${kafka.topics.user-created-event}")
    private String userCreatedEventTopic;

    @Value("${kafka.topics.user-update-response}")
    private String userUpdateResponseTopic;

    /**
     * Топик для команд создания пользователей.
     */
    @Bean
    public NewTopic userCreateCommandTopic() {
        return TopicBuilder.name(userCreateCommandTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    /**
     * Топик для команд обновления пользователей.
     */
    @Bean
    public NewTopic userUpdateCommandTopic() {
        return TopicBuilder.name(userUpdateCommandTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    /**
     * Топик для компенсирующих команд.
     */
    @Bean
    public NewTopic userCompensateCommandTopic() {
        return TopicBuilder.name(userCompensateCommandTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    /**
     * Топик для событий о создании пользователей.
     */
    @Bean
    public NewTopic userCreatedEventTopic() {
        return TopicBuilder.name(userCreatedEventTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    /**
     * Топик для ответов об обновлении пользователей.
     */
    @Bean
    public NewTopic userUpdateResponseTopic() {
        return TopicBuilder.name(userUpdateResponseTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}