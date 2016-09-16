package com.igitras.hikari.config.redis;

import com.igitras.hikari.config.AppProperties;
import com.igitras.hikari.service.repos.GitRepositorySyncService;
import com.igitras.hikari.sync.message.RepositorySyncEventListener;
import com.igitras.hikari.sync.message.RepositorySyncEventPublisher;
import com.igitras.hikari.sync.message.RepositorySyncEventRedisPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

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
    private GitRepositorySyncService gitRepositorySyncService;

    @Autowired
    private AppProperties properties;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(gitSyncTaskListener(), gitSyncTopic());
        return container;
    }

    @Bean
    public RepositorySyncEventPublisher syncEventPublisher() {
        RepositorySyncEventRedisPublisher redisPublisher = new RepositorySyncEventRedisPublisher();
        redisPublisher.setConnectionFactory(connectionFactory);
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
        delegate.setGitRepositorySyncService(gitRepositorySyncService);
        delegate.setAppProperties(properties);
        messageListener.setDelegate(delegate);
        return messageListener;
    }
}
