
FROM jboss/wildfly:11.0.0.Final

MAINTAINER "Jhon Fredy Hernadez" "fredyhc234@hotmail.com"

RUN /opt/jboss/wildfly/bin/add-user.sh admin admin#quasar2022 --silent

ADD standalone.xml /opt/jboss/wildfly/standalone/configuration/

COPY /postgresql/ /opt/jboss/wildfly/modules/system/layers/base/com/postgresql/

COPY /apidocs/ /opt/jboss/wildfly/welcome-content/apidocs

ADD service-quasar.war /opt/jboss/wildfly/standalone/deployments/

WORKDIR /opt/jboss/wildfly/

EXPOSE 8080 9990 8043

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]