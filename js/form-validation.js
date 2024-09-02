

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
    if(valor==0){
        return setErrors(`El campo ${campo.name} es obligatorio`,campo)
    }else{
        return false
    }
}
//Funcion para validar el formato de un campo
const validarFormato = (regex,regexFeedback, min, max, campo) => {
    const valor = campo.value.trim();
    const nombreRegex = regex;
    const minLength = min;
    const maxLength = max;
    console.log(campo.name)
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

//# CAMPOS DEL FORMULARIO #

//## INFO CONTACTO ##
const nombreInput = document.getElementById("nombre");
const emailInput = document.getElementById("email");
const celularInput = document.getElementById("celular");
const regionInput = document.getElementById("region");
const comunaInput = document.getElementById("comuna");

//### EVENTOS ###

//#### NOMBRE ####
nombreInput.addEventListener("input", (e) => 
    validarFormato( regex= /^[A-Za-z\s]+$/,
                    regexFeedback="El formato solo permite caracteres",
                    minLength= 3, 
                    maxLength=80,
                    campo= nombreInput)
);
nombreInput.addEventListener("blur", (e) => 
    validarCampoVacio(nombreInput)
);
//#### EMAIL ####
emailInput.addEventListener("input", (e) => 
    validarFormato( regex=/^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/,
                    regexFeedback="Debe cumplir con el formato de correo electrónico, tucorreo@dominio.TDL, TDL debe ser al menos 2 caracteres",
                    minLength= 5,
                    maxLength=30 ,
                    campo = emailInput)
);
emailInput.addEventListener("blur", (e) => 
    validarCampoVacio(emailInput)
);
//#### CELULAR ####

//#### REGION ####

//#### COMUNA ####

//## INFO DISPOSITIVO ##
const dispositivoInput = document.getElementById("nombre");
const tipoInput = document.getElementById("email");
const tiempoInput = document.getElementById("celular");
const estadoInput = document.getElementById("estado");
const archivosInput = document.querySelectorAll("[name=archivo]")





const handleFormSubmit = (event) => {
    event.preventDefault(); 

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

    if (isValid) {
        if (confirm("Confirma el registro del formulario?")) {
            document.getElementById("section").hidden = true;
            document.getElementById("submited-form").style.display = "block";
        }
    }
};

const submitButton = document.getElementById("button-submit");
submitButton.addEventListener("click", handleFormSubmit);
