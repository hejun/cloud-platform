FROM anapsix/alpine-java:8_server-jre
MAINTAINER HeJun hejun.web@qq.com

VOLUME /cloud/log

WORKDIR /cloud
ADD ./wait-for-it.sh ./wait-for-it.sh
ADD ./target/uac.jar ./
EXPOSE 9002

ENTRYPOINT ./wait-for-it.sh $NACOS_HOST:$NACOS_PORT -t 0 -- java \
                                                            -Dspring.profiles.active=$SPRING_PROFILE \
                                                            -Djava.security.egd=file:/dev/./urandom \
                                                            -Dfile.encoding=UTF8 \
                                                            -Duser.timezone=GMT+8 \
                                                            -jar uac.jar