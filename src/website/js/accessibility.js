function toggleModoGrande() {
    // Añade o quita una clase que agranda todo visualmente
    document.body.classList.toggle('modo-grande');
}

function toggleDaltonismo() {
    document.body.classList.remove('high-contrast');
    document.body.classList.toggle('daltonismo');
}

function toggleContraste() {
    document.body.classList.remove('daltonismo');
    document.body.classList.toggle('high-contrast');
}