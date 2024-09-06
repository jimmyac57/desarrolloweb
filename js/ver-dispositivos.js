//para hacer que solo se muestre el dispositivo seleccionado en informacion-dispositivos.html
const redirect = (fila) => {
    const deviceId = fila.getAttribute('data-id');
    window.location.href = `informacion-dispositivos.html#${deviceId}`;
}

