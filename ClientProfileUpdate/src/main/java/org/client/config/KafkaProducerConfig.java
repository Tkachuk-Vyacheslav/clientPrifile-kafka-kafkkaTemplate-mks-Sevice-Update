package org.client.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.client.common.dto.MoneyTransferDto;
import org.client.common.dto.TransferResultDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    private String kafkaServer="localhost:9092";

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${kafka.reply.topic}")
    private String replyTopic;

    @Bean  // producer
    public KafkaTemplate<String, TransferResultDto> replyTemplate(ProducerFactory<String, TransferResultDto> pf,
                                                                  ConcurrentKafkaListenerContainerFactory<String, TransferResultDto> factory) {
        KafkaTemplate<String, TransferResultDto> kafkaTemplate = new KafkaTemplate<>(pf);
         factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setReplyTemplate(kafkaTemplate);
        return kafkaTemplate;
    }

    @Bean
    public ReplyingKafkaTemplate<String, MoneyTransferDto, TransferResultDto> replyingKafkaTemplate(ProducerFactory<String, MoneyTransferDto> pf,
                                                                                                    ConcurrentKafkaListenerContainerFactory<String, TransferResultDto> factory) {
        ConcurrentMessageListenerContainer<String, TransferResultDto> replyContainer = factory.createContainer(replyTopic);
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId(groupId);
        return new ReplyingKafkaTemplate<>(pf, replyContainer);
    }


}
