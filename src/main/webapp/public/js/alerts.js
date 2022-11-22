const Sweet = Swal.mixin({
    toast: true,
    position: 'top',
    showConfirmButton: false,
    title: "Teste",
    timer: 3000,
    timerProgressBar: true,
    didOpen: (toast) => {
      toast.addEventListener('mouseenter', Swal.stopTimer)
      toast.addEventListener('mouseleave', Swal.resumeTimer)
    }
});

function alertSweet(msg = "", type = 0, time = 2) {

    let icon;
    time *= 1000;

    switch (type) {
        
        case 1:
            icon = "success";
        break;

        case 2:
            icon = "info";
        break;

        case 3:
            icon = "warning";
        break;

        default:
            icon = "error";
        break;

    }

    Sweet.fire({
        title: msg,
        icon: icon,
        timer: time
    });
}