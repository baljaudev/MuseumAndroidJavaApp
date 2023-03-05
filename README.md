# Enunciado - Android - Museos de Madrid

## API REST 

Los datos se obtienen de la API REST datos.madrid.es, que es un servicio que ofrece diferentes conjuntos de datos que publica el Ayuntamiento de Madrid. 


•	El funcionamiento de la pantalla es el siguiente:

-	Si se pulsa el botón Seleccionar Filtro , se eliminará el fragmento en el que se muestran los resultados de las consultas, si es que se hubiera realizado ya alguna, se borrará el filtro, si es que se había seleccionado ya alguno, y se accederá a un fragment dialog desde el que se podrá seleccionar un distrito (ver [Selección de Filtro](#selección-filtro)).

-	Si se pulsa el botón Consultar Listado (modo de visualización [Listado](#fragmento-de-consulta-modo-de-visualización-listado)) sin haber seleccionado un filtro, se cargará un fragmento (ver [Fragmento de Consulta](#fragmento-de-consulta-modo-de-visualización-listado)), que mostrará en un RecyclerView los nombres de los museos.

-	Si se pulsa el botón Consultar Listado (modo de visualización Listado) habiendo seleccionado un [distrito](#selección-filtro), se cargará un fragmento (ver Fragmento de Consulta) que mostrará en un RecyclerView los nombres de los museos que se encuentren en dicho distrito.


## Selección Filtro 

•	El cuadro de diálogo para seleccionar el filtro deberá tener un aspecto similar al siguiente: 

•	La ventana de selección de filtro deberá contener: 
-	Un Spinner para seleccionar el distrito. El spinner se deberá cargar con los siguientes distritos:

ARGANZUELA
BARAJAS
CARABANCHEL
CENTRO
CHAMARTIN
CHAMBERI
CIUDAD LINEAL
FUENCARRAL-EL PARDO
HORTALEZA
LATINA
MONCLOA-ARAVACA
MORATALAZ
PUENTE DE VALLECAS
RETIRO
SALAMANCA
SAN BLAS-CANILLEJAS
TETUAN
USERA
VICALVARO
VILLA DE VALLECAS
VILLAVERDE



•	El funcionamiento de la pantalla deberá ser así:

-	Si se pulsa el botón Cancelar, volverá a la pantalla de consulta sin retornar el filtro.
-	Si se pulsa el botón Aceptar, se comunicará a la pantalla de consulta el distrito seleccionado.





## Opción Mapa (Menú)

•	Si en el menú de la pantalla principal se selecciona la opción Mapa, se eliminará el fragmento en el que se muestran los resultados de las consultas, si es que se hubiera realizado ya alguna, se borrará el filtro, si es que se había seleccionado ya alguno, y se cambiará el texto del botón de Consulta a Consultar Mapa.

•	En la pantalla principal, si está seleccionado el modo de visualización Mapa, al pulsar el botón Consultar Mapa, se cargará un fragmento (ver [Fragmento de Consulta](#fragmento-de-consulta-modo-de-visualización-mapa)), que mostrará en un Mapa con la localización de los museos, información obtenida al realizar una consulta al webservice con la url correspondiente, dependiendo de si se ha seleccionado o no un filtro.



## Fragmento de Consulta (modo de visualización Listado)

-	El fragmento en el que se mostrarán los resultados de las consultas realizadas en modo de visualización Listado, deberá contener un RecyclerView en el que se mostrarán únicamente los nombres de los museos. La información se obtendrá al realizar una consulta al webservice dependiendo de si se ha seleccionado filtro o no.

-	Si no se ha seleccionado filtro, el webservice estará representado por la url:

https://datos.madrid.es/egob/catalogo/201132-0-museos.json

-	Si se ha seleccionado filtro, el webservice estará representado por la url:

https://datos.madrid.es/egob/catalogo/201132-0-museos.json?distrito_nombre=distrito


•	El funcionamiento de la pantalla deberá ser así:

-	Si se pulsa sobre uno de los museos que aparecen en el listado se accederá a un activity de Detalle (ver [Visualizar Detalle](#visualizar-detalle)) donde se mostrará más información acerca del museo seleccionado.

## Visualizar Detalle

•	Deberá mostrarse en un activity el nombre del museo, el distrito, el área, la dirección, el código postal, la localidad, la descripción de la organización y el horario. Formato libre.

•	La información que mostrar se deberá obtener de realizar una consulta a un webservice representado por la url:

https://datos.madrid.es/egob/catalogo/tipo/entidadesyorganismos/{museo}

Donde el museo será el nombre del fichero json del id del museo seleccionado en el listado.

Por ejemplo, si se ha pulsado sobre un museo con id:

"@id": "https://datos.madrid.es/egob/catalogo/tipo/entidadesyorganismos/4949641-casita-museo-raton-perez.json"
	
Se deberá extraer el nombre del fichero para montar la url en el APIRestService.

4949641-casita-museo-raton-perez.json


## Fragmento de Consulta (modo de visualización Mapa)

-	El fragmento en el que se mostrarán los resultados de las consultas realizadas en modo de visualización Mapa, deberá contener un Map en el que se mostrarán un marcador en cada una de las localizaciones de los museos. La información se obtendrá al realizar una consulta al webservice dependiendo de si se ha seleccionado filtro o no.

-	Si no se ha seleccionado filtro, el webservice estará representado por la url:

https://datos.madrid.es/egob/catalogo/201132-0-museos.json

-	Si se ha seleccionado filtro, el webservice estará representado por la url:

https://datos.madrid.es/egob/catalogo/201132-0-museos.json?distrito_nombre=distrito
