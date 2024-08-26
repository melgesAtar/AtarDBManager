document.addEventListener('DOMContentLoaded', () => {
    const dataTable = document.getElementById('dataTable').querySelector('tbody');
    const formContainer = document.getElementById('formContainer');
    const formTitle = document.getElementById('formTitle');
    const recordForm = document.getElementById('recordForm');
    let editingRecordId = null;
    let employeeId = null;

    // Fetch the current user information
    fetch('/api/user/current')
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Não autenticado');
            }
        })
        .then(data => {
            employeeId = data.userId; // Certifique-se de que o employeeId é capturado corretamente
            loadTableData();
        })
        .catch(error => console.error('Erro ao obter o usuário autenticado:', error));

    // Load table data
    function loadTableData() {
        if (!employeeId) return;

        fetch(`/api/activities/employee/${employeeId}`)
            .then(response => response.json())
            .then(data => {
                dataTable.innerHTML = '';
                data.forEach(record => {
                    const row = dataTable.insertRow();

                    row.insertCell(0).innerText = record.id;
                    const clientIdCell = row.insertCell(1);
                    clientIdCell.innerText = record.clientID;
                    clientIdCell.style.display = 'none';
                    row.insertCell(2).innerText = record.clientName;
                    row.insertCell(3).innerText = record.nameActivity;
                    row.insertCell(4).innerText = record.quantity;
                    row.insertCell(5).innerText = record.date;

                    const actionsCell = row.insertCell(6);
                    const editButton = document.createElement('button');
                    editButton.innerText = 'Editar';
                    editButton.addEventListener('click', () => {
                        openForm(record);
                    });
                    actionsCell.appendChild(editButton);

                    const deleteButton = document.createElement('button');
                    deleteButton.innerText = 'Excluir';
                    deleteButton.addEventListener('click', () => {
                        deleteRecord(record.id);
                        window.location.reload(true);
                    });
                    actionsCell.appendChild(deleteButton);
                });
            })
            .catch(error => console.error('Erro ao carregar as atividades:', error));
    }

    // Open form to add/edit record
    function openForm(record) {
        formContainer.style.display = 'block';
        formTitle.innerText = record ? 'Editar Atividade' : 'Adicionar Atividade';
        loadClients(record ? record.clientID : null);
        loadActivities(record ? record.nameActivity : null);

        if (record) {
            document.getElementById('recordId').value = record.id;
            document.getElementById('clientSelect').value = record.clientID; // Preenche o select com o ID do cliente
            document.getElementById('activitySelect').value = record.nameActivity;
            document.getElementById('quantity').value = record.quantity;
            document.getElementById('date').value = record.date;
            editingRecordId = record.id;
        } else {
            recordForm.reset();
            editingRecordId = null;
        }
    }

    // Load clients into the select box
    function loadClients(selectedClientId = null) {
        const clientSelect = document.getElementById('clientSelect');
        clientSelect.innerHTML = ''; // Clear existing options

        fetch(`/api/user/clients/${employeeId}`)
            .then(response => response.json())
            .then(clients => {
                clients.forEach(client => {
                    const option = document.createElement('option');
                    option.value = client.id; // Send client ID to backend
                    option.text = client.name; // Display client name to user
                    if (client.id === selectedClientId) {
                        option.selected = true; // Pre-select the client's name if editing
                    }
                    clientSelect.add(option);
                });
            })
            .catch(error => console.error('Erro ao carregar os clientes:', error));
    }

    // Load activities into the select box
    function loadActivities(selectedActivity = null) {
        const activitySelect = document.getElementById('activitySelect');
        activitySelect.innerHTML = ''; // Clear existing options

        // Activities options (assuming you have them hardcoded)
        const activities = ['Curtidas Instagram', 'Curtidas Facebook', 'Comentários Instagram', 'Comentários Facebook','Compartilhamentos Instagram', 'Compartilhamentos Facebook'];

        activities.forEach(activity => {
            const option = document.createElement('option');
            option.value = activity;
            option.text = activity;
            if (activity === selectedActivity) {
                option.selected = true; // Pre-select the activity if editing
            }
            activitySelect.add(option);
        });
    }

    // Handle form submission for adding/editing records
    recordForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const recordData = {
            employeeID: employeeId, // Employee ID
            client: {  // Enviando o ID do cliente como parte de um objeto Client
                    id: document.getElementById('clientSelect').value
                },
            nameActivity: document.getElementById('activitySelect').value,
            quantity: document.getElementById('quantity').value,
            date: document.getElementById('date').value
        };


        console.log("JSON Enviado:", JSON.stringify(recordData));
        if (editingRecordId) {
            console.log(recordData);
            fetch(`/api/activities/edit/${editingRecordId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(recordData)
            }).then(() => {
                formContainer.style.display = 'none';
                loadTableData();
            });
        } else {
            // Add new record
            console.log(recordData);
            fetch('/api/activities/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(recordData)
            }).then(() => {
                formContainer.style.display = 'none';
                loadTableData();
            });
        }
    });

    // Delete record function
    function deleteRecord(id) {
        fetch(`/api/activities/delete/${id}`, {
            method: 'DELETE'
        }).then(() => {
            loadTableData();
        });
    }

    // Add new record button click action
    document.getElementById('addRecordBtn').addEventListener('click', () => {
        openForm(null);
    });
});
