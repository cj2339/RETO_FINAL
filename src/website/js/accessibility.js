// Toggles the "large mode" which scales tables and content for better readability
function toggleModoGrande() {
    document.body.classList.toggle('modo-grande');
}

// Toggles the color-blind friendly mode (blue–yellow palette)
// Also ensures the high-contrast mode is disabled to avoid conflicts
function toggleDaltonismo() {
    document.body.classList.remove('high-contrast'); // Prevent overlapping modes
    document.body.classList.toggle('daltonismo');    // Activate/deactivate color-blind mode
}

// Toggles the high-contrast red mode for users with low vision
// Also ensures the color-blind mode is disabled to avoid visual conflicts
function toggleContraste() {
    document.body.classList.remove('daltonismo');    // Prevent overlapping modes
    document.body.classList.toggle('high-contrast'); // Activate/deactivate high-contrast mode
}
