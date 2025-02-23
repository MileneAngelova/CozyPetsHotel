function toggleEdit(field) {
    const viewElement = document.getElementById(field + "-view");
    const editElement = document.getElementById(field + "-edit");
    const editButton = document.getElementById(field + "-edit-button");
    if (viewElement) viewElement.style.display = "none";
    if (editElement) editElement.style.display = "block";
    if (editButton) editButton.style.display = "none";
}

function cancelEdit(field) {
    const viewElement = document.getElementById(field + "-view");
    const editElement = document.getElementById(field + "-edit");
    const editButton = document.getElementById(field + "-edit-button");
    if (viewElement) viewElement.style.display = "block";
    if (editElement) editElement.style.display = "none";
    if (editButton) editButton.style.display = "inline-block";
}