package com.igitras.hikari.sync.message;

import static org.springframework.util.Assert.notNull;

import com.igitras.hikari.sync.RepositorySyncEvent;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

/**
 * Repository Sync Event publisher with redis implementation.
 *
 * @author mason
 */
public class RepositorySyncEventRedisPublisher implements RepositorySyncEventPublisher, InitializingBean {

    private final RedisTemplate<String, RepositorySyncEvent> template;

    private RedisConnectionFactory connectionFactory;

    private Topic topic;

    public RepositorySyncEventRedisPublisher() {
        this(new RedisTemplate<>());
    }

    public RepositorySyncEventRedisPublisher(RedisTemplate<String, RepositorySyncEvent> template) {
        notNull(template, "RedisTemplate must not be null.");
        this.template = template;
    }

    public RepositorySyncEventRedisPublisher setTopic(Topic topic) {
        this.topic = topic;
        return this;
    }

    public RepositorySyncEventRedisPublisher setConnectionFactory(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        return this;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.template.setConnectionFactory(connectionFactory);
        this.template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        this.template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        this.template.afterPropertiesSet();
    }


    @Override
    public void publish(RepositorySyncEvent event) {
        template.convertAndSend(topic.getTopic(), event);
    }
}
