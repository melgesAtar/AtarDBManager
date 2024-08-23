 document.addEventListener('DOMContentLoaded', () => {
            const dataTable = document.getElementById('dataTable').querySelector('tbody');
            const formContainer = document.getElementById('formContainer');
            const formTitle = document.getElementById('formTitle');
            const recordForm = document.getElementById('recordForm');
            let editingRecordId = null;

            // Função para carregar os dados da tabela
            function loadTableData() {
                fetch('/api/activities/all')
                    .then(response => response.json())
                    .then(data => {
                        dataTable.innerHTML = '';
                        data.forEach(record => {
                            const row = dataTable.insertRow();
                            row.insertCell(0).innerText = record.id;
                            row.insertCell(1).innerText = record.name;
                            row.insertCell(2).innerText = record.date;

                            const actionsCell = row.insertCell(3);
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
                            });
                            actionsCell.appendChild(deleteButton);
                        });
                    });
            }

            // Função para abrir o formulário para adicionar/editar
            function openForm(record) {
                formContainer.style.display = 'block';
                formTitle.innerText = record ? 'Editar Atividade' : 'Adicionar Atividade';
                if (record) {
                    document.getElementById('recordId').value = record.id;
                    document.getElementById('name').value = record.name;
                    document.getElementById('date').value = record.date;
                    editingRecordId = record.id;
                } else {
                    recordForm.reset();
                    editingRecordId = null;
                }
            }

            // Função para adicionar/editar registro
            recordForm.addEventListener('submit', function (event) {
                event.preventDefault();

                const recordData = {
                    name: document.getElementById('name').value,
                    date: document.getElementById('date').value
                };

                if (editingRecordId) {
                    // Editar
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
                    // Adicionar
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

            // Função para excluir registro
            function deleteRecord(id) {
                fetch(`/api/activities/delete/${id}`, {
                    method: 'DELETE'
                }).then(() => {
                    loadTableData();
                });
            }

            // Ação do botão de adicionar
            document.getElementById('addRecordBtn').addEventListener('click', () => {
                openForm(null);
            });

            // Carregar dados ao iniciar
            loadTableData();
        });