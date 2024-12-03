document.querySelectorAll('tbody tr').forEach(fila => {
    fila.addEventListener('click', function() {
        const deviceId = this.getAttribute('data-id');
        window.location.href = `/informaciondispositivos/${deviceId}`; 
    });
});




