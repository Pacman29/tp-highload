FROM ubuntu:16.04

MAINTAINER Balyakin Danila

# Обвновление списка пакетов
RUN apt-get -y  update
#
# Сборка проекта
#

# Установка JDK
RUN apt-get install -y openjdk-8-jdk-headless
RUN apt-get install -y maven
RUN apt-get install -y tree

# Копируем исходный код в Docker-контейнер
ENV WORK /opt
ADD src $WORK/src/
ADD pom.xml $WORK/
ADD httptest $WORK/httptest

# Собираем и устанавливаем пакет
WORKDIR $WORK/
RUN ls -a
RUN mvn package
RUN tree
# Объявлем порт сервера
EXPOSE 80

CMD  java -jar $WORK/target/mail-1.0-SNAPSHOT-jar-with-dependencies.jar

