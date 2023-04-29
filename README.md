# 202006359-GITT-PAT-practica-5


La estructura del proyecto es:

 - **/controller:** Paquete donde se almacenán las clases que representan la API-REST del microservicio
 - **/service:** Declaración de la capa de Servicio que es invocada desde la capa API (Controller)
 - **/service/impl:**  Implementación de la capa de servicios. En estas clases se implementa la capa de negocio de los microservicios
 - **/resources/static:** Contenido del HTML, JS y CSS del portal Web

## Instrucciones de uso
Limpiar dependencias
> mvn clean

Empaquetar microservicio (Jar file)

> mvn package

Ejecutar microservicio en una terminal

> mvn spring-boot-run

## Operaciones disponibles

El servicio actualmente implementa un servicio de llamada a una API, esta API lo que hace es devolver una imagen con el QR solicitado. Admite como parámetro en la URL (Query-param) el size *"160x160"* y el data *"https://github.com"*. Ambos campos son obligatorios, aunque el de size no lo puede tocar el usuario. Adicionalmente, tanto los parametros de entrada descritos anteriormente como la imagen recibida por la API, es guardada en una BBDD. Se recomienda seguir leyendo para más informacíon.

CrearQR *"CreateQR.html"*

> http://localhost:8888/icai/CreateQR?size=160x160&data=https://github.com

Ver estado del servicio
> http://localhost:8888/icai/CreateQR/health



## Funcionalidades extra
- **import org.springframework.web.client.RestClientException** Al importar esta clase, se pueden manejar los errores que pueden ocurrir al ejecutar las solicitudes HTTP y asegurar que la aplicación pueda manejar cualquier error de manera adecuada.
- **import org.springframework.web.bind.annotation.CrossOrigin** La anotación "CrossOrigin" (@CrossOrigin(origins = {"http://127.0.0.1:5500/","https://202006359.github.io"})) permite a los controladores de Spring configurar y manejar las solicitudes CORS, lo que puede ser útil para permitir que las aplicaciones web realicen solicitudes de origen cruzado y para evitar que las solicitudes sean bloqueadas por los navegadores, i.e. Chrome.
- **import lombok.extern.slf4j.Slf4j** Para tener un control en tiempo real de lo que va ocurriendo en la ejecución del programa. A través de las funciones log.info y log.error.
<img width="770" alt="image" src="https://user-images.githubusercontent.com/113789409/229900284-f44a1fe1-46d2-4f86-9af4-af36e35015e1.png">
 - **Automator icai/CreateQR/health** Me dice si el servicio de generar QR se ha levantado correctamente
> http://localhost:8888/icai/CreateQR/health
<img width="645" alt="image" src="https://user-images.githubusercontent.com/113789409/229899930-fd4d29b5-8e22-46a2-adaf-be429ccdb4a8.png">
- **¡¡Guardado de imagen en BBDD!!** En esta pratica se ha implementado el microservecio de guardarQR, donde se almacena un ID, el url de la web solicitada, el tamaño del QR y la imagen; almacenada como un byte [ ].
- **Anotacion Transactional** El servicio superior, ejecutarOperacion, está implemetando con lógica transacional desde que llamas a la API hasta que acabas de guardar la información en la BBDD, para poder deshacer los cambios en caso de error.
<img width="645" alt="image" src="https://user-images.githubusercontent.com/113789409/235310071-67bb9e42-669a-46a1-a02e-b45ed29b54d0.png">
- **Repositorio con @Query** El CrudRepository está implementado mediante una sentencia SQL de insert y select personalizada
<img width="645" alt="image" src="https://user-images.githubusercontent.com/113789409/235310087-fdb9a5e1-6580-4db2-8140-472a6190fa1c.png">
- **Test** Se han añadido dos clases de tipo Test, que implementan lógica de SpringBootTest y JUnit para verificar el correcto funcionamiento del controlador y los microservicios asociados a la llamada de la API y la persistencia en BBDD.
- **BBDD Schema**
<img width="645" alt="image" src="https://user-images.githubusercontent.com/113789409/235310286-213208b3-48fe-4a7d-ba43-5684addaea6a.png">

## Ejemplo visual 
<img width="645" alt="image" src="https://user-images.githubusercontent.com/113789409/229900083-7c4b519f-a4bb-42f5-aac1-3f5b04f42168.png">


