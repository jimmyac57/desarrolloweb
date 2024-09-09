const poblarRegiones = () => {
    let regionSelect = document.getElementById("region");
    for (let i = 0; i < regiones_comunas.regiones.length; i++) {
        const region = regiones_comunas.regiones[i];
        let option = document.createElement("option");
        option.value=region.id;
        option.text=region.nombre;
        regionSelect.appendChild(option);
    }
}

const actualizarComunas = () => {
    let regionSelect = document.getElementById("region");
    let comunaSelect = document.getElementById("comuna");

    let selectedRegionId = regionSelect.value;

    comunaSelect.innerHTML='<option value="0">Seleccione una comuna</option>';

    const regionSeleccionada = regiones_comunas.regiones.find(region => region.id == selectedRegionId);
    if (regionSeleccionada) {
        for(let i = 0; i < regionSeleccionada.comunas.length; i++){
            const comuna = regionSeleccionada.comunas[i]
            let option = document.createElement("option");
            option.value=comuna.id;
            option.innerText=comuna.nombre;
            comunaSelect.appendChild(option);
        };
    }
}

document.getElementById("region").addEventListener("change",actualizarComunas);

window.onload = () => {
    poblarRegiones();
}
