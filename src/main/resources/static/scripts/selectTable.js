  document.querySelectorAll('.table').forEach(div => {
            div.addEventListener('click', () => {
                const tableName = div.getAttribute('data-table');
                window.location.href = `/edit-table/${tableName}`;
            });
        });