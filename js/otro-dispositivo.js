let counter = 0;

function generateUniqueId(prefix) {
    counter++;
    return `${prefix}_${Date.now()}_${counter}`;
}

const crearInputs = (id, id2,id3,id4,id5,id6,numerodispositivo) => {
    const html= `<fieldset><legend> dispositivo numero ${numerodispositivo} </legend><label for=${id} ><em>Nombre del dispositivo:</em></label>
          <input type="text" id=${id} name="dispositivo[]" minlength="3" maxlength=80 size="80" required><span></span><br><br>
    <label for=${id2}>Descripción:</label>
          <textarea id=${id2} rows="4" cols="50" name="descripcion" required></textarea><span></span><br><br>

          <label for=${id3}>Tipo:</label>
          <select type="select" id=${id3} name="tipo[]" required>
            <option value="0">Seleccione una opción</option>
            <option value="1">Pantalla</option>
            <option value="2">Notebook</option>
            <option value="3">Tablet</option>
            <option value="4">Celular</option>
            <option value="5">Consola</option>
            <option value="6">Mouse</option>
            <option value="7">Teclado</option>
            <option value="8">Impresora</option>
            <option value="9">Parlante</option>
            <option value="10">Audífonos</option>
            <option value="11">Otro</option>
          </select><span></span>
          <br><br>

          <label for=${id4}>Años de uso:</label>
          <input type="number" size="3" maxlength="" min="1" max="99" id=${id4} name="uso[]" required><span></span><br><br>
          
          <label for=${id5}>Estado de funcionamiento:</label>
          <select type="text" id=${id5} name="estado[]" required>
            <option value="0">Seleccione una opción</option>
            <option value="1">Funciona perfecto</option>
            <option value="2">Funciona a medias</option>
            <option value="3">No funciona</option>
          </select><span></span><br><br>

          <label for="${id6}">Fotos del producto:</label>
          <input type="file" id=${id6} name="archivos[]" multiple required><span></span><br><br>

          </fieldset>
        `;
    return html;
}

let numerodispositivo = 2;

document.getElementById('addDeviceBtn').addEventListener('click', () => {
    const contenedorDispositivo= document.getElementById('contenedor-dispositivo');
    
    // Generar IDs únicos para cada nuevo dispositivo
    const id = generateUniqueId("dispositivo");
    const id2 =  generateUniqueId("descripcion");
    const id3 =   generateUniqueId("tipo");
    const id4 =   generateUniqueId("uso");
    const id5 =   generateUniqueId("estado");
    const id6 =   generateUniqueId("archivos");

    // Generar HTML para el nuevo dispositivo
    const nuevoDispositivoHTML = crearInputs(id, id2, id3, id4, id5,id6,numerodispositivo);
    
    // Crear un nuevo contenedor y añadir el HTML generado
    const nuevoDispositivo = document.createElement('div');
    nuevoDispositivo.className = 'dispositivo';
    nuevoDispositivo.innerHTML = nuevoDispositivoHTML;
    
    // Añadir el nuevo dispositivo al contenedor principal
    contenedorDispositivo.appendChild(nuevoDispositivo);

    numerodispositivo++;
});

