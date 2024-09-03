

//Funcion para colocar los mensajes de error
const setErrors = (mensaje, campo, isError=true) =>{
    if(isError){
        campo.classList.add("campo-invalido");
        campo.nextElementSibling.classList.add("error");
        campo.nextElementSibling.innerText = mensaje ;
        return false;
    }else{
        campo.classList.remove("campo-invalido");
        campo.nextElementSibling.classList.remove("error");
        campo.nextElementSibling.innerText = mensaje ;
        return true;
    }
}
//Funcion para validar si un campo esta vacio o no
const validarCampoVacio = (campo) =>{
    const valor = campo.value.trim();
    if(valor.length==0 && !campo.required){
        return setErrors("",campo,false);
    }else if(valor==0){
        return setErrors(`El campo ${campo.name} es obligatorio`,campo);
    }else{
        return false;
    }
}
//Funcion para validar el formato de un campo
const validarFormato = (regex,regexFeedback, min, max, campo) => {
    const valor = campo.value.trim();
    const nombreRegex = regex;
    const minLength = min;
    const maxLength = max;
    if(valor.length==0){
        return validarCampoVacio(campo);
    }
    else if (valor.length > 0 && valor.length < minLength) {
        return setErrors(`El ${campo.name} debe tener al menos ` + minLength +` caracteres`,campo);
    } 
    else if (!nombreRegex.test(valor)) {
        return setErrors(regexFeedback,campo);
    }
    else if (valor.length > maxLength) {
        return setErrors(`El ${campo.name} debe tener como maximo `+ maxLength +` caracteres`,campo);
    } 
    else {
        return setErrors("",campo,isError=false);
    }
};

const validarTamanno = ( max,campo) => {

    let valor=campo.files;
    if(valor.length==0){
        return validarCampoVacio(campo);
    }
    else if (valor.length > max) {
        return setErrors(`La cantidad de archivos debe ser a lo más de ` + max ,campo);
    } else {
        return setErrors("",campo, isError=false);
    }
}

const validarSelect = (opcionesvalidas,campo) => {
    if(!campo) setErrors("La selección no es válida",campo);
    if (!opcionesvalidas) {
      return setErrors("El valor no coincide con el texto seleccionado",campo);
    }
    return setErrors("",campo,isError=false);
}
  
  

//# CAMPOS DEL FORMULARIO #

//## INFO CONTACTO ##
const nombreInput = document.getElementById("nombre");
const emailInput = document.getElementById("email");
const celularInput = document.getElementById("celular");
const regionInput = document.querySelector("#region");
const comunaInput = document.querySelector('#comuna');

//### EVENTOS ###

//#### NOMBRE ####
const validarNombre = (campo) => {
    return validarFormato( regex= /^[A-Za-z\s]+$/,
        regexFeedback="El formato solo permite caracteres literales",
        minLength= 3, 
        maxLength=80,
        campo)
}
//validacion dinamica nombre
nombreInput.addEventListener("input", () => 
    validarNombre(nombreInput)
);
nombreInput.addEventListener("blur", () => 
    validarCampoVacio(nombreInput)

);
//#### EMAIL ####
const validarEmail = (campo) => {
    return validarFormato( regex=/^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/,
        regexFeedback="Debe cumplir con el formato de correo electrónico, tucorreo@dominio.TDL, TDL debe ser al menos 2 caracteres",
        minLength= 5,
        maxLength=30 ,
        campo = emailInput)
}
//validacion dinamica email
emailInput.addEventListener("input", () => 
    validarEmail(emailInput)
);
emailInput.addEventListener("blur", () => 
    validarCampoVacio(emailInput)
);

//#### CELULAR ####
const validarCelular = (campo) =>{
    return validarFormato( regex=/^[0-9]{3,15}$/,
        regexFeedback="El formato solo permite numeros",
        minLength= 3,
        maxLength=15 ,
        campo = celularInput);
}
//validacion dinamica celular
celularInput.addEventListener("input", () => 
    validarCelular(celularInput)
);

//#### REGION ####
const validarRegion = (campo) => {
    let textoVisible=campo.options[campo.selectedIndex].textContent;
    if(campo.value==0){
        return validarCampoVacio(campo)
    }
    let cond1 = regiones_comunas.regiones.find(region => region.id == campo.value);
    let cond2 = regiones_comunas.regiones.find(region => region.nombre == textoVisible);
    return validarSelect(cond1==cond2,campo)
};
//validacion dinamica region
regionInput.addEventListener("change",() => {
    validarRegion(regionInput)
});


//#### COMUNA ####
const validarComuna = (regionSelected,campo) => {
    let textoVisible=campo.options[campo.selectedIndex].textContent;
    if(campo.value==0){
        return validarCampoVacio(campo)
    }
    let regionSeleccionada = regiones_comunas.regiones.find(region => region.id == regionSelected.value);
    let cond1 = regionSeleccionada.comunas.find(comuna => comuna.id == campo.value);
    let cond2 = regionSeleccionada.comunas.find(comuna => comuna.nombre == textoVisible);
    return validarSelect(cond1==cond2,campo)
}
comunaInput.addEventListener("change",()=>{
    validarComuna(regionInput,comunaInput)
});

