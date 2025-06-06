# Alejandro_Mejias_Samuel_Moran_UT6UT7_NotasAPI
**ApiNotas**

API REST para Gestión de Usuarios y Notas
Con autenticación segura y manejo robusto de contraseñas

🔍 **Descripción**

ApiNotas es un backend desarrollado en Java con Spring Boot, pensado para administrar usuarios y sus notas personales.
Las contraseñas se almacenan con hashing SHA-256 para garantizar mayor seguridad.

El proyecto incluye dos versiones de controladores para usuarios:

v1: CRUD completo para usuarios (Crear, Leer, Actualizar, Eliminar)

v2: Endpoint dedicado exclusivamente para login/autenticación

🛠 **Tecnologías utilizadas**

Java 17

Spring Boot

Spring Data JPA

Hibernate Validator

Maven

Postman (para pruebas)

⚙️ **Configuración Base**

Base URL: http://localhost:8080

Headers obligatorios:

Content-Type: application/

📂 **Estructura del proyecto**

swift


src/main/java/com/alejandrosamuel/apinotas/
├── controller/
│   ├── v1/
│   │   ├── NotaController.java
│   │   └── UsuarioController.java
│   └── v2/
│       └── UsuarioControllerV2.java
├── exception/
│   └── GlobalExceptionHandler.java
├── model/
│   ├── Nota.java
│   └── Usuario.java
├── repository/
│   ├── NotaRepository.java
│   └── UsuarioRepository.java
├── service/
│   ├── AbstractCrudService.java
│   ├── CrudService.java
│   ├── NotaService.java
│   ├── NotaServiceImpl.java
│   ├── UsuarioService.java
│   └── UsuarioServiceImpl.java
└── ApinotasApplication.java

🚀 **Endpoints disponibles**

**Usuarios v1 (CRUD Completo)**

Método │      Ruta	   │Descripción

POST	/api/v1/usuarios	Crear un nuevo usuario

GET	/api/v1/usuarios	Listar todos los usuarios

GET	/api/v1/usuarios/{id}	Obtener usuario por ID

PUT	/api/v1/usuarios/{id}	Actualizar usuario

DELETE	/api/v1/usuarios/{id}	Eliminar usuario


**Usuarios v2 (Login/Autenticación)**

Método │      Ruta	   │Descripción

POST	/api/v2/usuarios/sign-in	Autenticación de usuario

**Notas**

Método │      Ruta	   │Descripción

POST	/api/v1/notas?usuarioId=	Crear nota para un usuario específico

GET	/api/v1/notas	Listar todas las notas

GET	/api/v1/notas?usuarioId=&order=	Listar notas filtradas y ordenadas

GET	/api/v1/notas/{id}	Obtener nota por ID

PUT	/api/v1/notas/{id}	Actualizar nota

DELETE	/api/v1/notas/{id}	Eliminar nota


**🧪 Uso típico con Postman**

**Crear usuario (v1)**

POST /api/v1/usuarios
{
  "nombre": "Juan Pérez",
  "email": "juan@email.com",
  "passwordHash": "123456"
}

La contraseña se procesa y se guarda automáticamente como hash SHA-256.

**Login (v2)**

POST /api/v2/usuarios/sign-in
{
  "email": "juan@email.com",
  "password": "123456"
}

🔄 **Flujo típico para probar la API**

1. Crear un usuario con POST /api/v1/usuarios

2. Autenticarse con POST /api/v2/usuarios/sign-in

3. Crear notas para el usuario autenticado

4. Listar, actualizar y eliminar notas según sea necesario

5. Actualizar o eliminar usuarios y verificar el comportamiento en cascada de las notas

**⚠️ Validaciones y manejo de errores**

-Validación detallada de datos con mensajes claros (email, nombre, contraseña)

* Código 400 para datos inválidos o email duplicado

* Código 401 para login con credenciales incorrectas

* Código 404 para recursos no encontrados

* Contraseñas almacenadas con hash seguro (SHA-256)

