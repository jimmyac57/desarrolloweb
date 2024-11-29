import pymysql
import json

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
    print(f"Inserting file: {filename} for device ID: {deviceId} at path: {path}")
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
    print(estado)
    tipo = mapear_tipo(tipo_id)
    print(tipo)
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
    
