########################################################################################################################
# 1. build
# docker build -t toowis/sandbox-study-servlet ./
#
# 2. check
# docker images
# REPOSITORY                                      TAG       IMAGE ID       CREATED         SIZE
# toowis/sandbox-study-servlet                    latest    9a863afea258   5 minutes ago   241MB
#
# 3. run
# docker run -d -p 8080:8080 --name toowis-servlet toowis/sandbox-study-servlet
#
# ps.
# docker stop toowis-servlet && docker rm toowis-servlet && docker rmi toowis/sandbox-study-servlet
########################################################################################################################
FROM tomcat:9.0.58-jre11-openjdk-slim-buster

# Set docker container timezone
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 기존 도커안의 tomcat의 루트 경로 삭제
#RUN rm -rf /usr/local/tomcat/webapps/ROOT

# 배포
COPY target/toowis.war /usr/local/tomcat/webapps

# 필요시 환경설정
#ENV JAVA_OPTS="-Dserver.type=dev"

# PORT
EXPOSE 8080