document.getElementById("region").addEventListener("change", function () {
    const regionId = this.value;

    if (regionId === "0") {
        document.getElementById("comuna").innerHTML = '<option value="0">Seleccione una comuna</option>';
        return;
    }

    fetch(`/api/region/${regionId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error HTTP: ${response.status}`);
            }
            return response.json(); 
        })
        .then(comunas => {
            const comunaSelect = document.getElementById("comuna");
            comunaSelect.innerHTML = '<option value="0">Seleccione una comuna</option>';
            comunas.forEach(comuna => {
                const option = document.createElement("option");
                option.value = comuna.id;
                option.text = comuna.nombre;
                comunaSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error("Error al cargar comunas:", error);
        });
});
