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

const validarTamanno = (max, campo) => {
  let valor = campo.files;
  if (valor.length == 0) {
    return validarCampoVacio(campo);
  } else if (valor.length > max) {
    return setErrors(
      `La cantidad de archivos debe ser a lo más de ` + max,
      campo
    );
  } else {
    return setErrors("", campo, false);
  }
};

const validarSelect = (opcionesvalidas, campo) => {
  if (!campo) {
    setErrors("La selección no es válida", campo);
  }
  if (!opcionesvalidas) {
    return setErrors("El valor no coincide con el texto seleccionado", campo);
  }
  return setErrors("", campo, false);
};


//## INFO CONTACTO ##
const nombreInput = document.getElementById("nombre");
const emailInput = document.getElementById("email");
const celularInput = document.getElementById("celular");
const regionInput = document.querySelector("#region");
const comunaInput = document.querySelector("#comuna");

//#### NOMBRE ####
const validarNombre = (campo) => {
  return validarFormato(
    /^[A-Za-z\s]+$/,
    "El formato solo permite caracteres literales",
    3,
    80,
    campo
  );
};
//#### EMAIL ####
const validarEmail = (campo) => {
  return validarFormato(
    /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/,
    "Debe cumplir con el formato de correo electrónico, tucorreo@dominio.com",
    5,
    30,
    (campo = emailInput)
  );
};
//#### CELULAR ####
const validarCelular = (campo) => {
  return validarFormato(
    /^[0-9]{3,15}$/,
    "El formato solo permite numeros",
    3,
    15,
    (campo = celularInput)
  );
};
//#### REGION ####
const validarRegion = (campo) => {
  let textoVisible = campo.options[campo.selectedIndex].textContent;
  if (campo.value == 0) {
    return validarCampoVacio(campo);
  }
  let cond1 = regiones_comunas.regiones.find(
    (region) => region.id == campo.value
  );
  let cond2 = regiones_comunas.regiones.find(
    (region) => region.nombre == textoVisible
  );
  return validarSelect(cond1 == cond2, campo);
};
//#### COMUNA ####
const validarComuna = (regionSelected, campo) => {
  let textoVisible = campo.options[campo.selectedIndex].textContent;
  if (campo.value == 0) {
    return validarCampoVacio(campo);
  }
  let regionSeleccionada = regiones_comunas.regiones.find(
    (region) => region.id == regionSelected.value
  );
  let cond1 = regionSeleccionada.comunas.find(
    (comuna) => comuna.id == campo.value
  );
  let cond2 = regionSeleccionada.comunas.find(
    (comuna) => comuna.nombre == textoVisible
  );
  return validarSelect(cond1 == cond2, campo);
};
//### DISPOSITIVO ###
const validarDispositivo = (campo) => {
  return validarFormato(
    /^[a-zA-Z0-9\s]+$/,
    "El formato solo permite numeros o letras",
    3,
    80,
    campo
  );
};
//### TIPO ###
const validarTipo = (campo) => {
  let opcionElegida = campo.options[campo.selectedIndex].textContent;
  if (campo.value == 0) {
    return validarCampoVacio(campo);
  }
  let opcionesvalidas = [
    "Pantalla",
    "Notebook",
    "Tablet",
    "Celular",
    "Consola",
    "Mouse",
    "Teclado",
    "Impresora",
    "Parlante",
    "Audífonos",
    "Otro",
  ];
  return validarSelect(opcionesvalidas.includes(opcionElegida), campo);
};
//### TIEMPO DE USO ###
const validarTiempodeuso = (campo) => {
  return validarFormato(
    /^(?:[1-9]|[1-9][0-9])$/,
    "El formato solo permite caracteres numericos entre 1 y 99",
    1,
    2,
    campo
  );
};
//### ESTADO ###
const validarEstado = (campo) => {
  let opcionElegida = campo.options[campo.selectedIndex].textContent;
  if (campo.value == 0) {
    return validarCampoVacio(campo);
  }
  let opcionesvalidas = [
    "Funciona perfecto",
    "Funciona a medias",
    "No funciona",
  ];
  return validarSelect(opcionesvalidas.includes(opcionElegida), campo);
};
//### ARCHIVOS ###
const validarArchivos = (campo) => {
  let files = campo.files;
  if (!files) {
    return validarCampoVacio(campo);
  }
  let typeValid = true;

  for (const file of files) {
    // el tipo de archivo debe ser "image/<foo>" o "application/pdf"
    let fileFamily = file.type.split("/")[0];
    typeValid &&= fileFamily == "image" || file.type == "application/pdf";
  }
  if (!typeValid) {
    return setErrors(
      "El archivo debe ser una imagen (JPEG, PNG, GIF, etc.) o un PDF",
      campo
    );
  }

  // devolvemos la lógica AND de las validaciones.
  return validarTamanno(3, campo) && typeValid;
};

