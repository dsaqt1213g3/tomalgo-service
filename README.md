<PRE> --------------------------------------------------------------------------------------------------- 
|  -------------    -------     ----    ----     --------     ---          ----------     -------    |
| |             | /    _    \  |     \/     |  /    /\    \  |   |       /    _______|  /    _    \  |
|  ----     ---- |    / \    | |            | |    /  \    | |   |      |   /          |    / \    | |
|      |   |     |   |   |   | |    \  /    | |    \  /    | |   |      |   |    ----  |   |   |   | |
|      |   |     |    \_/    | |   | -- |   | |   | -- |   | |   -----  |   |___\    | |    \_/    | |
|      |   |      \         /  |   |    |   | |   |    |   | |        |  \          /   \         /  |
|      |___|        -------     ---      ---   ---      ---   --------     --------       -------    |
 ---------------------------------------------------------------------------------------------------- </PRE> 

# Configuración de la base de datos:

1. Crear una base de datos en mysql llamada Tomalgo y darle persmisos a un usuario todos los persmisos sobre esa
   base de datos.

2. Una vez tenemos la base de datos creada ejecutar el archivo tomalgo_data.sql o tomalgo.sql que se encuentran
   en la carpeta de sql dependiendo si queremos una base de datos con algunos datos o la queremos vacia.
    
# Configuración del servicio:

1. Abrir el archivo WebContent/META-INF/context.xml y editar los valores de username="user" password="password" y poner los
   que se han utilizado para la base de datos.
   
2. Exportar el proyecto como WAR y añadirlo al servicor de tomcat. El path del servicio es:
						
				 localhost:puerto_tomcat/tomalgo-service/ServiceServlet?
				 
				 
			 
<h1>Ejemplos de operaciones que realiza el servidor:</h1>

Register (Cliente y empresa)<br/>
action=register&username=alicia&password=394ED584A2984E057FFC59F99FAB7EACDA4B6C7D&mail=alicia@foo.com&enterprise=0&birth=1990-12-12<br/>
action=register&username=El%20pais%20de%20la%20birra&password=394ED584A2984E057FFC59F99FAB7EACDA4B6C7D&mail=birra@foo.com&enterprise=1&street=Mas%20cerca%20de%20lo%20que%20tu%20crees<br/>

Decline<br/>
action=decline&password=EC3E661D7BC7BFBF5334E7DFAD309F947DACE5F7

CheckUser<br/>
action=checkuser&username=fernando

CheckUsername<br/>
action=checkusername&username=fernando

Login (Cliente y empresa)<br/>
action=login&username=tomalgo&password=394ED584A2984E057FFC59F99FAB7EACDA4B6C7D
action=login&username=fernando&password=EC3E661D7BC7BFBF5334E7DFAD309F947DACE5F7

Logout<br/>
action=logout

ChangePassword (Cliente y empresa)<br/>
action=changepassword&oldpassword=EC3E661D7BC7BFBF5334E7DFAD309F947DACE5F7&newpassword=2C6D680F5C570BA21D22697CD028F230E9F4CD56
action=changepassword&oldpassword=2C6D680F5C570BA21D22697CD028F230E9F4CD56&newpassword=EC3E661D7BC7BFBF5334E7DFAD309F947DACE5F7

UpdateTags<br/>
action=updatetags&tags=[nocturno,jawaiano,copas,jawaiano]

SendEvent<br/>
action=sendevent&text=esto%20es%20una%20prueva%20de%20un%20evento&inidate=2012-12-28%2023:06&enddate=2012-12-28%2023:06&isevent=0

action=queryevents&password=394ED584A2984E057FFC59F99FAB7EACDA4B6C7D
action=queryevents&password=EC3E661D7BC7BFBF5334E7DFAD309F947DACE5F7

(respuesta: {"status":"OK","result":[{"id":1,"enterprise":"tomalgo","text":"copas gratis en tomamos algo","inidate":"2012-01-05 13:49:54.0",
"enddate":"2012-01-23 13:49:58.0","promo":false}]})

QueryOldEvents<br/>
action=queryevents&password=EC3E661D7BC7BFBF5334E7DFAD309F947DACE5F7

QueryAssists<br/>
action=queryassists&password=394ED584A2984E057FFC59F99FAB7EACDA4B6C7D&event=1 
