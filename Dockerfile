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

# Копируем исходный код в Docker-контейнер
ENV WORK /opt
ADD src/ $WORK/src/
ADD pom.xml $WORK/
ADD httptest/ $WORK/

# Собираем и устанавливаем пакет
WORKDIR $WORK/
RUN mvn package

# Объявлем порт сервера
EXPOSE 80

CMD  java -jar $WORK/target/mail-SNAPSHOT-0.1-jar-with-dependencie.jar

