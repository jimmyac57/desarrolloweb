document.addEventListener("DOMContentLoaded", () => {
    const tableBody = document.getElementById("tabla-archivos"); 
    const cancelButton = document.getElementById("btn-cancelar");
    const motivo = document.getElementById("motivo");
    let ultimoBtn=null;
    cancelButton.addEventListener("click", () => {
        const myForm = document.getElementById("formEliminacion");
        myForm.style.display = "none";
        const fila = myForm.closest("tr").children[4];
        fila.children[0].textContent = "Eliminar";
        fila.children[0].classList.remove("disabled");
    });
    tableBody.addEventListener("click", (event) => {
      if (event.target.classList.contains("btn-eliminar")) {
        if (ultimoBtn && ultimoBtn !== event.target) {
            ultimoBtn.textContent = "Eliminar";
            ultimoBtn.classList.remove("disabled");
        }
        const idArchivo = event.target.getAttribute("data-id-archivo");
        const idArchivoInput = document.getElementById("idArchivo");
        idArchivoInput.value = idArchivo; 
        console.log("idarchivo",idArchivo);
        const idContacto =  event.target.getAttribute("data-id-contacto");
        const idContactoInput = document.getElementById("idContacto");
        idContactoInput.value = idContacto;
        console.log("idcontacto",idContacto);
        const idDispositivo = event.target.getAttribute("data-id-dispositivo");
        const idDispositivoInput = document.getElementById("idDispositivo");
        idDispositivoInput.value = idDispositivo;
        console.log("iddispositivo",idDispositivo);
        const myForm = document.getElementById("formEliminacion");
        const fila = event.target.closest("tr").children[4];
        ultimoBtn = fila.children[0];
        fila.children[0].textContent = "Eliminando...";
        fila.children[0].classList.add("disabled");
        motivo.value="";
        fila.appendChild(myForm);
        myForm.style.display = "block";
        motivo.focus();
        ultimoBtn=event.target;
      }
    });
  });
