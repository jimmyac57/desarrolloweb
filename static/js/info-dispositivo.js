const setErrors = (mensaje, campo, isError = true) => {
  if (isError) {
    campo.classList.add("campo-invalido");
    campo.nextElementSibling.classList.add("error");
    campo.nextElementSibling.innerText = mensaje;
    return false;
  } else {
    campo.classList.remove("campo-invalido");
    campo.nextElementSibling.classList.remove("error");
    campo.nextElementSibling.innerText = mensaje;
    return true;
  }
};

const validarCampoVacio = (campo) => {
  const valor = campo.value.trim();
  if (valor.length == 0 && !campo.required) {
    return setErrors("", campo, false);
  } else if (valor == 0) {
    return setErrors(
      `El campo ${campo.name.replace(/\[\]$/, "")} es obligatorio`,
      campo
    );
  } else {
    return false;
  }
};

const validarFormato = (regex, regexFeedback, min, max, campo) => {
  const valor = campo.value.trim();
  const nombreRegex = regex;
  const minLength = min;
  const maxLength = max;
  if (valor.length == 0) {
    return validarCampoVacio(campo);
  } else if (valor.length > 0 && valor.length < minLength) {
    return setErrors(
      `El ${campo.name.replace(/\[\]$/, "")} debe tener al menos ` +
        minLength +
        ` caracteres`,
      campo
    );
  } else if (!nombreRegex.test(valor)) {
    return setErrors(regexFeedback, campo);
  } else if (valor.length > maxLength) {
    return setErrors(
      `El ${campo.name.replace(/\[\]$/, "")} debe tener como maximo ` +
        maxLength +
        ` caracteres`,
      campo
    );
  } else {
    return setErrors("", campo, false);
  }
};

//aqui se va a poner la info del dispositivo seleccionado en ver-dispositivos.html
const contenedor = document.getElementById("contenedor-dispositivo")


const devices = {
  dispositivo1: {
    nombre: "Jimmyneitor",
    email: "jimmyaguilera010699@gmail.com",
    celular: "75136833",
    region: "Región Metropolitana de Santiago",
    comuna: "San Bernardo",
    dispositivo: "GOLF powerbank P20-LCDPD",
    tipo: "Otro",
    uso: "1",
    status: "Funciona a medias",
    img: "../img/powerbank_golf_20000mA.jpg",
    comentario: `
            <h3>Comentarios</h3>
            <div class="comentario">
                <div class="comentario-header">
                    <span>@user0123</span>
                    <span>22/03/23</span>
                </div>
                <div class="comentario-body">
                    carga de pana
                </div>
            </div>
            <div class="comentario">
                <div class="comentario-header">
                    <span>@anonimouse</span>
                    <span>22/03/24</span>
                </div>
                <div class="comentario-body">
                    da pa 3 cargas, casi 4
                </div>
            </div>
  `,
  },
  dispositivo2: {
    nombre: "Ptolomeo",
    email: "mipc@acer.cl",
    celular: "12345678",
    region: "Región de Valparaíso",
    comuna: "Quillota",
    dispositivo: "ACER aspire 4733Z",
    tipo: "Notebook",
    uso: "3",
    status: "No funciona",
    img: "../img/acer_aspire_4733Z.jpg",
    comentario: `
            <h3>Comentarios</h3>
            <div class="comentario">
                <div class="comentario-header">
                    <span>@pcmaster3000</span>
                    <span>01/01/22</span>
                </div>
                <div class="comentario-body">
                    no corre el lol
                </div>
            </div>
            <div class="comentario">
                <div class="comentario-header">
                    <span>@goblin46</span>
                    <span>23/05/23</span>
                </div>
                <div class="comentario-body">
                    ta bonito
                </div>
            </div>
  `,
  },
  dispositivo3: {
    nombre: "Luis Jara",
    email: "luchitojara@hotmail.cl",
    celular: "87654321",
    region: "Región Metropolitana de Santiago",
    comuna: "San Bernardo",
    dispositivo: "Brother DCP-T710W",
    tipo: "Impresora",
    uso: "2",
    status: "Funciona a medias",
    img: "../img/impresora_brother_DCP_T710W.jpg",
    comentario: `
            <h3>Comentarios</h3>
            <div class="comentario">
                <div class="comentario-header">
                    <span>@student777</span>
                    <span>05/03/21</span>
                </div>
                <div class="comentario-body">
                    despues de 3 años se me acabó la tinta recien
                </div>
            </div>
            <div class="comentario">
                <div class="comentario-header">
                    <span>@primeraimpresion57</span>
                    <span>23/03/23</span>
                </div>
                <div class="comentario-body">
                    tenia una de las baratas y no rinde ni de cerca como esta
                </div>
            </div>
  `,
  },
  dispositivo4: {
    nombre: "Andrés Iniesta",
    email: "barcelonafc@gmail.com",
    celular: "65432100",
    region: "Región de Antofagasta",
    comuna: "Antofagasta",
    dispositivo: "JBL QUANTUM 300",
    tipo: "Audífonos",
    uso: "1",
    status: "Funciona perfecto",
    img: "../img/audifonosjbl.jpg",
    comentario: `
            <h3>Comentarios</h3>
            <div class="comentario">
                <div class="comentario-header">
                    <span>@noteescucho</span>
                    <span>22/03/22</span>
                </div>
                <div class="comentario-body">
                    que pena que no sean inalambricos
                </div>
            </div>
            <div class="comentario">
                <div class="comentario-header">
                    <span>@fanjbl300</span>
                    <span>22/03/24</span>
                </div>
                <div class="comentario-body">
                    son los mejores que he tenido
                </div>
            </div>
  `,
  },
  dispositivo5: {
    nombre: "vegeta",
    email: "vegeta777@outlook.com",
    celular: "32109876",
    region: "Región de Tarapacá",
    comuna: "Iquique",
    dispositivo: "ASUS TUF GAMING F15",
    tipo: "Notebook",
    uso: "3",
    status: "Funciona perfecto",
    img: "../img/notebook_asus.jpg",
    comentario: `
            <h3>Comentarios</h3>
            <div class="comentario">
                <div class="comentario-header">
                    <span>@usuario12311</span>
                    <span>22/03/23</span>
                </div>
                <div class="comentario-body">
                    nah el medio pcsito
                </div>
            </div>
            <div class="comentario">
                <div class="comentario-header">
                    <span>@gamer300</span>
                    <span>22/03/24</span>
                </div>
                <div class="comentario-body">
                    pesa caleta siii
                </div>
            </div>
  `,
  },
};

