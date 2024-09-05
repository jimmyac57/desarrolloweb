import * as AF from "./aux-functions.js";

const devices = {
  dispositivo1: {
    nombre: "Jimmy Aguilera",
    email: "jimmy.aguilera010699@gmail.com",
    celular: "75136833",
    region: "Metropolitana de Santiago",
    comuna: "San Bernardo",
    dispositivo: "GOLF powerbank P20-LCDPD",
    tipo: "Otro",
    uso: "1 año",
    status: "Funciona a medias",
    img: "../img/powerbank_golf_20000mA.jpg",
  },
  dispositivo2: {
    nombre: "ACER aspire 4733Z",
    email: "acer@example.com",
    celular: "12345678",
    region: "Valparaíso",
    comuna: "Quillota",
    dispositivo: "ACER aspire 4733Z",
    tipo: "Computadora",
    uso: "3 años",
    status: "No funciona",
    img: "../img/acer_aspire_4733Z.jpg",
  },
  dispositivo3: {
    nombre: "Brother DCP-T710W",
    email: "brother@example.com",
    celular: "87654321",
    region: "Metropolitana de Santiago",
    comuna: "San Bernardo",
    dispositivo: "Brother DCP-T710W",
    tipo: "Impresora",
    uso: "2 años",
    status: "Funciona a medias",
    img: "../img/impresora_brother_DCP_T710W.jpg",
  },
  dispositivo4: {
    nombre: "Andrés Iniesta",
    email: "barcelonafc@gmail.com",
    celular: "65432100",
    region: "Antofagasta",
    comuna: "Antofagasta",
    dispositivo: "JBL QUANTUM 300",
    tipo: "Audífonos",
    uso: "1 año",
    status: "Funciona perfecto",
    img: "../img/audifonosjbl.jpg",
  },
  dispositivo5: {
    nombre: "ASUS TUF GAMING F15",
    email: "asus@example.com",
    celular: "32109876",
    region: "Tarapacá",
    comuna: "Iquique",
    dispositivo: "ASUS TUF GAMING F15",
    tipo: "Computadora",
    uso: "6 meses",
    status: "Funciona perfecto",
    img: "../img/notebook_asus.jpg",
  },
};


function getHash() {
  return window.location.hash.substring(1);
}


const deviceId = getHash();


const deviceInfo = devices[deviceId];

if (deviceInfo) {
  
  const deviceHtml = `
            <h2>Información de contacto</h2>
            <p>${deviceInfo.nombre}</p>
            <p>Email: ${deviceInfo.email}</p>
            <p>Celular: ${deviceInfo.celular}</p>
            <p>Región: ${deviceInfo.region}</p>
            <p>Comuna: ${deviceInfo.comuna}</p>
            <h2>Información del dispositivo</h2>
            <p>Dispositivo: ${deviceInfo.dispositivo}</p>
            <p>Tipo: ${deviceInfo.tipo || "No disponible"}</p>
            <p>Tiempo de uso: ${deviceInfo.uso || "No disponible"}</p>
            <p>Estado de funcionamiento: ${deviceInfo.status}</p>
            <p>Fotos del producto</p>
            <img id="${deviceId}" class="zoomin" src="${deviceInfo.img}" alt="${deviceInfo.dispositivo}" width="640" height="480" />`;
  const comentariosHtml = `
            <h3>Comentarios</h3>
            <div class="comentario">
                <div class="comentario-header">
                    <span>@usuario123</span>
                    <span>22-03-23</span>
                </div>
                <div class="comentario-body">
                    asdda
                </div>
            </div>
            <div class="comentario">
                <div class="comentario-header">
                    <span>@anonimouse</span>
                    <span>22-03-24</span>
                </div>
                <div class="comentario-body">
                    ta wenardo
                </div>
            </div>
  `;

  const formularioHtml = `
  <div id="contenedor-comentarios"></div>
  <form id="myForm">
        <fieldset>
        <legend>Agregar nuevo comentario</legend>
      <label for="comentarista"><em>Nombre:</em></label>
      <input type="text" id="comentarista" name="nombre" minlength="3" maxlength="80" required><span></span> <br><br>

      <textarea name="comentario" id="comentario" cols="50" rows="4" required></textarea><span></span><br><br>

      <button type="button" id="addComentarioBtn">
          Agregar comentario
        </button>
        <div id="exito" class="exito" hidden><em>El comentario se agregó con éxito</em></div>
        </fieldset>
  </form>`;

  document.getElementById("device-info").innerHTML = deviceHtml;
  document.getElementById("device-info").innerHTML += comentariosHtml;
  document.getElementById("device-info").innerHTML += formularioHtml;

  const validarNombre = (campo) => {
    return AF.validarFormato(
      /^[a-zA-Z0-9]+$/,
      "El nombre solo acepta caracteres literales y numéricos",
      3,
      80,
      campo
    );
  };

  const validarComentario = (campo) => {
    return AF.validarFormato(
      /^[a-zA-Z0-9\s]+$/,
      "El comentario solo acepta caracteres literales y numéricos",
      5,
      200,
      campo
    );
  };

  const imagen = document.getElementById(deviceId);
  imagen.addEventListener("click", () => {
    if (imagen.classList.contains("zoomin")) {
      imagen.classList.remove("zoomin");
      imagen.classList.add("zoomout");
      imagen.style.width = "1240px";
      imagen.style.height = "1080px";
    } else {
      imagen.classList.remove("zoomout");
      imagen.classList.add("zoomin");
      imagen.style.width = "640px";
      imagen.style.height = "480px";
    }
  });

  const handleFormSubmit = () => {
    console.log("Validating form...");
    const nombre = document.getElementById("comentarista");
    const comentario = document.getElementById("comentario");

    // Variables auxiliares de validación
    let invalidInputs = [];
    let isValid = true;

    const setInvalidInput = (inputName) => {
      invalidInputs.push(inputName);
      console.log(invalidInputs);
      isValid = false;
    };

    // Validación de NOMBRE
    if (!validarNombre(nombre)) {
      setInvalidInput(nombre.name);
    }

    // Validación de COMENTARIO
    if (!validarComentario(comentario)) {
      setInvalidInput(comentario.name);
    }

    if (isValid) {
      const nuevoComentario = (nombre, comentario) => `
            <div class="comentario">
                <div class="comentario-header">
                    <span>@${nombre}</span>
                    <span>${new Date().toLocaleDateString()}</span>
                </div>
                <div class="comentario-body">
                    ${comentario}
                </div>
            </div>
        `;

      document.getElementById("contenedor-comentarios").innerHTML += nuevoComentario(
        nombre.value,
        comentario.value
      );
      document.getElementById("exito").hidden=false;

      nombre.value = "";
      comentario.value = "";
    }
    else{
        document.getElementById("exito").hidden=true;
    }
  };

  document.getElementById("addComentarioBtn").addEventListener("click", handleFormSubmit);
} else {
  document.getElementById("device-info").innerHTML = "<p>Dispositivo no encontrado.</p>";
}
