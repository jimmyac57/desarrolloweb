document.addEventListener('DOMContentLoaded', function() {
    const contenedor = document.getElementById('dispositivos-container');
    const btnAgregar = document.getElementById('agregar-dispositivo');
    const template = document.getElementById('dispositivo-template');

    btnAgregar.addEventListener('click', function() {
        const dispositivoCount = contenedor.querySelectorAll('.dispositivo-item').length;

        const clone = template.content.cloneNode(true);
        clone.querySelector("legend").textContent = "Dispositivo " + (dispositivoCount + 1);

        clone.querySelector('input[name="nombre"]').setAttribute('name', `dispositivos[${dispositivoCount}].nombre`);
        clone.querySelector('textarea[name="descripcion"]').setAttribute('name', `dispositivos[${dispositivoCount}].descripcion`);
        clone.querySelector('select[name="tipo"]').setAttribute('name', `dispositivos[${dispositivoCount}].tipo`);
        clone.querySelector('select[name="estado"]').setAttribute('name', `dispositivos[${dispositivoCount}].estado`);
        clone.querySelector('input[name="archivos"]').setAttribute('name', `dispositivos[${dispositivoCount}].archivos`);
        clone.querySelector('input[name="uso"]').setAttribute('name', `dispositivos[${dispositivoCount}].uso`);

        contenedor.appendChild(clone);
    });
});
