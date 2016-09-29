# Set the base image
FROM docker-hub.igitras.com/igitras/java8

add /application/git-blog/git-blog*.jar /application/git-blog/git-blog.jar

RUN mkdir /application/git-blog -p \
    && cd /application/git-blog \
    && mkdir log data tmp conf \

export 8080

CMD echo "Starting git-blog application" \
    && java -jar /application/git-blog/git-blog.jar