//## VALIDACIONES DINAMICAS ##
//validacion dinamica nombre
nombreInput.addEventListener("input", () => {
  validarNombre(nombreInput);
});
nombreInput.addEventListener("blur", () => {
  validarCampoVacio(nombreInput);
});

//validacion dinamica email
emailInput.addEventListener("input", () => {
  validarEmail(emailInput);
});
emailInput.addEventListener("blur", () => {
  validarCampoVacio(emailInput);
});

//validacion dinamica celular
celularInput.addEventListener("input", () => {
  validarCelular(celularInput);
});

//validacion dinamica region
regionInput.addEventListener("change", () => {
  validarRegion(regionInput);
});

//validacion dinamica comuna
comunaInput.addEventListener("change", () => {
  validarComuna(regionInput, comunaInput);
});

const myForm = document.getElementById("myForm");

myForm.addEventListener("input", (event) => {
  if (event.target.matches('input[name="dispositivo[]"]')) {
    validarDispositivo(event.target);
  }
});

myForm.addEventListener("blur", (event) => {
  if (event.target.matches('input[name="dispositivo[]"]')) {
    validarCampoVacio(event.target);
  }
});

myForm.addEventListener("change", (event) => {
  if (event.target.matches('select[name="tipo[]"]')) {
    validarTipo(event.target);
  }
});

myForm.addEventListener("input", (event) => {
  if (event.target.matches('input[name="uso[]"]')) {
    validarTiempodeuso(event.target);
  }
});

myForm.addEventListener("blur", (event) => {
  if (event.target.matches('input[name="uso[]"]')) {
    validarCampoVacio(event.target);
  }
});

myForm.addEventListener("change", (event) => {
  if (event.target.matches('select[name="estado[]"]')) {
    validarEstado(event.target);
  }
});

myForm.addEventListener("change", (event) => {
  if (event.target.matches('input[name="archivos[]"]')) {
    validarArchivos(event.target);
  }
});

