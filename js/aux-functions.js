export const setErrors = (mensaje, campo, isError = true) => {
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

export const validarCampoVacio = (campo) => {
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

export const validarFormato = (regex, regexFeedback, min, max, campo) => {
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

export const validarTamanno = (max, campo) => {
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

export const validarSelect = (opcionesvalidas, campo) => {
  if (!campo) {
    setErrors("La selección no es válida", campo);
  }
  if (!opcionesvalidas) {
    return setErrors("El valor no coincide con el texto seleccionado", campo);
  }
  return setErrors("", campo, false);
};