//funcion para tomar el id que enviamos desde ver-dispositivos.html
const getHash = () => {
  return window.location.hash.substring(1);
}

const deviceId = getHash();

const deviceInfo = devices[deviceId];

if (deviceInfo) {
  const deviceHtml = `
            <h2>Información de contacto</h2>
            <p>Nombre: ${deviceInfo.nombre}</p>
            <p>Email: ${deviceInfo.email}</p>
            <p>Celular: ${deviceInfo.celular}</p>
            <p>Región: ${deviceInfo.region}</p>
            <p>Comuna: ${deviceInfo.comuna}</p>
            <h2>Información del dispositivo</h2>
            <p>Dispositivo: ${deviceInfo.dispositivo}</p>
            <p>Tipo: ${deviceInfo.tipo || "No disponible"}</p>
            <p>Años de uso: ${deviceInfo.uso || "No disponible"}</p>
            <p>Estado de funcionamiento: ${deviceInfo.status}</p>
            <p>Fotos del producto</p>
            <img id="${deviceId}" class="zoomin" src="${deviceInfo.img}" alt="${
    deviceInfo.dispositivo
  }" style="width:640px;height:480px;" />`;

  const formularioHtml = `
  <div id="contenedor-comentarios"></div>
  <form id="myForm">
        <fieldset>
        <legend>Agregar nuevo comentario</legend>
      <label for="comentarista"><em>Nombre:</em></label>
      <input type="text" id="comentarista" name="nombre" minlength="3" maxlength="80" required><span></span> <br><br>

      <label for="comentario"> <em>Comentario:</em> </label>
      <textarea name="comentario" id="comentario" cols="50" rows="4" maxlength="200" required></textarea>
      <span></span><br><br>

      <button type="button" id="addComentarioBtn">
          Agregar comentario
        </button>

        <div>
            <a class="volver-button" href="../html/ver-dispositivos.html"> Volver </a>
          </div>
        <div>
            <a class="button" href="../html/index.html">Volver al inicio</a>
          </div>
          
        <div id="exito" class="exito" hidden><em>El comentario se agregó con éxito</em></div>
        </fieldset>
  </form>`;

  
  contenedor.innerHTML = deviceHtml;
  contenedor.innerHTML += deviceInfo.comentario;
  contenedor.innerHTML += formularioHtml;

  const validarNombre = (campo) => {
    return validarFormato(
      /^[a-zA-Z0-9]+$/,
      "El nombre solo acepta caracteres literales y numéricos",
      3,
      80,
      campo
    );
  };

  const validarComentario = (campo) => {
    return validarFormato(
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
      const fecha = new Date();
      const opciones = { day: "2-digit", month: "2-digit", year: "2-digit" };
      const fechaFormateada = fecha.toLocaleDateString("es-ES", opciones);
      console.log(fechaFormateada);

      const nuevoComentario = (nombre, comentario) => `
            <div class="comentario">
                <div class="comentario-header">
                    <span>@${nombre}</span>
                    <span class="date">${fechaFormateada}</span>
                </div>
                <div class="comentario-body">
                    ${comentario}
                </div>
            </div>
        `;

      document.getElementById("contenedor-comentarios").innerHTML +=
        nuevoComentario(nombre.value, comentario.value);
      document.getElementById("exito").hidden = false;

      nombre.value = "";
      comentario.value = "";
    } else {
      document.getElementById("exito").hidden = true;
    }
  };

  document
    .getElementById("addComentarioBtn")
    .addEventListener("click", handleFormSubmit);
} else {
  contenedor.innerHTML =
    "<p>Dispositivo no encontrado.</p>";
}
