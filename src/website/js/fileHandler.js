const dropZone = document.querySelector('.upload-area');
const xmlInput = document.getElementById('xmlInput');
const fileInput = document.getElementById('fileInput');

fileInput.addEventListener('change', function(e) {
    if (e.target.files.length > 0) {
        readXML(e.target.files[0]);
    }
});

['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
    dropZone.addEventListener(eventName, e => {
        e.preventDefault();
        e.stopPropagation();
    }, false);
});

['dragenter', 'dragover'].forEach(eventName => {
    dropZone.addEventListener(eventName, () => dropZone.style.borderColor = '#007bff', false);
});

['dragleave', 'drop'].forEach(eventName => {
    dropZone.addEventListener(eventName, () => dropZone.style.borderColor = '', false);
});

dropZone.addEventListener('drop', (e) => {
    const files = e.dataTransfer.files;
    if (files.length > 0) {
        readXML(files[0]);
    }
}, false);

function readXML(file) {
    if (file.name.endsWith('.xml')) {
        const reader = new FileReader();
        reader.onload = function(ev) {
            xmlInput.value = ev.target.result; 
        };
        reader.readAsText(file);
    } else {
        alert("Please upload a valid .xml file.");
    }
}
