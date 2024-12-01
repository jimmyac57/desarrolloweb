import pymysql
import json
from datetime import datetime

DB_NAME = "tarea2"
DB_USERNAME = "cc5002"
DB_PASSWORD = "programacionweb"
DB_HOST = "localhost"
DB_PORT = 3306
DB_CHARSET = "utf8"

with open('database/querys.json', 'r') as querys:
	QUERY_DICT = json.load(querys)
# -- conn ---

def get_conn():
    try:
        conn = pymysql.connect(
            db=DB_NAME,
            user=DB_USERNAME,
            passwd=DB_PASSWORD,
            host=DB_HOST,
            port=DB_PORT,
            charset=DB_CHARSET
        )
        return conn
    except pymysql.Error as e:
        print(f"Error al conectar a la base de datos: {e}")
        return None




def get_regiones():
    conn = get_conn()
    cursor = conn.cursor()
    cursor.execute("SELECT id, nombre FROM region;")
    regiones = [{"id": row[0], "nombre": row[1]} for row in cursor.fetchall()]
    return regiones

def get_comunas(region_id):
    conn = get_conn()
    cursor = conn.cursor()
    cursor.execute("SELECT nombre, region_id from comuna where region_id=%s",(region_id,))
    comunas = [row[0] for row in cursor.fetchall()]
    return comunas


def insert_contact(name,email,phone,comuna_id,fecha):
    conn = get_conn()
    cursor = conn.cursor()
    cursor.execute(QUERY_DICT["insert_contact"], (name, email, phone,comuna_id,fecha))
    conn.commit()

def insert_device(contactId,deviceName,descripcion, tipo,uso,estado):
    conn = get_conn()
    cursor = conn.cursor()
    cursor.execute(QUERY_DICT["insert_device"], (contactId,deviceName,descripcion, tipo, uso,estado))
    conn.commit()

def insert_file(path,filename,deviceId):
    conn = get_conn()
    cursor = conn.cursor()
    cursor.execute(QUERY_DICT["insert_file"], (path,filename,deviceId))
    conn.commit()

def get_contact_id(nombre,email,comuna_id,fecha_creacion):
    conn = get_conn()
    cursor = conn.cursor()
    cursor.execute(QUERY_DICT["get_contact_id"], (nombre,email,comuna_id,fecha_creacion))
    
    result = cursor.fetchone()
    if result:  
        return result[0] 
    else:
        return None 


def mapear_tipo(tipo_id):
    valores_tipo = {
    1: "pantalla",
    2: "notebook",
    3: "tablet",
    4: "celular",
    5: "consola",
    6: "mouse",
    7: "teclado",
    8: "impresora",
    9: "parlante",
    10: "audífonos",
    11: "otro"
    }
    tipo_id = int(tipo_id)
    return valores_tipo.get(tipo_id)

def mapear_estado(estado_id):
    valores_estado = {
    1: "funciona perfecto",
    2: "funciona a medias",
    3: "no funciona"
    }
    estado_id = int(estado_id)
    return valores_estado.get(estado_id)
    
def get_device_id(contact_id,deviceName,descripcion, tipo_id,uso,estado_id):
    conn = get_conn()
    cursor = conn.cursor()
    estado=mapear_estado(estado_id)
    tipo = mapear_tipo(tipo_id)
    cursor.execute(QUERY_DICT["get_device_id"], (contact_id,deviceName,descripcion,tipo,uso,estado))
    result = cursor.fetchone()
    if result:  
        return result[0] 
    else:
        return None 
    
def validate_region_db(region_id):
    if not region_id:
        return "La region debe ser seleccionada"
    conn = get_conn()
    cursor = conn.cursor()
    cursor.execute("SELECT id, nombre FROM region WHERE id= %s ;",(region_id,))
    result = cursor.fetchone()
    if result:  
        return "" 
    else:
        return "La región seleccionada no existe" 
    
def validate_comuna_db(region_id,comuna_id):
    if not region_id or not comuna_id:
        return "La comuna debe ser seleccionada"
    conn = get_conn()
    cursor = conn.cursor()
    cursor.execute("SELECT id, nombre FROM comuna WHERE region_id= %s and id=%s ;",(region_id,comuna_id))
    result = cursor.fetchone()
    if result:  
        return "" 
    else:
        return "La comuna seleccionada no coincide con la región seleccionada" 
    
def get_devices_and_contact_data():
    conn = get_conn()
    cursor = conn.cursor()
    cursor.execute(
        """SELECT DISTINCT c.nombre ,c.email, c.celular,reg.nombre, com.nombre, d.nombre, d.tipo, d.anos_uso,d.estado , a.ruta_archivo,a.nombre_archivo, d.id
        FROM dispositivo d
        JOIN contacto c ON d.contacto_id = c.id
        JOIN archivo a ON d.id = a.dispositivo_id
        JOIN comuna com ON c.comuna_id= com.id
        JOIN region reg ON com.region_id = reg.id
        LIMIT 5;"""
    )
    result = cursor.fetchall()
    resultados_lista = []
    rutas_visitadas=[]
    for row in result:
        if row[9] not in rutas_visitadas or row[9]=="static/media":
            rutas_visitadas.append(row[9])
            resultado_dict = {
                'nombre': row[0],
                'email': row[1],
                'celular': row[2],
                'region': row[3],
                'comuna': row[4],
                'dispositivo': row[5],
                'tipo': row[6],
                'anos_uso': row[7],
                'estado' : row[8],
                'ruta_archivo': row[9],
                'nombre_archivo' : row[10],
                'device_id': row[11]
            }
            
            resultados_lista.append(resultado_dict)
    
        

    return resultados_lista

