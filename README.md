# CC5002 Desarrollo de Aplicaciones Web
## Tarea 1 - Donación de dispositivos electrónicos(sin backend)

_autor_: Jimmy Aguilera


### Validación

Me gustó la idea de implementar una validación dinámica en __agregar dispositivo__ puesto que cuando estaba introduciendo los datos me molestaba tener que recargar la pagina para probar cada caso, preferi hacerlo dinamicamente y asi veia los errores en tiempo real sin enviar el formulario antes.
Tambien para la validación cree funciones auxiliares con las cuales las validaciones eran mas faciles para campos comunes, esto con el fin de evitar repetir tanto codigo.

No quise agregar validación a descripción porque pienso que se puede agregar cualquier caracter ahi, como no guardé nunca la información de descripcion no me parecio relevante, aunque si lo uso en el futuro debere al menos evitar XSS

Un video que me motivo a hacer la validacion dinamica fue: 
[este](https://www.youtube.com/watch?v=h_nl7mHCL5c&t=2742s) las funciones auxiliares son idea de ese video, pero fue adaptado a la tarea, habia conflictos con el manejo de eventos asi que tuve que ponerle los eventos directamente al formulario para que hacer delegación de eventos.

Las expresiones regulares que use para el proyecto las fui creando luego de ver [esta pagina](https://lenguajejs.com/javascript/regexp/crear-expresiones-regulares/)

### CSS 

Quise separar el css para cada archivo porque me daban problemas y no se veian los cambios que queria realizar, separando el css en archivos para cada html hubieron menos errores, pero se que esto se podia evitar colocando bien las clases y manejando de mejor manera la estructura.

#### Visibilidad 

Para resoluciones mas bajas fije el tamaño de los inputs para que se adecuaran al width="100%"; esto para evitar que se vea tan feo en celulares o pantallas con poca resolucion, este cambio se aplica luego de bajar de 1150px
, me parecia un numero bonito porque para resoluciones mas altas que eso segun yo se veia bien.

### información dispositivo

Como eran datos que habia que ingresar uno hice que se mandaran como link a una seccion de la pagina, como en [este link](https://users.dcc.uchile.cl/~ahevia/html/links.html#:~:text=Para%20insertar%20un%20link%20en,d%C3%B3nde%20debe%20seguir%20el%20link).
y esos datos los fui mostrando con un formato json y accediendo a ellos en base id dado en la pagina anterior, este dato lo obtuve con ``window.location.hash.substring(1)``, que nos da el fragmento de la url despues del # y asi recuperaba el id.

Ademas aqui no quise poner validaciones dinamicas porque es mas opcional agregar un comentario o no, asi que si alguien queria agregar un comentario si o si tenia que presionar el boton de agregar comentario, donde si apareceria el mensaje de validacion

### Idioma de las variables

Tuve mucho conflicto con el nombre de las variables, usualmente prefiero poner todo en ingles, pero al estar leyendo en español la tarea se me hacia complicado leer en español y poner las variables y funciones en ingles, hay un poco mezclado de cada uno.

### ingregar varios dispositivos

Para esto tuve que darles unos id distintos a todos los datos por lo que los genere con la funcion generateUniqueId y los busque por el atributo name que si compartian en comun. Para asi poder validar luego con un for recorriendo la cantidad de dispositivos.