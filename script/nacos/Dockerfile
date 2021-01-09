FROM nacos/nacos-server:1.4.0
MAINTAINER HeJun hejun.web@qq.com

WORKDIR //home/nacos
COPY wait-for-it.sh bin/wait-for-it.sh

ENTRYPOINT sh bin/wait-for-it.sh $MYSQL_SERVICE_HOST:$MYSQL_SERVICE_PORT -t 0 -- bin/docker-startup.sh