fetch("/api/admin/data")
    .then((response) => {
        if(!response.ok){
        return new Error("Error al obtener los datos de archivos");
    } else {   
        return response.json();
    }
    })
    .then(data => {
        const tablaArchivos = document.getElementById("tabla-archivos");
        tablaArchivos.innerHTML = ""; 
        console.log(data);
        data.forEach(dato => {
            const fila = `
                <tr>
                    <td>${dato.id_archivo}</td>
                    <td><img class="img-thumbnail mx-auto d-block imagen imagem-modal" src="${dato.ruta_archivo}" alt="Imagen ${dato.id_archivo}" onclick="openModal(this)"></td>
                    <td>${dato.nombre_dispositivo}</td>
                    <td>${dato.email_contacto}</td>
                    <td>
                        <button class="btn btn-danger btn-eliminar" data-id-archivo="${dato.id_archivo}" data-id-dispositivo="${dato.id_dispositivo}" data-id-contacto="${dato.id_contacto}">Eliminar</button>
                    </td>
                </tr>
            `;
            tablaArchivos.insertAdjacentHTML("beforeend", fila);
        });
    })
    .catch(error => console.error("Error:", error));