import re
from database import db
import filetype

def validate_name(name):
    if not name:
        return "El nombre es obligatorio"
    if len(name) < 3:
        return "El nombre es demasiado corto, debe ser mayor de 3 caracteres"
    if len(name) > 80:
        return "El nombre es demasiado largo, debe ser menor de 80 caracteres"
    if not re.match(r"^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$",name): 
        return "El nombre solo puede contener letras"
    return ""

def validate_email(email):
    if not email:
        return "El email es obligatorio"
    if len(email)<5:
        return "El email es demasiado corto, debe ser mayor de 5 caracteres"
    if len(email) > 30:
        return "El email es demasiado largo, debe ser menos de 30 caracteres"
    if not re.match(r'^[^\s@]+@[^\s@]+\.[^\s@]+$',email):
        return "El email debe tener el formato email@dominio.cl"
    return ""

def validate_phone(phone):
    if not phone:
        return ""
    if len(phone) > 15 or not phone.isdigit():
        return "El telefono no puede ser mayor de 15 digitos"
    if len(phone) < 3 and len(phone)>0:
        return "El telefono no puede ser menor de 3 digitos"
    if not phone.isdigit():
        return "El teléfono solo puede contener números"
    return ""

def validate_region(region_id):
    result = db.validate_region_db(region_id)
    return result

def validate_comuna(region_id,comuna):
    result = db.validate_comuna_db(region_id,comuna)
    return result

def validate_device(device):
    if not device:
        return "El nombre es obligatorio"
    if len(device) < 3:
        return "El nombre es demasiado corto, debe ser mayor de 3 caracteres"
    if len(device) > 80:
        return "El nombre es demasiado largo, debe ser menor de 80 caracteres"
    if not re.match(r'^[a-zA-Z0-9áéíóúñÁÉÍÓÚÑ]*$', device):
        return "El nombre solo puede contener letras y números"
    return ""
    
def validate_descripcion(descricion):
    descricion=descricion.strip()
    if not descricion:
        return ""
    if len(descricion)<3 and len(descricion)>0:
        return "La descripción es demasiado corta, debe ser mayor de 3 caracteres"
    if len(descricion)>200:
        return "La descripcion es demasiado larga, debe ser menor de 200 caracteres"
    return ""

def validate_tipo(tipo):
    if not tipo:
        return "Seleccionar el tipo de dispositivo es obligatorio"
    opciones_regex = r"^(?:[1-9]|10|11)$"
    if not re.match(opciones_regex,tipo):
        return "La opción seleccionada no existe"
    return ""

def validate_uso(uso):
    if not uso:
        return "Seleccionar los años de uso es obligatorio"
    try:
        uso = int(uso) 
    except ValueError:
        return "El tiempo de uso debe ser un número"

    if uso > 99:
        return "El tiempo de uso no puede exceder los 99 años"
    if uso < 1:
        return "El tiempo de uso no puede ser menos de 1 año"
    
    return ""


def validate_estado(estado):
    if not estado:
        return "Debe seleccionar el estado del dispositivo"
    opciones_regex = r"^(?:[1-3])$"
    if not re.match(opciones_regex,estado):
        return "La opción seleccionada no existe"
    return ""


def validate_archivos(archivos):
    ALLOWED_EXTENSIONS = {"png", "jpg", "jpeg", "gif"}
    ALLOWED_MIMETYPES = {"image/jpeg", "image/png", "image/gif"}
    mensaje = ""
    for archivo in archivos:
 
        if archivo is None:
            return mensaje + "Debe subir un archivo\n"

        if archivo.filename == "":
            return mensaje + "No se subio ningun archivo\n"
    
        ftype_guess = filetype.guess(archivo)
        if ftype_guess.extension not in ALLOWED_EXTENSIONS:
            return mensaje + "El archivo no tiene la extension requerida\n"
        # check mimetype
        if ftype_guess.mime not in ALLOWED_MIMETYPES:
            return mensaje + "El archivo no corresponde con el tipo de archivo requerido\n"
    return mensaje