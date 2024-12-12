// Get the modal
let modal = document.getElementById("modal");
let modalImg = document.getElementById("modal-img");

// close modal if click el x button
document.querySelector(".btn-close").onclick = () => {
    modal.style.display = "none";
  };
  
// close modal if click outside
modal.onclick = (event) => {
    if (!event.target.closest(".modal-content")) {
      modal.style.display = "none";
    }
};
  
// open modal
const openModal = (elem) => {
    modalImg.src = elem.src;
    modal.style.display = "block";
  };