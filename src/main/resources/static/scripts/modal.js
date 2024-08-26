// modal.js
document.addEventListener('DOMContentLoaded', () => {
    const formContainer = document.getElementById('formContainer');
    const addRecordBtn = document.getElementById('addRecordBtn');
    const closeModalBtn = document.getElementById('closeModalBtn');

    addRecordBtn.addEventListener('click', () => {
        openForm();
    });

    closeModalBtn.addEventListener('click', () => {
        closeForm();
    });

    function openForm(record) {
        formContainer.classList.add('active');
        // Configure the form as needed
    }

    function closeForm() {
        formContainer.classList.remove('active');
    }
});
