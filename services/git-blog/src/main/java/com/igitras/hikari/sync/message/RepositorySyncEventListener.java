package com.igitras.hikari.sync.message;

import com.igitras.hikari.config.AppProperties;
import com.igitras.hikari.service.repos.GitRepositorySyncService;
import com.igitras.hikari.service.repos.GitRepositorySyncTaskContext;
import com.igitras.hikari.sync.RepositorySyncEvent;
import com.igitras.hikari.sync.RepositorySyncTaskConfig;
import com.igitras.hikari.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Repository Sync Event Listener.
 *
 * @author mason
 */
public class RepositorySyncEventListener implements MessageListener, InitializingBean {
    private static final Logger LOG = LoggerFactory.getLogger(RepositorySyncEventListener.class);

    private Topic topic;
    private GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
    private GitRepositorySyncService gitRepositorySyncService;
    private AppProperties properties;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(topic, "Topic must not be null.");
        Assert.notNull(gitRepositorySyncService, "Repository Sync Service must not be null.");
        Assert.notNull(properties, "App Properties must not be null.");
    }

    public RepositorySyncEventListener setAppProperties(AppProperties properties) {
        this.properties = properties;
        return this;
    }

    public RepositorySyncEventListener setTopic(Topic topic) {
        this.topic = topic;
        return this;
    }

    public RepositorySyncEventListener setSerializer(GenericJackson2JsonRedisSerializer serializer) {
        this.serializer = serializer;
        return this;
    }

    public RepositorySyncEventListener setGitRepositorySyncService(GitRepositorySyncService gitRepositorySyncService) {
        this.gitRepositorySyncService = gitRepositorySyncService;
        return this;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String topic = new String(pattern);
        if (!this.topic.getTopic()
                .equals(topic)) {
            return;
        }

        RepositorySyncEvent syncEvent = serializer.deserialize(message.getBody(), RepositorySyncEvent.class);
        try {
            dispatchEvent(syncEvent);
        } catch (FileNotFoundException e) {
            LOG.warn("Dispatch event failed.");
        }
    }

    private void dispatchEvent(RepositorySyncEvent syncEvent) throws FileNotFoundException {
        switch (syncEvent.getType()) {
            case UPDATE:
                // DO NOTHING NOW
                break;
            case CREATE:
                gitRepositorySyncService.addTask(buildTask(syncEvent));
                break;
            case RELOAD:
                break;
            case REMOVE:
                gitRepositorySyncService.removeTask(buildTask(syncEvent));
                break;
            default:
                break;
        }
    }

    private GitRepositorySyncTaskContext buildTask(RepositorySyncEvent syncEvent) throws FileNotFoundException {
        RepositorySyncTaskConfig config = syncEvent.getConfig();
        return new GitRepositorySyncTaskContext().setRefreshInterval(config.getRefreshInterval())
                .setRemoteUrl(config.getRepository())
                .setTargetFolder(buildTargetFolder(config.getRelativePath()));
    }

    private File buildTargetFolder(String relativePath) throws FileNotFoundException {
        File downloadFolder = FileUtil.resolveFolder(properties.getDownloadFolder());
        return new File(downloadFolder, relativePath);
    }
}
