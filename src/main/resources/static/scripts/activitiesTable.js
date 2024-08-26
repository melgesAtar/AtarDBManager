document.addEventListener('DOMContentLoaded', () => {
    const dataTable = document.getElementById('dataTable').querySelector('tbody');
    const formContainer = document.getElementById('formContainer');
    const formTitle = document.getElementById('formTitle');
    const recordForm = document.getElementById('recordForm');
    let editingRecordId = null;
    let employeeId = null;

    fetch('/api/user/current')
        .then(response => response.json())
        .then(data => {
            employeeId = data.userId;
            loadTableData();
        })
        .catch(error => console.error('Erro ao obter o usuário autenticado:', error));

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
                    editButton.classList.add('edit-button');
                    editButton.classList.add('description__text');
                    editButton.addEventListener('click', () => {
                        openForm(record);
                    });
                    actionsCell.appendChild(editButton);

                    const deleteButton = document.createElement('button');
                    deleteButton.innerText = 'Excluir';
                    deleteButton.classList.add('delete-button');
                    deleteButton.classList.add('description__text');
                    deleteButton.addEventListener('click', () => {
                        deleteRecord(record.id);
                        loadTableData();
                    });
                    actionsCell.appendChild(deleteButton);
                });
            })
            .catch(error => console.error('Erro ao carregar as atividades:', error));
    }

    function openForm(record) {
        formContainer.classList.add('active');
        formTitle.innerText = record ? 'Editar Atividade' : 'Adicionar Atividade';

        loadClients(record ? record.clientID : null);
        loadActivities(record ? record.nameActivity : null);

        if (record) {
            document.getElementById('recordId').value = record.id;
            document.getElementById('clientSelect').value = record.clientID;
            document.getElementById('activitySelect').value = record.nameActivity;
            document.getElementById('quantity').value = record.quantity;
            document.getElementById('date').value = record.date;
            editingRecordId = record.id; // Certifique-se de que o ID está sendo atribuído corretamente
        } else {
            recordForm.reset();
            editingRecordId = null; // Limpa o ID ao adicionar um novo registro
        }
    }

    function loadClients(selectedClientId = null) {
        const clientSelect = document.getElementById('clientSelect');
        clientSelect.innerHTML = '';

        fetch(`/api/user/clients/${employeeId}`)
            .then(response => response.json())
            .then(clients => {
                clients.forEach(client => {
                    const option = document.createElement('option');
                    option.value = client.id;
                    option.text = client.name;
                    if (client.id === selectedClientId) {
                        option.selected = true;
                    }
                    clientSelect.add(option);
                });
            })
            .catch(error => console.error('Erro ao carregar os clientes:', error));
    }

    function loadActivities(selectedActivity = null) {
        const activitySelect = document.getElementById('activitySelect');
        activitySelect.innerHTML = '';

        const activities = ['Curtidas Instagram', 'Curtidas Facebook', 'Comentários Instagram', 'Comentários Facebook', 'Compartilhamentos Instagram', 'Compartilhamentos Facebook'];

        activities.forEach(activity => {
            const option = document.createElement('option');
            option.value = activity;
            option.text = activity;
            if (activity === selectedActivity) {
                option.selected = true;
            }
            activitySelect.add(option);
        });
    }

    recordForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const recordData = {
            employeeID: employeeId,
            client: { id: document.getElementById('clientSelect').value },
            nameActivity: document.getElementById('activitySelect').value,
            quantity: document.getElementById('quantity').value,
            date: document.getElementById('date').value
        };

        if (editingRecordId) {
            fetch(`/api/activities/edit/${editingRecordId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(recordData)
            })
            .then(() => {
                formContainer.classList.remove('active');
                loadTableData();
            });
        } else {
            fetch('/api/activities/add', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(recordData)
            })
            .then(() => {
                formContainer.classList.remove('active');
                loadTableData();
            });
        }
    });

    function deleteRecord(id) {
        fetch(`/api/activities/delete/${id}`, {
            method: 'DELETE'
        })
        .then(() => {
            loadTableData();
        });
    }

    document.getElementById('addRecordBtn').addEventListener('click', () => {
        openForm(null);
    });
});
