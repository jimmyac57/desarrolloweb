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


const device_Id = document.getElementById('contenedor-dispositivo').dataset.deviceId;
console.log("ID del dispositivo:", device_Id);

if (device_Id) {
  const validarNombre = (campo) => {
    return validarFormato(
      /^[a-zA-Z\s]+$/,
      "El nombre solo acepta caracteres literales",
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

  const imagen = document.getElementById(device_Id);
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

  const handleFormSubmit = (event) => {
    console.log("Validating form...");
    event.preventDefault();
    const nombre = document.getElementById("nombre");
    const comentario = document.getElementById("comentario");

    let isValid = true;

    // Validación de NOMBRE
    if (!validarNombre(nombre)) {
      isValid &&= false;
    }

    // Validación de COMENTARIO
    if (!validarComentario(comentario)) {
      isValid &&=false;
    }

    if (isValid) {
      
      const myForm = document.getElementById("myForm");
      myForm.submit();
    }
      
  };

  document.getElementById("myForm").addEventListener("submit", handleFormSubmit);
} else {
  contenedor.innerHTML =
    "<p>Dispositivo no encontrado.</p>";
}
