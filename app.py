from flask import Flask, request, render_template,redirect,url_for

app = Flask(__name__)

app.config['TEMPLATES_AUTO_RELOAD']=True
UPLOAD_FOLDER = 'static/uploads'

app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER



#rutas de nuestra aplicacion
@app.route("/" , methods=["GET","POST"])
def index():
    if request.method == "POST":
        pass
    return render_template("index.html")

@app.route("/agregardonacion")
def agregar_donacion():
    errors={}
    return render_template("agregar-donacion.html",errors=errors)

@app.route("/verdispositivos")
def ver_dispositivos():
    errors={}
    return render_template("ver-dispositivos.html",errors=errors)


@app.route("/informaciondispositivos")
def info_dispositivos():
    errors={}
    return render_template("informacion-dispositivos.html",errors=errors)


