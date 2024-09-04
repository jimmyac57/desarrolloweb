
//funciones auxiliares para hacer las validaciones
import * as AF from './aux-functions.js';

//## INFO CONTACTO ##
const nombreInput = document.getElementById("nombre");
const emailInput = document.getElementById("email");
const celularInput = document.getElementById("celular");
const regionInput = document.querySelector("#region");
const comunaInput = document.querySelector('#comuna');

//#### NOMBRE ####
const validarNombre = (campo) => {
    return AF.validarFormato(/^[A-Za-z\s]+$/,"El formato solo permite caracteres literales",3,80,campo)
};
//#### EMAIL ####
const validarEmail = (campo) => {
    return AF.validarFormato(/^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/,"Debe cumplir con el formato de correo electrónico, tucorreo@dominio.com",5,30,campo = emailInput)
};
//#### CELULAR ####
const validarCelular = (campo) =>{
    return AF.validarFormato(/^[0-9]{3,15}$/,"El formato solo permite numeros",3,15,campo = celularInput);
};
//#### REGION ####
const validarRegion = (campo) => {
    let textoVisible=campo.options[campo.selectedIndex].textContent;
    if(campo.value==0){
        return AF.validarCampoVacio(campo)
    }
    let cond1 = regiones_comunas.regiones.find(region => region.id == campo.value);
    let cond2 = regiones_comunas.regiones.find(region => region.nombre == textoVisible);
    return AF.validarSelect(cond1==cond2,campo)
};
//#### COMUNA ####
const validarComuna = (regionSelected,campo) => {
    let textoVisible=campo.options[campo.selectedIndex].textContent;
    if(campo.value==0){
        return AF.validarCampoVacio(campo)
    }
    let regionSeleccionada = regiones_comunas.regiones.find(region => region.id == regionSelected.value);
    let cond1 = regionSeleccionada.comunas.find(comuna => comuna.id == campo.value);
    let cond2 = regionSeleccionada.comunas.find(comuna => comuna.nombre == textoVisible);
    return AF.validarSelect(cond1==cond2,campo)
};
//### DISPOSITIVO ###
const validarDispositivo = (campo) => {
    return AF.validarFormato( /^[A-Za-z\s]+$/,"El formato solo permite caracteres literales",3,80,campo)
};
//### TIPO ###
const validarTipo = (campo) => {
    let opcionElegida=campo.options[campo.selectedIndex].textContent
    if(campo.value==0){
        return AF.validarCampoVacio(campo)
    }
    let opcionesvalidas=["Pantalla","Notebook","Tablet","Celular","Consola","Mouse","Teclado","Impresora","Parlante","Audífonos","Otro"]
    return AF.validarSelect(opcionesvalidas.includes(opcionElegida),campo)
};
//### TIEMPO DE USO ###
const validarTiempodeuso= (campo) => {
    return AF.validarFormato( /^(?:[1-9]|[1-9][0-9])$/,"El formato solo permite caracteres numericos entre 1 y 99",1,2,campo)
};
//### ESTADO ###
const validarEstado = (campo) => {
    let opcionElegida=campo.options[campo.selectedIndex].textContent
    if(campo.value==0){
        return AF.validarCampoVacio(campo)
    }
    let opcionesvalidas=["Funciona perfecto" , "Funciona a medias" , "No funciona"]
    return AF.validarSelect(opcionesvalidas.includes(opcionElegida),campo)
};
//### ARCHIVOS ###
const validarArchivos = (campo) => {
    let files = campo.files
    if (!files){
        return AF.validarCampoVacio(campo)
    };
  let typeValid = true;

  for (const file of files) {
    // el tipo de archivo debe ser "image/<foo>" o "application/pdf"
    let fileFamily = file.type.split("/")[0];
    typeValid &&= fileFamily == "image" || file.type == "application/pdf";
  }
  if(!typeValid){
    return AF.setErrors("El archivo debe ser una imagen (JPEG, PNG, GIF, etc.) o un PDF",campo);
  }

  // devolvemos la lógica AND de las validaciones.
  return AF.validarTamanno(3,campo) && typeValid;
};

