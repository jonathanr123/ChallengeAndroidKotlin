# ChallengeAndroidKotlin
Challenge Android Kotlin propuesto por Alkemy
Objetivos Cumplidos:
👉La app deberá tener una pantalla principal en donde se muestre una lista de
películas populares (ver The Movie Database API) con sus respectivos títulos y
portadas.
👉Cada una de esas películas podrá ser seleccionada y se desplegará una
nueva vista con los detalles de la misma (género, idioma original, popularidad,
fecha de estreno, entre otras). Para ello, consultar el siguiente endpoint: The
Movie Database API.
👉En caso de que alguna consulta falle o algún listado esté vacío deberán
mostrarse los correspondientes errores en pantalla.

Objetivos Adicionales que fueron cumplidos:
● Dado que el endpoint de películas populares es paginado, la aplicación
podrá consultar por nuevas a medida que el usuario navegue entre estas.
Es decir, que al llegar al final de la lista que se muestra en pantalla, la
aplicación busque nuevo contenido en la API.
● Agregar un campo de búsqueda, que permita filtrar aquellas películas que
contengan dicho texto. La búsqueda deberá realizarse entre el listado que
se encuentra visible, si no hay resultado satisfactorio, deberá mostrarse su
error correspondiente.
● La API posee un endpoint para evaluar una película determinada (ver The
Movie Database API). Agregar en la vista de detalle la posibilidad de
evaluar una película y actualizar la API con dicha información.
● Para aumentar la performance de la aplicación, se busca evitar la consulta
continua de una misma película. Por este motivo, la aplicación deberá
almacenar los detalles de las películas ya vistas. En caso de que el usuario
seleccione una de ellas se consultará dicha información guardada en el
dispositivo, caso contrario, deberá consultar a la API correspondiente.

PRUEBAS:
Pruebas realizadas en emulador Genymotion con Xiaomi Redmi Note 7 (Android 9.0 - API 28).
Pruebas realizadas en dispositivo físico con Xiaomi Redmi Note 7 (Android 10).

SDK mínimo: API 22 - Android 5.1 (Lollipop)

## Demo
<img src="https://github.com/jonathanr123/ChallengeAndroidKotlin/blob/master/assets/movieApp_2.jpg" width=80%>
<img src="https://github.com/jonathanr123/ChallengeAndroidKotlin/blob/master/assets/movieApp_1.jpg" width=80%>
