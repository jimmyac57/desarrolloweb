
const contenedorDispositivo = document.getElementById("contenedor-dispositivo");


const device_Id = contenedorDispositivo.dataset.deviceId;
console.log("ID del dispositivo:", device_Id);

if (device_Id) {

  const imagenes = document.querySelectorAll(".zoomin");
  imagenes.forEach((imagen) => {
    imagen.addEventListener("click", () => {
      if (imagen.classList.contains("zoomin")) {
        imagen.classList.remove("zoomin");
        imagen.classList.add("zoomout");
        imagen.style.width = "1240px";
        imagen.style.height = "1080px";
      } else {
        imagen.classList.remove("zoomout");
        imagen.classList.add("zoomin");
        imagen.style.width = "640px";
        imagen.style.height = "480px";
      }
    });
  })

  
} else {
  contenedorDispositivo.innerHTML =
    "<p>Dispositivo no encontrado.</p>";
}