def get_data_with_device_id(id):
    conn = get_conn()
    cursor = conn.cursor()
    cursor.execute(QUERY_DICT["get_data_with_device_id"],(id,))
    result = cursor.fetchone()  
    if result:
        resultado_dict = {
            'nombre': result[0],
            'email': result[1],
            'celular': result[2],
            'region': result[3],
            'comuna': result[4],
            'dispositivo': result[5],
            'tipo': result[6],
            'anos_uso': result[7],
            'estado': result[8],
            'ruta_archivo': result[9],
            'nombre_archivo': result[10],
            'device_id': result[11]
        }
        return resultado_dict
    else:
        return None
    
def get_data_with_device_by_pag(pag):
    count=5
    offset= 5*(pag-1)
    conn = get_conn()
    cursor = conn.cursor()
    cursor.execute(QUERY_DICT["get_data_with_device_by_pag"],(offset,count))
    result = cursor.fetchall()
    resultados_lista = []
    rutas_visitadas=[]
    for row in result:
        if row[9] not in rutas_visitadas or row[9]=="static/media":
            rutas_visitadas.append(row[9])
            resultado_dict = {
                'nombre': row[0],
                'email': row[1],
                'celular': row[2],
                'region': row[3],
                'comuna': row[4],
                'dispositivo': row[5],
                'tipo': row[6],
                'anos_uso': row[7],
                'estado' : row[8],
                'ruta_archivo': row[9],
                'nombre_archivo' : row[10],
                'device_id': row[11]
            }
            resultados_lista.append(resultado_dict)
    return resultados_lista
    
def count_devices():
    conn = get_conn()
    cursor=conn.cursor()
    cursor.execute(QUERY_DICT["count_devices"])
    result = cursor.fetchone()
    return result[0]

def insert_comentary(nombre,texto,fecha,dispositivo_id):
    conn = get_conn()
    cursor=conn.cursor()
    cursor.execute(QUERY_DICT["insert_comentary"],(nombre,texto,fecha,dispositivo_id))
    conn.commit()

def get_comentaries_by_device_id(device_id):
    conn = get_conn()
    cursor=conn.cursor()
    cursor.execute(QUERY_DICT["get_comentaries_by_device_id"],(device_id,))
    result=cursor.fetchall()
    resultado_lista=[]
    for row in result:
        resultado_dict={
            'nombre': row[0],
            'texto': row[1],
            'fecha': row[2],
        }
        resultado_lista.append(resultado_dict)
    return resultado_lista

#para que ver dispositivos no este tan vacio(luego esto se carga al ingregar a ver dispositivos)  
# por esto las carpetas de uploads comienzan en 6 ya que los 5 primeros id los tienen los archivos base 
def add_verdispositivos_data():
    date= datetime.now().strftime('%Y-%m-%d %H:%M:%S')
    insert_contact("Jimmyneitor","jimmyaguilera010699@gmail.com","77777777",130401,date)
    
    contact_id= get_contact_id("Jimmyneitor","jimmyaguilera010699@gmail.com",130401,date)

    insert_device(contact_id,"GOLF powerbank P20-LCDPD","",11,1,1)


    device_id = get_device_id(contact_id,"GOLF powerbank P20-LCDPD","",11,1,1)

    
    insert_file("static/media","powerbank_golf_20000mA.jpg",device_id)

    #segundo disp  base

    insert_contact("Ptolomeo","mipc@acer.cl","77777778",130402,date)
    
    contact_id= get_contact_id("Ptolomeo","mipc@acer.cl",130402,date)

    insert_device(contact_id,"ACER aspire 4733Z","",2,3,3)


    device_id = get_device_id(contact_id,"ACER aspire 4733Z","",2,3,3)

    
    insert_file("static/media","acer_aspire_4733Z.jpg",device_id)

    #3er 

    insert_contact("Luis Jara","luchitojara@hotmail.cl","77777779",130403,date)
    
    contact_id= get_contact_id("Luis Jara","luchitojara@hotmail.cl",130403,date)

    insert_device(contact_id,"Brother DCP-T710W","",8,2,1)


    device_id = get_device_id(contact_id,"Brother DCP-T710W","",8,2,1)

    
    insert_file("static/media","impresora_brother_DCP_T710W.jpg",device_id)

    #4to

    insert_contact("Andrés Iniesta","barcelonafc@gmail.com","77777780",130404,date)
    
    contact_id= get_contact_id("Andrés Iniesta","barcelonafc@gmail.com",130404,date)

    insert_device(contact_id,"JBL QUANTUM 300","",10,1,1)


    device_id = get_device_id(contact_id,"JBL QUANTUM 300","",10,1,1)

    
    insert_file("static/media","audifonosjbl.jpg",device_id)

    #5to

    insert_contact("vegeta","vegeta777@outlook.com","87777777",130401,date)
    
    contact_id= get_contact_id("vegeta","vegeta777@outlook.com",130401,date)

    insert_device(contact_id,"ASUS TUF GAMING F15","",2,4,1)


    device_id = get_device_id(contact_id,"ASUS TUF GAMING F15","",2,4,1)

    
    insert_file("static/media","notebook_asus.jpg",device_id)

#add_verdispositivos_data() si se quisiera añadir muchos dispositivos se puede ejecutar esta cosa muchas veces para ver el paginado

