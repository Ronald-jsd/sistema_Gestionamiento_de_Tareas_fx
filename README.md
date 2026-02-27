# Sistema de Gestión de Tareas

## Descripción

Sistema de gestión de tareas desarrollado con JavaFX y Spring Boot. La aplicación permite crear, leer, actualizar y eliminar tareas, asignando responsables y estado a cada una de ellas. Ofrece funcionalidades adicionales para consultar la cantidad de tareas por estado.

## Características

- Crear nuevas tareas con nombre, responsable y estado
- Editar tareas existentes
- Eliminar tareas del sistema
- Visualizar todas las tareas en una tabla interactiva
- Filtrar tareas por estado (Pendiente, En Proceso, Finalizado)
- Consultar cantidad total de tareas
- Interfaz gráfica moderna y responsiva con JavaFX
- Integración con Spring Boot para gestión de inyección de dependencias
- Base de datos embebida H2 con persistencia

## Requisitos

- Java 17 o superior
- Maven 3.6 o superior
- Windows, macOS o Linux

## Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación principal
- **Spring Boot 3.5.10**: Framework para desarrollo de aplicaciones
- **JavaFX 21**: Framework para interfaz gráfica de usuario
- **Spring Data JPA**: ORM para acceso a datos
- **PostgreSQL**: Base de datos relacional
- **Lombok**: Generador de boilerplate code
- **Maven**: Gestor de dependencias y construcción

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/ronald/app/prueba/
│   │   ├── PruebaApplication.java          # Clase principal de la aplicación
│   │   ├── Controller/
│   │   │   └── IndexControlador.java       # Controlador JavaFX
│   │   ├── model/
│   │   │   ├── Tarea.java                  # Entidad de base de datos
│   │   │   └── Estado.java                 # Enumeración de estados
│   │   ├── presentation/
│   │   │   └── SistemaTareasFx.java        # Configuración de aplicación JavaFX
│   │   ├── repository/
│   │   │   └── TareaRepository.java        # Interfaz de acceso a datos
│   │   └── service/
│   │       ├── ITareaService.java          # Interfaz de servicio
│   │       └── TareaServiceImpl.java        # Implementación del servicio
│   └── resources/
│       ├── application.properties           # Configuración de la aplicación
│       ├── data.sql                         # Datos iniciales
│       ├── logback-spring.xml               # Configuración de logs
│       └── templates/
│           └── index.fxml                   # Vista principal JavaFX
└── test/
    └── java/com/ronald/app/prueba/
        └── PruebaApplicationTests.java      # Tests de la aplicación
```

## Instalación y Configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/sistema-tareas-javafx.git
cd sistema-tareas-javafx
```

### 2. Compilar el proyecto

```bash
mvn clean compile
```

### 3. Construir el proyecto

```bash
mvn clean package
```

## Uso

### Ejecutar desde Maven

```bash
mvn spring-boot:run
```

### Ejecutar desde línea de comandos

```bash
java -jar target/prueba-0.0.1-SNAPSHOT.jar
```

## Funcionalidades Detalladas

### Crear Tarea

1. Ingrese el nombre de la tarea en el campo "TAREA"
2. Ingrese el responsable en el campo "RESPONSABLE"
3. Seleccione el estado deseado en el combo "ESTADO"
4. Haga clic en el botón "Guardar"

### Editar Tarea

1. Seleccione una tarea de la tabla haciendo clic en ella
2. Modifique los valores en los campos del formulario
3. Haga clic en el botón "Editar"

### Eliminar Tarea

1. Seleccione una tarea de la tabla
2. Haga clic en el botón "Eliminar"
3. Confirme la eliminación en el diálogo

### Consultar Tareas por Estado

La aplicación proporciona botones de consulta rápida:
- "Tareas Pendientes": Muestra cantidad de tareas en estado PENDIENTE
- "Tareas en Proceso": Muestra cantidad de tareas en estado EN_PROCESO
- "Tareas Finalizadas": Muestra cantidad de tareas en estado FINALIZADO
- "Total de Tareas": Muestra cantidad total de tareas

### Estados Disponibles

- **PENDIENTE**: Tarea no iniciada
- **EN_PROCESO**: Tarea en ejecución
- **FINALIZADO**: Tarea completada

## Configuración

### Archivo application.properties

```properties
spring.application.name=gui-springboot-javafx

spring.datasource.url=jdbc:postgresql://localhost:5432/${DB_DATABASE:database}
spring.datasource.username=${DB_USER:postgres}
spring.datasource.password=${DB_PASSWORD:password}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

spring.main.web.application-type=none
spring.sql.init.mode=NEVER
```