//## INFO DISPOSITIVO ##
const dispositivoInput = document.getElementById("dispositivo");
const tipoInput = document.getElementById("tipo");
const tiempodeusoInput = document.getElementById("uso");
const estadoInput = document.getElementById("estado");
const archivosInput = document.querySelector("[name=archivos]")


//### DISPOSITIVO ###
const validarDispositivo = (campo) => {
    return validarFormato( regex= /^[A-Za-z\s]+$/,
        regexFeedback="El formato solo permite caracteres literales",
        minLength= 3, 
        maxLength=80,
        campo)
}
dispositivoInput.addEventListener("input", (e) => 
    validarDispositivo(dispositivoInput)
);
dispositivoInput.addEventListener("blur", (e) => 
    validarCampoVacio(dispositivoInput)
);
//### TIPO ###
const validarTipo = (campo) => {
    let opcionElegida=campo.options[campo.selectedIndex].textContent
    if(campo.value==0){
        return validarCampoVacio(campo)
    }
    let opcionesvalidas=["Pantalla","Notebook","Tablet","Celular","Consola","Mouse","Teclado","Impresora","Parlante","Audífonos","Otro"]
    return validarSelect(opcionesvalidas.includes(opcionElegida),campo)
}
tipoInput.addEventListener("change",() => {
    validarTipo(tipoInput)
})

//### TIEMPO DE USO ###
const validarTiempodeuso= (campo) => {
    return validarFormato( regex= /^(?:[1-9]|[1-9][0-9])$/,
                    regexFeedback="El formato solo permite caracteres numericos entre 1 y 99",
                    minLength= 1, 
                    maxLength=2,
                    campo)
}
tiempodeusoInput.addEventListener("input", (e) => 
    validarTiempodeuso(tiempodeusoInput)
);
tiempodeusoInput.addEventListener("blur", (e) => 
    validarCampoVacio(tiempodeusoInput)
);

//### ESTADO ###
const validarEstado = (campo) => {
    let opcionElegida=campo.options[campo.selectedIndex].textContent
    if(campo.value==0){
        return validarCampoVacio(campo)
    }
    let opcionesvalidas=["Funciona perfecto" , "Funciona a medias" , "No funciona"]
    return validarSelect(opcionesvalidas.includes(opcionElegida),campo)
}
estadoInput.addEventListener("change",() => {
    validarEstado(estadoInput)
})
//### ARCHIVOS ###
const validarArchivos = (campo) => {
    let files = campo.files
    if (!files){
        return validarCampoVacio(campo)
    };

  // validación del número de archivos
  
  
  // validación del tipo de archivo
  let typeValid = true;

  for (const file of files) {
    // el tipo de archivo debe ser "image/<foo>" o "application/pdf"
    let fileFamily = file.type.split("/")[0];
    typeValid &&= fileFamily == "image" || file.type == "application/pdf";
  }

  // devolvemos la lógica AND de las validaciones.
  return validarTamanno(3,campo) && typeValid;
}

archivosInput.addEventListener("change",()=>{
    validarArchivos(archivosInput)
})

const handleFormSubmit = (event) => {

    console.log("Validating form...");

    let isValid = true;
    //NOMBRE
    if (!validarFormato(regex = /^[A-Za-z\s]+$/,
                        regexFeedback="El formato solo permite caracteres",
                        minLength= 3,
                        maxLength= 80,
                        campo= nombreInput)) {
        isValid = false;
    }
    //EMAIL
    if (!validarFormato(regex=/^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/,
                        regexFeedback="Debe cumplir con el formato de correo electrónico, tucorreo@dominio.TDL, TDL debe ser al menos 2 caracteres",
                        minLength= 5,
                        maxLength=30 ,
                        campo = emailInput)) {
        isValid = false;
    }

    //REGION
    if(!validarRegion(regionInput)){
        console.log("regiooon")
        isValid = false;
    }
    //COMUNA
    if(!validarComuna(regionInput,comunaInput)){
        console.log("comunaaa")
        isValid = false;
    }


    //DISPOSITIVO
    if (!validarFormato(regex = /^[A-Za-z\s]+$/,
        regexFeedback="El formato solo permite caracteres",
        minLength= 3,
        maxLength= 80,
        campo= dispositivoInput)) {
            console.log("dispositivooo")
        isValid = false;
    }
    //TIPO
    if(!validarTipo(tipoInput)){
        console.log("tipo")
        isValid=false;
    }

    //AÑOS DE USO
    if(!validarTiempodeuso(tiempodeusoInput)){
        console.log("tiempoooo")
        isValid=false;
    }

    //ESTADO
    if(!validarEstado(estadoInput)){
        console.log("estadoooo")
        isValid=false;
    }

    //ARCHIVOS
    if(!validarArchivos(archivosInput)){
        console.log("archivos")
        isValid=false;
    }
    if (isValid) {
        if (confirm("Confirma el registro del formulario?")) {
            document.querySelector("section").hidden = true;
            document.getElementById("submited-form").style.display = "block";
        }
    }

};

const submitButton = document.getElementById("button-submit");
submitButton.addEventListener("click", handleFormSubmit);
