// Selects the main elements used for XML uploading and drag-and-drop
const dropZone = document.querySelector('.upload-area');
const xmlInput = document.getElementById('xmlInput');
const fileInput = document.getElementById('fileInput');

// Triggered when the user selects a file using the hidden file input
fileInput.addEventListener('change', function(e) {
    if (e.target.files.length > 0) {
        // Reads the selected XML file
        readXML(e.target.files[0]);
    }
});

// Prevent default browser behavior for drag & drop events
['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
    dropZone.addEventListener(eventName, e => {
        e.preventDefault();     // Prevents file from opening in the browser
        e.stopPropagation();    // Stops event from bubbling up
    }, false);
});

// Highlight the drop zone when a file is dragged over it
['dragenter', 'dragover'].forEach(eventName => {
    dropZone.addEventListener(eventName, () => 
        dropZone.style.borderColor = '#007bff', 
    false);
});

// Remove highlight when the file leaves or is dropped
['dragleave', 'drop'].forEach(eventName => {
    dropZone.addEventListener(eventName, () => 
        dropZone.style.borderColor = '', 
    false);
});

// Handles the actual file drop event
dropZone.addEventListener('drop', (e) => {
    const files = e.dataTransfer.files;
    if (files.length > 0) {
        // Reads the dropped XML file
        readXML(files[0]);
    }
}, false);

// Reads the content of an XML file and places it inside the textarea
function readXML(file) {
    // Ensures the file has a .xml extension
    if (file.name.endsWith('.xml')) {
        const reader = new FileReader();

        // When the file is fully loaded, insert its text into the textarea
        reader.onload = function(ev) {
            xmlInput.value = ev.target.result;
        };

        // Reads the file as plain text
        reader.readAsText(file);

    } else {
        // Shows an alert if the file is not XML
        alert("Please upload a valid .xml file.");
    }
}
