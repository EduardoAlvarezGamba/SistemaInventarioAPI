# SistemaInventarioAPI

API REST para la gestión de productos y categorías, desarrollada con Java y Spring Boot. El proyecto utiliza PostgreSQL para la persistencia de datos, Spring Data JPA para el acceso a la base de datos y Spring Security con JWT para la autenticación y autorización.

## Tecnologías utilizadas

- Java 25
- Spring Boot 4.1.1
- Maven
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Spring Security
- JWT
- Jakarta Bean Validation
- JUnit y pruebas automatizadas

## Funcionalidades

- Gestión de productos.
- Gestión de categorías.
- Persistencia de información en PostgreSQL.
- Validación de datos mediante DTO.
- Manejo centralizado de errores HTTP.
- Autenticación mediante JWT.
- Autorización basada en roles.
- Pruebas unitarias, de repositorio, de seguridad y de integración.

## Endpoints de productos

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/productos` | Listar productos |
| GET | `/productos/{id}` | Buscar producto por ID |
| POST | `/productos` | Registrar producto |
| PUT | `/productos/{id}` | Actualizar producto |
| DELETE | `/productos/{id}` | Eliminar producto |

## Endpoints de categorías

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/categorias` | Listar categorías |
| GET | `/categorias/{id}` | Buscar categoría por ID |
| POST | `/categorias` | Registrar categoría |
| PUT | `/categorias/{id}` | Actualizar categoría |
| DELETE | `/categorias/{id}` | Eliminar categoría |

## Autenticación JWT

La API utiliza Spring Security y JSON Web Tokens (JWT) para autenticar usuarios y controlar el acceso a los endpoints protegidos.

Iniciar sesión

Endpoint: POST /auth/login

Este endpoint recibe las credenciales del usuario y devuelve un token JWT si la autenticación es correcta.

Solicitud de ejemplo:

{
"username": "usuario",
"password": "usuario123"
}

Respuesta exitosa:

{
"token": "TOKEN_JWT_GENERADO"
}

Si las credenciales son incorrectas, el endpoint responde con 401 Unauthorized.

{
"mensaje": "Usuario o contraseña incorrectos"
}

Utilizar el token

Para acceder a los endpoints protegidos, envía el token en la cabecera HTTP:

Authorization: Bearer TOKEN_JWT_GENERADO

Roles y permisos

La aplicación utiliza los roles USER y ADMIN. Los permisos específicos dependen de las reglas definidas en la configuración de Spring Security.

No publiques tokens válidos, contraseñas reales ni claves secretas en el repositorio.

Los endpoints están protegidos por Spring Security. Los permisos dependen del método HTTP y del rol asignado al usuario. Para acceder, se debe enviar un token JWT válido cuando corresponda.
## Base de datos

El proyecto utiliza PostgreSQL y una base de datos llamada `sistema_inventario`.

Configura las credenciales de la base de datos en `application.properties` mediante valores apropiados para tu entorno local. No publiques contraseñas reales.

## Configuración de JWT

El proyecto requiere la variable de entorno `JWT_SECRET`, utilizada para configurar la clave de firma de los tokens JWT.

Configura una clave segura y compatible con el mecanismo de codificación que utiliza la aplicación. No publiques claves secretas ni tokens válidos en el repositorio.

## Ejecutar el proyecto

1. Instala Java 25 y PostgreSQL.
2. Configura la base de datos y sus credenciales.
3. Configura la variable de entorno `JWT_SECRET`.
4. Abre una terminal en la raíz del proyecto.
5. Ejecuta las pruebas:

   ```bash
   .\mvnw.cmd test
   ```

6. Inicia la aplicación desde IntelliJ IDEA o mediante Maven Wrapper, según la configuración del proyecto.

## Pruebas automatizadas

El proyecto cuenta con pruebas unitarias, de repositorios, de validación, de seguridad y de integración. En la última ejecución completa registrada, las 31 pruebas finalizaron correctamente, sin fallos ni errores.

## Arquitectura

El código está organizado en paquetes para separar las responsabilidades:

- `config`: configuración de la aplicación.
- `controller`: endpoints REST.
- `dto`: objetos para solicitudes y respuestas.
- `entity`: entidades persistidas en la base de datos.
- `exception`: manejo global de errores.
- `repository`: acceso a datos.
- `security`: autenticación y autorización.
- `service`: lógica de negocio.

## Estado del proyecto

Proyecto desarrollado como parte del proceso de aprendizaje de Java Backend, con énfasis en APIs REST, persistencia relacional, validación, seguridad y pruebas automatizadas.