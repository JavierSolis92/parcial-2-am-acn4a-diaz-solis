# INFORME - PARCIAL 2

**Materia:** Aplicaciones Móviles  
**Docente:** Sergio Medina  
**Comisión:** ACN4A  
**Integrantes:**
* Díaz, Fabrizio
* Solís, Javier

**Repositorio GitHub:**  
[https://github.com/JavierSolis92/parcial-2-am-acn4a-diaz-solis](https://github.com/JavierSolis92/parcial-2-am-acn4a-diaz-solis)

---

## Descripción de la Aplicación
Evolución del Producto Mínimo Viable (MVP) hacia una aplicación móvil completa de gestión de rutinas de entrenamiento físico.

La nueva versión incorpora un **sistema de autenticación seguro mediante Firebase**, persistencia de sesión de usuario, navegación multi-pantalla optimizada con un planificador semanal, vistas detalladas de rutinas específicas y un **temporizador interactivo de descanso** integrado para el control de los tiempos de entrenamiento.

---

## Flujo de Uso
1. **Autenticación (Login / Registro):** Al abrir la aplicación, el usuario se encuentra con la pantalla de inicio de sesión. Si no posee credenciales, puede registrarse mediante el módulo conectado a Firebase Authentication.
2. **Pantalla Principal (Planificador Semanal):** Una vez autenticado, el usuario accede al menú principal que le permite seleccionar el entrenamiento correspondiente al día (ej. *Lunes: Pecho, Hombro y Tríceps*, *Miércoles: Piernas*, *Viernes: Espalda y Bíceps*).
3. **Menú de Cuenta y Sesión:** Mediante la opción superior "Cuenta", se despliega un menú contextual que muestra el correo electrónico del usuario autenticado actualmente y la opción de cerrar sesión de manera segura.
4. **Vista de Detalle y Temporizador de Descanso:** Al ingresar a una rutina específica, la interfaz despliega una imagen representativa, la lista detallada de ejercicios y un temporizador dinámico de descanso de 1:30 minutos, el cual cambia de estado visual (de verde a gris y viceversa) al ser accionado por el usuario durante las series.

---

## Arquitectura y Componentes de Interfaz

### Layouts e Interfaz XML
* **`activity_main.xml` (Login):** Estructura basada en `ConstraintLayout` para el ingreso de credenciales de correo y contraseña conectadas a Firebase.
* **`activity_detail.xml` (Rutinas y Temporizador):** Diseñado con componentes modernos para albergar imágenes ilustrativas (`ImageView`), listados de ejercicios y la interfaz del temporizador con controles de estado dinámicos.

### Componentes de Lógica y Backend (`Java`)
* **`LoginActivity.java` y `RegisterActivity.java`:** Controladores encargados de la validación y comunicación asincrónica con Firebase Auth.
* **`MainActivity.java`:** Administra el panel de selección semanal y los menús flotantes de sesión.
* **`DetailActivity.java`:** Gestiona la lógica de visualización de cada rutina junto con la implementación del temporizador de descanso.

### Modularización de Recursos y Buenas Prácticas
* **`libs.versions.toml`:** Gestión centralizada de dependencias y versiones de Gradle (AGP 9.3.0).
* **`strings.xml` / `colors.xml` / `dimens.xml`:** Extracción completa de textos, paleta de colores con temática oscura uniforme y estandarización de dimensiones.

---

## Capturas de Pantalla 
* Pantalla de Inicio de Sesión / Registro
* Planificador Semanal y Menú Desplegable con el mail del usuario
* Detalle de Rutina y Temporizador de Descanso en funcionamiento