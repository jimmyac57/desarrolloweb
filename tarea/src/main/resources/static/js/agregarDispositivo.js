document.addEventListener('DOMContentLoaded', function() {
    const contenedor = document.getElementById('dispositivos-container');
    const btnAgregar = document.getElementById('agregar-dispositivo');
    const template = document.getElementById('dispositivo-template');

    btnAgregar.addEventListener('click', function() {
        // Contar cuántos dispositivos hay actualmente
        const dispositivoCount = contenedor.querySelectorAll('.dispositivo-item').length;

        // Clonamos el template
        const clone = template.content.cloneNode(true);

        // Actualizamos el encabezado del dispositivo
        clone.querySelector("legend").textContent = "Dispositivo " + (dispositivoCount + 1);

        // Actualizamos los nombres de los inputs
        clone.querySelector('input[name="nombre"]').setAttribute('name', `dispositivos[${dispositivoCount}].nombre`);
        clone.querySelector('textarea[name="descripcion"]').setAttribute('name', `dispositivos[${dispositivoCount}].descripcion`);
        clone.querySelector('select[name="tipo"]').setAttribute('name', `dispositivos[${dispositivoCount}].tipo`);
        clone.querySelector('select[name="estado"]').setAttribute('name', `dispositivos[${dispositivoCount}].estado`);
        clone.querySelector('input[name="archivos"]').setAttribute('name', `dispositivos[${dispositivoCount}].archivos`);
        clone.querySelector('input[name="uso"]').setAttribute('name', `dispositivos[${dispositivoCount}].uso`);

        // Añadimos el clon al contenedor
        contenedor.appendChild(clone);
    });
});
