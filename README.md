# Operación Fuego de Quasar

## 1. Arquitectura de solución
La arquitectura planteada para dar solución a la problematica es una arquitectura de microservicios, a continuación se muestra distintas vistas para mejorar su entendimiento.

## 1.2. Vista de arquitectura por capas
En la visualización por medio de capas se encuentra una definicion que permite entender la estructura general del proyecto, entendiendo que esta fue creada apartir de maven con una estructura de pom, la cual permite gestionar las dependencias de cada uno de los modulos reuqeridos para dar funcionalidad al servicio.

<imagen vista por capas>

Siguiendo los principios SOLID de programacion orientada objectos y buenas practicas de arquitecturas limpias: 
* Separacion por capas (data,business,service).
* Separar responsabilidades tanto para obtener el mensaje como la localización.
* Usar caracteristicas de herencia para manejar a traves de patrones de diseño (Patron Comando) para estructurar la forma de ejecutar una operación.
<patron comando imagen>
* Inversion de dependencias al impedir que un componente de una capa inferior acceda a una capa superior.
* Manejando excepciones para los componentes reuqeridos.


## 1.3. Vista de despliegue
El despliegue del microservicio se realiza en Google Cloud Plataform usando contenedores Docker para gestionar cada componente, tanto base de datos como backend.

 <imagen vista despliegue>

### 1.3.1. Enpoints
Se relaciona la dirección de la documentación por medio de la especificacion swagger de todos los enpoints disponibles para el manejo de a soulción.
http://34.125.24.35:8080/apidocs/#/


## 2. Instalación de microservicio
Para poder instalar el correspondiente proyecto se hace necesario por primera vez la ejecución del comando de docker-compose el cual genera el ambiente con dos contenedores, uno para unservidor wildfly y otro con un sistema gestor de bases de datos postgresql.

### 2.1 Construccion de composicion de postgres y wildfly
docker-compose up -d --build

### 2.2. Construir imagen de wildfly
docker build -q --rm --tag=jboss/wildfly:11-quasar .

### 2.3. Ejecutar creacion de contenedor
docker run -d -p 8080:8080 -p 8081:9990 -p 8082:8443 --name quasar-app -it jboss/wildfly:11-quasar
