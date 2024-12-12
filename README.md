# TAREA 5 CC5002 - Desarrollo de aplicaciones web

estudiante: Jimmy Aguilera

Este readme es una explicación de las decisiones que tome en el diseño.

Si en el codigo todavia quedan prints, perdun :c
Hay algunas rutas de ApiController que solo use para ver como estaban los datos

## ADMIN

### Acceso a /admin

La ruta `/admin` está disponible exclusivamente para el usuario (admin) y la contraseña(tarea5cc5002) definidos en la tarea

### Login y Logout

- El sistema de **login** y **logout** utiliza la configuración por defecto de Spring Security
- Actualmente, cualquiera puede acceder a la página de login (`/login`), incluso si ya están autenticados como administrador. No supe evitar que un usuario autenticado pueda ingresar a login

### Botón para /admin

No implementé un botón visible para acceder a la ruta `/admin`, ya que considero que esta no debería ser visible para usuarios comunes

## Datos en /admin

Decidi no ordenarlo por fecha pero quedaron ordenados por id archivo , por lo que como el id es autoincremental esto hace que a menos que el archivo se le haya proporcionado un id y se haya ingresado, este estara ordenado, como la tabla archivo no tiene fecha preferi evitar ordenar por fecha.


## Eliminación de Archivos

### Decisiones Tomadas

Al momento de implementar la funcionalidad para eliminar archivos, tuve que tomar algunas decisiones complejas que detallo a continuación:

#### 1. Mantenimiento de Dispositivos sin Imagen
- **Problema**: Si se elimina el único archivo asociado a un dispositivo, ese dispositivo ya no se mostraría en la vista de dispositivos.
- **Decisión**: Decidí **no borrar el dispositivo asociado**, incluso si no tiene una imagen. Esto se debe a que el dispositivo sigue conteniendo información relevante que podría ser útil en el futuro
  - Por ejemplo, si se implementa un sistema de usuarios con autenticación en el futuro, podríamos mostrar al usuario logueado sus donaciones realizadas. En caso de que un dispositivo no tenga una imagen, podría aparecer con una advertencia para que el usuario cargue una imagen nueva acorde al dispositivo

#### 2. Acceso a Información de Dispositivos sin Imagen
- **Problema**: Los dispositivos sin imagen no aparecen en la lista de dispositivos, pero siguen siendo accesibles mediante la URL directa, como `informaciondispositivo/{id}`
- **Decisión**: Permití el acceso directo por URL (por ejemplo, `informaciondispositivo/1`), ya que no he implementado un sistema de autenticación con tokens que restrinja el acceso

#### 3. Eliminación de Imágenes del Directorio
- **Problema**: Decidir si la imagen asociada al archivo debe ser eliminada físicamente del directorio al eliminar un archivo
- **Decisión**: Opté por **no borrar la imagen del directorio**, considerando los siguientes puntos:
  - Si un dispositivo está en estado de advertencia y el usuario decide apelar la eliminación de la imagen, esta puede ser restaurada si la apelación es válida(en caso de que se validen los usuarios en un futuro y puedan ver sus donaciones)
  - El modo actual de crear dispositivos deja carpetas asociadas al ID del dispositivo, por lo que las imágenes podrían mantenerse en esas carpetas para referencia futura

#### 4. Implementar datos iniciales para muestra
- **Decisión**: No decidi dar datos iniciales como muestra, ya que tendria que implementar un metodo que añada directamente a la base de datos y no se si los auxs o el profe trabajan con una base de datos que ya tenga datos incluidos, en cambio es implemente las validaciones y metodos para ingresar la donacion, si esto es un problema contactarme

#### 5. Texto con el motivo
- **Problema**: Al presionar un boton donde poner el texto
- **Decisión**: Decidi ponerlo justo abajo del boton de eliminar, luego añadiendo el boton para confirmar o cancelar, esto fue complejo porque tuve que investigar como traer el formulario completo a una celda, pero se hizo, perdon si se ve feo el js de eso

## Gráficos

Los graficos que se pidieron en la tarea 3 estan implementados, sin embargo , decidi que la informacion en la que no hay archivo en una imagen luego de borrarlo se mantenga como informacion valida, ya que el dispositivo no fue borrado. asi que aunque no tenga archivo el dispositivo igual cuenta como dato

## validaciones de JS

Quise quitar las validaciones del js porque eran molestas para verificar las validaciones del backend, serian las mismas en la tarea 2 asi que no crei relevante ponerlas, todo esta validado por el backend

## Mensaje de confirmacion de envio del formulario

Lo quise quitar porque estuve agregando y quitando tantos dispositivos que me ocupaba mucho tiempo, asi que al presionar el boton de publicar donacion se envia directamente 

## Uso de DTO(data transfer object) para validaciones

En los formularios decidi usar DTO's para validar porque era mas facil y quedaban todos los errores dentro del bindingResult, el uso de @RequestParam funcionaba bien pero quedaba muy lleno cuando llamaba al formulario de donacion, fue una forma de dejarlo mas limpio tambien. 

## Errores no solucionados

Al momento de ingresar un archivo, aun no se como hacer para validar que el tamaño no exceda el maximo

Al momento de tener un error en el formulario de donacion, las imagenes no se como hacer para que queden guardadas, aunque el error no sea ahi no pude lograr eso

## Custom Error Controller

lo implemente para que no se me cayera la pagina con cada el error 404 y me mande al inicio


---



En caso de tener cualquier duda se me puede contactar al correo jimmyaguilera010699@gmail.com , la tarea la tengo subida a github asi que se puede demostrar si se necesita que no hay cambios posteriores en caso de que yo tenga que ejecutarlo en mi maquina
