package com.igitras.hikari.sync;

/**
 * Repository Sync Task config.
 *
 * @author mason
 */
public interface RepositorySyncTaskConfig {

    String getRepository();

    String getRelativePath();

    Long getRefreshInterval();

    class BasicTaskConfig implements RepositorySyncTaskConfig {

        private String repository;
        private String relativePath;
        private Long refreshInterval;

        public BasicTaskConfig() {
        }

        public BasicTaskConfig(RepositorySyncTaskConfig config) {
            this.refreshInterval = config.getRefreshInterval();
            this.relativePath = config.getRelativePath();
            this.repository = config.getRepository();
        }

        public BasicTaskConfig setRepository(String repository) {
            this.repository = repository;
            return this;
        }

        public BasicTaskConfig setRelativePath(String relativePath) {
            this.relativePath = relativePath;
            return this;
        }

        public BasicTaskConfig setRefreshInterval(Long refreshInterval) {
            this.refreshInterval = refreshInterval;
            return this;
        }

        @Override
        public String getRepository() {
            return repository;
        }

        @Override
        public String getRelativePath() {
            return relativePath;
        }

        @Override
        public Long getRefreshInterval() {
            return refreshInterval;
        }
    }
}