//## VALIDACIONES DINAMICAS ##
//validacion dinamica nombre
nombreInput.addEventListener("input", () =>{ 
    validarNombre(nombreInput)
});
nombreInput.addEventListener("blur", () => {
    AF.validarCampoVacio(nombreInput)
});

//validacion dinamica email
emailInput.addEventListener("input", () => {
    validarEmail(emailInput)
});
emailInput.addEventListener("blur", () => {
    AF.validarCampoVacio(emailInput)
});

//validacion dinamica celular
celularInput.addEventListener("input", () => {
    validarCelular(celularInput)
});

//validacion dinamica region
regionInput.addEventListener("change",() => {
    validarRegion(regionInput)
});

//validacion dinamica comuna
comunaInput.addEventListener("change",()=>{
    validarComuna(regionInput,comunaInput)
});

document.getElementById('form').addEventListener('input', (event) => {
    if (event.target.matches('input[name="dispositivo[]"]')) {
        validarDispositivo(event.target);
    }
});

document.getElementById('form').addEventListener('blur', (event) => {
    if (event.target.matches('input[name="dispositivo[]"]')) {
        AF.validarCampoVacio(event.target);
    }
}, true);

document.getElementById('form').addEventListener('change', (event) => {
    if (event.target.matches('select[name="tipo[]"]')) {
        validarTipo(event.target);
    }
});

document.getElementById('form').addEventListener('input', (event) => {
    if (event.target.matches('input[name="uso[]"]')) {
        validarTiempodeuso(event.target);
    }
});

document.getElementById('form').addEventListener('blur', (event) => {
    if (event.target.matches('input[name="uso[]"]')) {
        AF.validarCampoVacio(event.target);
    }
}, true);

document.getElementById('form').addEventListener('change', (event) => {
    if (event.target.matches('select[name="estado[]"]')) {
        validarEstado(event.target);
    }
});

document.getElementById('form').addEventListener('change', (event) => {
    if (event.target.matches('input[name="archivos[]"]')) {
        validarArchivos(event.target);
    }
});

const handleFormSubmit = (event) => {

    console.log("Validating form...");

        const dispositivoInputs= document.getElementsByName("dispositivo[]");
        const tipoInputs= document.getElementsByName("tipo[]");
        const tiempodeusoInputs= document.getElementsByName("uso[]");
        const estadoInputs= document.getElementsByName("estado[]");
        const archivosInputs= document.getElementsByName("archivos[]");

    let isValid = true;
    //NOMBRE
    if (!validarNombre(nombreInput)) {
        isValid = false;
    }
    //EMAIL
    if (!validarEmail(emailInput)) {
        isValid = false;
    }

    //REGION
    if(!validarRegion(regionInput)){
        isValid = false;
    }
    //COMUNA
    if(!validarComuna(regionInput,comunaInput)){
        isValid = false;
    }


    //DISPOSITIVO
    for(let i=0;i<dispositivoInputs.length;i++){
        if(!validarDispositivo(dispositivoInputs[i])){
            isValid=false;
        }
    }
    //TIPO
    for(let i=0;i<tipoInputs.length;i++){
        if(!validarTipo(tipoInputs[i])){
            isValid=false;
        }
    }

    //AÑOS DE USO
    for(let i=0;i<tiempodeusoInputs.length;i++){
        if(!validarTiempodeuso(tiempodeusoInputs[i])){
            isValid=false;
        }
    }
    

    //ESTADO
    for(let i=0;i<estadoInputs.length;i++){
        if(!validarEstado(estadoInputs[i])){
            isValid=false;
        }
    }

    //ARCHIVOS
    for(let i=0;i<archivosInputs.length;i++){
        if(!validarArchivos(archivosInputs[i])){
            isValid=false;
        }
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