const handleFormSubmit = (event) => {
  event.preventDefault();
  console.log("Validating form...");

  const dispositivoInputs = document.getElementsByName("dispositivo[]");
  const tipoInputs = document.getElementsByName("tipo[]");
  const tiempodeusoInputs = document.getElementsByName("uso[]");
  const estadoInputs = document.getElementsByName("estado[]");
  const archivosInputs = document.getElementsByName("archivos[]");

  // variables auxiliares de validación y función.
  let invalidInputs = [];
  let isValid = true;
  const setInvalidInput = (inputName) => {
    invalidInputs.push(inputName);
    isValid &&= false;
  };
  //NOMBRE
  if (!validarNombre(nombreInput)) {
    setInvalidInput(nombreInput.name);
  }
  //EMAIL
  if (!validarEmail(emailInput)) {
    setInvalidInput(emailInput.name);
  }
  //CELULAR
  if (!validarCelular(celularInput)) {
    setInvalidInput(celularInput.name);
  }

  //REGION
  if (!validarRegion(regionInput)) {
    setInvalidInput(regionInput.name);
  }
  //COMUNA
  if (!validarComuna(regionInput, comunaInput)) {
    setInvalidInput(comunaInput.name);
  }

  //DISPOSITIVO
  for (let i = 0; i < dispositivoInputs.length; i++) {
    if (!validarDispositivo(dispositivoInputs[i])) {
      let inputName = dispositivoInputs[i].name;
      if (i > 0) {
        inputName = inputName.replace(/\[\]$/, ` n${i + 1}`);
      }
      setInvalidInput(inputName.replace(/\[\]$/, ""));
    }
  }
  //TIPO
  for (let i = 0; i < tipoInputs.length; i++) {
    if (!validarTipo(tipoInputs[i])) {
      let inputName = tipoInputs[i].name;
      if (i > 0) {
        inputName = inputName.replace(/\[\]$/, ` n${i + 1}`);
      }
      setInvalidInput(inputName.replace(/\[\]$/, ""));
    }
  }

  //AÑOS DE USO
  for (let i = 0; i < tiempodeusoInputs.length; i++) {
    if (!validarTiempodeuso(tiempodeusoInputs[i])) {
      let inputName = tiempodeusoInputs[i].name;
      if (i > 0) {
        inputName = inputName.replace(/\[\]$/, ` n${i + 1}`);
      }
      setInvalidInput(inputName.replace(/\[\]$/, ""));
    }
  }

  //ESTADO
  for (let i = 0; i < estadoInputs.length; i++) {
    if (!validarEstado(estadoInputs[i])) {
      let inputName = estadoInputs[i].name;
      if (i > 0) {
        inputName = inputName.replace(/\[\]$/, ` n${i + 1}`);
      }
      setInvalidInput(inputName.replace(/\[\]$/, ""));
    }
  }

  //ARCHIVOS
  for (let i = 0; i < archivosInputs.length; i++) {
    if (!validarArchivos(archivosInputs[i])) {
      let inputName = archivosInputs[i].name;
      if (i > 0) {
        inputName = inputName.replace(/\[\]$/, ` n${i + 1}`);
      }
      setInvalidInput(inputName.replace(/\[\]$/, ""));
    }
  }

  // finalmente mostrar la validación
  let validationBox = document.getElementById("val-box");
  let validationMessageElem = document.getElementById("val-msg");
  let validationListElem = document.getElementById("val-list");
  let confirmationBox = document.getElementById("conf-box");
  let confirmationMessage = document.getElementById("conf-msg");

  if (!isValid) {
    validationListElem.textContent = "";
    // agregar elementos inválidos al elemento val-list.
    for (let i = 0; i < invalidInputs.length; i++) {
      let listElement = document.createElement("li");
      listElement.innerText = invalidInputs[i];
      validationListElem.append(listElement);
    }
    // establecer val-msg
    validationMessageElem.innerText = "Los siguientes campos son inválidos:";

    // aplicar estilos de error
    validationBox.style.backgroundColor = "#ffdddd";
    validationBox.style.borderLeftColor = "#f44336";

    // hacer visible el mensaje de validación
    validationBox.hidden = false;

    confirmationBox.hidden = true;
  } else {
    // Ocultar el formulario
    myForm.style.display = "none";

    document.querySelector("h1").style.display="none"

    // establecer mensaje de éxito
    validationMessageElem.innerText ="¡Formulario válido! ¿Confirma que desea publicar esta donación?";
    validationListElem.textContent = "";

    // aplicar estilos de éxito
    validationBox.style.backgroundColor = "#ddffdd";
    validationBox.style.borderLeftColor = "#4CAF50";

    // Agregar botones para enviar el formulario o volver
    let submitButton = document.createElement("button");
    submitButton.innerText = "Si, confirmo";
    submitButton.style.marginRight = "10px";
    submitButton.addEventListener("click", () => {
      
      validationBox.hidden = true;
      myForm.submit()
    });

    let backButton = document.createElement("button");
    backButton.innerText = "No, quiero volver al formulario";
    backButton.addEventListener("click", () => {
      // Mostrar el formulario nuevamente
      myForm.style.display = "block";
      validationBox.hidden = true;
      document.querySelector("h1").style.display="block";
    });

    validationListElem.appendChild(submitButton);
    validationListElem.appendChild(backButton);

    // hacer visible el mensaje de validación
    validationBox.hidden = false;
  }
};

const submitButton = document.getElementById("button-submit");
submitButton.addEventListener("click", handleFormSubmit);