La aplicación utiliza variables de entorno para la configuración de la base de datos. Puede configurarlas de la siguiente manera:

#### Variables de Entorno

- `DB_DATABASE`: Nombre de la base de datos PostgreSQL (por defecto: `database`)
- `DB_USER`: Usuario de PostgreSQL (por defecto: `postgres`)
- `DB_PASSWORD`: Contraseña de PostgreSQL (por defecto: `password`)

#### Configuración en Windows

Establezca las variables de entorno antes de ejecutar la aplicación:

```bash
set DB_DATABASE=su_base_datos
set DB_USER=su_usuario
set DB_PASSWORD=su_contraseña
mvn spring-boot:run
```

#### Configuración en Linux/macOS

```bash
export DB_DATABASE=su_base_datos
export DB_USER=su_usuario
export DB_PASSWORD=su_contraseña
mvn spring-boot:run
```

#### Configuración en el IDE

Si ejecuta desde su IDE (IntelliJ, Eclipse, etc.), puede configurar las variables de entorno en:
- **IntelliJ IDEA**: Run -> Edit Configurations -> Environment variables
- **Eclipse**: Run -> Run Configurations -> Environment

### Datos Iniciales

Los datos iniciales se cargan desde el archivo `data.sql` ubicado en `src/main/resources/`.

## Arquitectura

El proyecto sigue una arquitectura en capas:

- **Capa de Presentación**: `SistemaTareasFx.java`, `IndexControlador.java`
- **Capa de Servicio**: `ITareaService.java`, `TareaServiceImpl.java`
- **Capa de Repositorio**: `TareaRepository.java`
- **Capa de Modelo**: `Tarea.java`, `Estado.java`

## Logging

La aplicación utiliza SLF4J con Logback para gestionar logs. La configuración se encuentra en `logback-spring.xml`.

## Flujo de la Aplicación

1. `PruebaApplication.main()` inicia la aplicación
2. `SistemaTareasFx.init()` inicializa el contexto de Spring
3. `SistemaTareasFx.start()` carga la interfaz FXML
4. `IndexControlador` maneja todas las interacciones del usuario
5. El servicio `TareaServiceImpl` procesa la lógica de negocio
6. El repositorio `TareaRepository` persiste los datos

## Tratamiento de Errores

La aplicación implementa validaciones en:
- Campos de entrada (no pueden estar vacíos)
- Selección de tareas (validación de elementos seleccionados)
- Operaciones de base de datos (manejo de excepciones EntityNotFoundException)

## Mejoras Futuras

- Agregar funcionalidad de búsqueda y filtrado avanzado
- Implementar autenticación de usuarios
- Agregar exportación de tareas a PDF
- Implementar notificaciones de tareas vencidas
- Agregar soporte para múltiples usuarios
- Implementar sincronización con servidor remoto
- Agregar temas personalizables

## Contribuir

Las contribuciones son bienvenidas. Para contribuir:

1. Haga un fork del proyecto
2. Cree una rama para su característica (`git checkout -b feature/AmazingFeature`)
3. Haga commit de sus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abra un Pull Request

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulte el archivo LICENSE para más detalles.

## Contacto

Ronald - ronald.santi.jd@gmail.com

URL del Proyecto: [https://github.com/tu-usuario/sistema-tareas-javafx](https://github.com/tu-usuario/sistema-tareas-javafx)

## Resolución de Problemas

### La aplicación no inicia

Verifique que:
- Tiene Java 17 o superior instalado
- Maven está correctamente instalado
- Las variables de entorno JAVA_HOME están configuradas

### Error de conexión a base de datos

La aplicación requiere una instancia de PostgreSQL corriendo. Si experimenta problemas:
- Verifique que PostgreSQL está instalado y en ejecución
- Confirme que la base de datos `colegio_db` existe
- Verifique las credenciales en `application.properties` (usuario y contraseña)
- Asegúrese que el puerto 5432 está disponible
- Verifique los logs en `logs/` si están habilitados

### Interfaz gráfica no carga

Asegúrese de que:
- El archivo `index.fxml` existe en `src/main/resources/templates/`
- JavaFX está correctamente configurado en el pom.xml
- Los módulos de JavaFX están disponibles en su sistema

## Agradecimientos

- Spring Boot por proporcionar un framework robusto
- JavaFX por la interfaz gráfica moderna
- PostgreSQL Database por la base de datos fiable
- Lombok por reducir boilerplate code




