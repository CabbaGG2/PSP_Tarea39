# PSP_Tarea39: Herramientas de Seguridad

Este proyecto implementa un conjunto de herramientas básicas de seguridad informática en Java, incluyendo auditoría de hashes, un decodificador de cifrado César y un ataque de fuerza bruta multihilo.

## Características

-   **Auditoría de Hash:** Compara un hash objetivo con los hashes de palabras de un diccionario local.
-   **Decodificador César:** Descifra un texto codificado con el cifrado César, probando todos los posibles desplazamientos y validando las palabras resultantes con una API externa.
-   **Fuerza Bruta Multihilo:** Realiza un ataque de fuerza bruta en paralelo para encontrar una contraseña a partir de un hash objetivo, utilizando un pool de hilos para optimizar el proceso.

## Tecnologías Utilizadas

-   Java
-   Maven (para gestión de dependencias y construcción del proyecto)
-   Jackson (para el parseo de JSON en la validación de palabras)
-   Java HTTP Client (para las llamadas a la API externa)

## Prerrequisitos

-   Java Development Kit (JDK) 17 o superior.
-   Apache Maven 3.x.x.

## Cómo Compilar el Proyecto

Para compilar el proyecto, navega hasta la raíz del mismo y ejecuta el siguiente comando Maven:

```bash
/home/dam/.local/share/JetBrains/Toolbox/apps/intellij-idea-community-edition/plugins/maven/lib/maven3/bin/mvn clean install
```

Este comando limpiará el proyecto, compilará el código fuente y empaquetará la aplicación en un archivo JAR, además de instalarlo en tu repositorio Maven local.

## Cómo Ejecutar el Proyecto

Una vez compilado, puedes ejecutar la aplicación desde la línea de comandos. El archivo JAR ejecutable se encontrará en el directorio `target/`.

```bash
java -jar target/PSP_Tarea39-1.0-SNAPSHOT.jar
```

## Uso

Al ejecutar la aplicación, se te presentará un menú interactivo en la consola. Podrás elegir entre las siguientes opciones:

1.  **Auditoría de Hash contrastado con fichero.**
2.  **Decodificador CESAR con API REST.**
3.  **Fuerza bruta en paralelo con multihilos.**

Para salir de la aplicación, escribe `salir`.
