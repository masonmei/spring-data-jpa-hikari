package com.igitras.hikari.config.redis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.igitras.hikari.config.AppProperties;
import com.igitras.hikari.service.repos.RepositorySyncService;
import com.igitras.hikari.sync.RepositorySyncEvent;
import com.igitras.hikari.sync.message.RepositorySyncEventListener;
import com.igitras.hikari.sync.message.RepositorySyncEventPublisher;
import com.igitras.hikari.sync.message.RepositorySyncEventRedisPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

/**
 * Redis Related configuration.
 *
 * @author mason
 */
@Configuration
public class RedisConfiguration {

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Autowired
    private RepositorySyncService gitRepositorySyncService;

    @Autowired
    private AppProperties properties;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(gitSyncTaskListener(), gitSyncTopic());
        return container;
    }

    @Bean
    public RedisTemplate<String, RepositorySyncEvent> syncEventRedisTemplate() {
        RedisTemplate<String, RepositorySyncEvent> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RepositorySyncEventPublisher syncEventPublisher() {
        RepositorySyncEventRedisPublisher redisPublisher =
                new RepositorySyncEventRedisPublisher(syncEventRedisTemplate());
        redisPublisher.setTopic(gitSyncTopic());
        return redisPublisher;
    }

    private Topic gitSyncTopic() {
        return new ChannelTopic("git-repo-sync");
    }

    private MessageListener gitSyncTaskListener() {
        MessageListenerAdapter messageListener = new MessageListenerAdapter();
        RepositorySyncEventListener delegate = new RepositorySyncEventListener();
        delegate.setTopic(gitSyncTopic());
        delegate.setRedisTemplate(syncEventRedisTemplate());
        delegate.setGitRepositorySyncService(gitRepositorySyncService);
        delegate.setAppProperties(properties);
        messageListener.setDelegate(delegate);
        return messageListener;
    }
}
