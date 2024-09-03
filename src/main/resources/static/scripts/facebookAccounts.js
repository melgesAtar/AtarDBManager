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

        fetch(`/api/facebookAccounts/employee/${employeeId}`)
            .then(response => response.json())
            .then(data => {

                dataTable.innerHTML = '';
                data.forEach(record => {
                    const row = dataTable.insertRow();

                    row.insertCell(0).innerText = record.id;
                    row.insertCell(1).innerText = record.username;
                    row.insertCell(2).innerText = record.password;

                    // Cria a célula para o cliente
                    const clientCell = row.insertCell(3);
                    clientCell.innerText = record.client.name;
                    clientCell.setAttribute('data-client-id', record.client.id); // Adiciona o atributo personalizado

                    row.insertCell(4).innerText = record.active;
                    row.insertCell(5).innerText = record.city;
                    row.insertCell(6).innerText = record.neighborhood;
                    if (record.niche && record.niche.name) {
                        row.insertCell(7).innerText = record.niche.name;
                    } else {
                        row.insertCell(7).innerText = "Não Atribuido";
                    }
                    row.insertCell(8).innerText = record.address;
                    row.insertCell(9).innerText = record.uf;

                    const actionsCell = row.insertCell(10);
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
            .catch(error => console.error('Erro ao carregar as contas do facebook:', error));
    }

    function openForm(record) {

        formContainer.classList.add('active');
        recordForm.reset();
        formTitle.innerText = record ? 'Editar Contas Facebook' : 'Adicionar Contas Facebook';



        if (record) {

            document.getElementById('recordId').value = record.id;
            document.getElementById('username').value = record.username;
            document.getElementById('password').value = record.password;
            document.getElementById('status').value = record.status;
            document.getElementById('address').value = record.address;
            document.getElementById('city').value = record.city;
            document.getElementById('neighborhood').value = record.neighborhood;
            document.getElementById('uf').value = record.uf;
            editingRecordId = record.id;

        } else {
            recordForm.reset();
            editingRecordId = null;
        }

        loadClients(record ? record.client.id : null);
        loadStatus(record ? record.active : null);

         if (record && record.niche && record.niche.id !== undefined) {
                const selectedNiche = record.niche.id;
                loadNiches(selectedNiche);
            } else {
                console.error('Registro ou cliente não está definido ou não possui um id válido.');
                loadNiches(null);
            }



    }

   function loadStatus(statusSelected = null) {
       const statusSelect = document.getElementById('status');
       statusSelect.innerHTML = '';

       const status = [
           { id: true, name: 'Ativo' },
           { id: false, name: 'Inativo' }
       ];

       status.forEach(status => {
           const option = document.createElement('option');
           option.value = status.id;
           option.text = status.name;
           if (status.id === statusSelected) {
               option.selected = true;
           }
           statusSelect.add(option);

       });
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

    function loadNiches(selectedNiche = null) {
        const nicheSelect = document.getElementById('niche');
        nicheSelect.innerHTML = '';

        fetch('http://localhost:8080/api/nicheController/niches')
            .then(response => response.json())
            .then(niches => {

                if (Array.isArray(niches)) {
                    niches.forEach(niche => {
                        const option = document.createElement('option');
                        option.value = niche.id;
                        option.text = niche.name;


                        if (selectedNiche !== null && niche.id === selectedNiche) {
                            option.selected = true;
                        }

                        nicheSelect.add(option);
                    });

                    // Selecionar o primeiro item se selectedNiche for nulo e o select não estiver vazio
                    if (selectedNiche === null && nicheSelect.options.length > 0) {
                        nicheSelect.options[0].selected = true;
                    }
                } else {
                    console.error('Erro: O retorno da API não é um array válido.');
                }
            })
            .catch(error => console.error('Erro ao carregar os nichos:', error));
    }


    recordForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = new FormData(recordForm);

        const recordData = {
            username :  event.target.username.value,
            password: event.target.password.value,
            client : event.target.client.value,
            status : event.target.status.value,
            address: event.target.address.value,
            neighborhood: event.target.neighborhood.value,
            city: event.target.city.value,
            niche: event.target.niche.value,
            uf: event.target.uf.value,
        };



        if (editingRecordId) {
            fetch(`/api/facebookAccounts/edit/${editingRecordId}`, {
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
