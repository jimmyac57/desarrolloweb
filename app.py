from flask import Flask, request, render_template, redirect, url_for
from werkzeug.utils import secure_filename
from utils.validations import * 
import os
from database import db
from datetime import datetime
import hashlib
import filetype
import uuid

app = Flask(__name__)

app.config['TEMPLATES_AUTO_RELOAD'] = True
UPLOAD_FOLDER = 'static/uploads'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
app.config['MAX_CONTENT_LENGTH'] = 16 * 1000 * 1000



@app.route("/", methods=["GET", "POST"])
def index():
    if request.method == "POST":
        pass  
    return render_template("index.html")

@app.route("/agregardonacion", methods=['GET', 'POST'])
def agregar_donacion():
    if request.method == "POST":
        errors = {}
        # INFO CONTACTO
        nombre = request.form.get("nombre").strip()
        email = request.form.get("email").strip()
        phone = request.form.get("celular").strip()
        region_id = int(request.form.get("region"))
        comuna_id = int(request.form.get("comuna"))
        """ print("info contacto")
        print("nombre:",nombre)
        print("email:",email)
        print("phone:",phone)
        print("region-id:",region_id)
        print("comuna-id:",comuna_id) """
        # INFO DISPOSITIVO
        devices = request.form.getlist("dispositivo[]")
        descripciones = request.form.getlist("descripcion[]")
        tipos = request.form.getlist("tipo[]")
        usos = request.form.getlist("uso[]")
        estados = request.form.getlist("estado[]")
        cantidad_de_dispositivos= len(estados)
        archivos = []
        for i in range(cantidad_de_dispositivos): 
            archivo= request.files.getlist(f"archivo_{i+1}")
            archivos.append(archivo)

        """ print("ver dispositivos")
        print("devices:",devices)
        print("descripciones:",descripciones)
        print("tipos:",tipos)
        print("usos:",usos)
        print("estados:",estados)
        print("archivos:",archivos) 

        print("esta pasando por aca")  """

        # Validaciones
        errors['nombre'] = validate_name(nombre)
        errors['email'] = validate_email(email)
        errors['phone'] = validate_phone(phone)
        errors['region'] = validate_region(region_id)
        errors['comuna'] = validate_comuna(region_id, comuna_id) 


        for i in range(len(devices)):
            errors[f'device_{i+1}']=validate_device(devices[i])
            errors[f'descripcion_{i+1}'] = validate_descripcion(descripciones[i])
            errors[f'tipo_{i+1}']= validate_tipo(tipos[i].strip())
            errors[f'estado_{i+1}']= validate_estado(estados[i])
            errors[f'usos_{i+1}']=validate_uso(usos[i])
            errors[f'img_{i+1}']=validate_archivos(archivos[i])

        errors = {key: value for key, value in errors.items() if value}

        if errors:
            #print("errores:",errors)
            return render_template("agregar-donacion.html", errors=errors, form=request.form)
        else: 
            #print("no hay errores")
            fecha = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
            #print("fecha:" , fecha)
            path = UPLOAD_FOLDER
            
            #INSERTAMOS CONTACTO
            db.insert_contact(nombre, email, phone, comuna_id, fecha)

            #CAPTURAMOS EL ID ASIGNADO
            id_contact = db.get_contact_id(nombre, email, comuna_id, fecha)

            #print("id_contact:",id_contact)

            #INSERTAMOS DISPOSITIVOS
            for i in range(cantidad_de_dispositivos):
                db.insert_device(id_contact, devices[i].strip(),descripciones[i].strip(), tipos[i], usos[i], estados[i])

                id_dispositivo = db.get_device_id(id_contact, devices[i].strip(),descripciones[i].strip(),tipos[i], usos[i], estados[i])
                print("es el id:" ,id_dispositivo)
                
                device_join_path = os.path.join(path, str(id_dispositivo))
                device_path = device_join_path.replace(os.sep,'/')
                if not os.path.exists(device_path):
                    os.makedirs(device_path)
    
                print(f"Carpeta creada (o existente): {device_path}")

                for k in range(len(archivos[i])):
                    unique_id = uuid.uuid4().hex
                    _filename = hashlib.sha256(
                    f"{secure_filename(archivos[i][k].filename)}_{unique_id}".encode("utf-8")
                    ).hexdigest()
                    _extension = filetype.guess(archivos[i][k]).extension
                    img_filename = f"{_filename}.{_extension}"

                    #print("img filename: ",img_filename )

                    save_join_path = os.path.join(device_path, img_filename)
                    save_path=save_join_path.replace(os.sep,'/')
                    archivos[i][k].save(save_path)
 

                    db.insert_file(device_path,img_filename,id_dispositivo)
            
            return redirect(url_for('agregar_donacion', exito=True))
    else:
        exito = request.args.get('exito', default=False, type=bool)
        errors = request.args.get('errors', None)
        return render_template("agregar-donacion.html",errors=errors,exito=exito)
        

@app.route("/verdispositivos")
def ver_dispositivos():
    errors = {}
    return render_template("ver-dispositivos.html", errors=errors)

@app.route("/informaciondispositivos")
def info_dispositivos():
    errors = {}
    return render_template("informacion-dispositivos.html", errors=errors)

@app.errorhandler(413)
def handle_file_too_large(e):
    return render_template("agregar-donacion.html", errors="El archivo es demasiado grande. Máximo permitido: 16 MB."),413

if __name__ == "__main__":
    app.run(debug=True)

