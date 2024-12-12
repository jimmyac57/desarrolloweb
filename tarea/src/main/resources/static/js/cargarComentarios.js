const obtenerComentarios = (dispositivoId) => {
    fetch(`/api/comentarios/${dispositivoId}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error("Error al obtener los comentarios");
        }
        return response.json();
      })
      .then((comentarios) => {
        renderComentarios(comentarios);
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  };
  
  const renderComentarios = (comentarios) => {
    const contenedorComentarios = document.getElementById("contenedor-comentarios");
    contenedorComentarios.innerHTML = ""; 
  
    comentarios.forEach((comentario) => {
      const comentarioElemento = document.createElement("div");
      comentarioElemento.classList.add("comentario");
      comentarioElemento.innerHTML = `
        <div class="comentario-header">
          <strong>${comentario.nombre}</strong>
          <span>${new Date(comentario.fecha).toLocaleString()}</span>
        </div>
        <div class="comentario-body">
          <p>${comentario.texto}</p>
        </div>
      `;
      contenedorComentarios.appendChild(comentarioElemento);
    });
  };
  

  document.addEventListener("DOMContentLoaded", () => {
    const dispositivoId = document.querySelector('input[name="deviceId"]').value;
  
    obtenerComentarios(dispositivoId);
  });