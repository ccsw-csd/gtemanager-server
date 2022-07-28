# GTE Manager

Parte backend del aplicativo GTE Manager

## Instalación

### Dependencias de ejecución:

El módulo requiere la presencia de un JDK 1.11 y de una base de datos MySQL

### Compilación:

Para compilar el proyecto se utilizará el comando genérico de maven:
```Shell
$ mvn clean install
```  
Este comando lanzará la compilación del proyecto tras ejecutar los Test Unitarios de la aplicación.

### Ejecución:

Para arrancar la aplicación navegar hasta la calase anotada como @SpringBootApplication

* Hacer click con el botón derecho sobre la clase:
    * Run as >
        * Java application

## Seguridad

La seguridad esta basada el Token JWT, para generar un token válido se debe realizar una llamada POST a la URL del SSO:

* URL: http://ccsw.capgemini.com/sso/authenticate/

Como Body se adjunta las credenciales del SSO:

```json
{
    "username":"XXX",
    "password":"***"
}
```  
La respuesta provee del token necesario para realizar las llamadas pertinentes a los endpoint publicados.
