document.querySelector('.button-logout').addEventListener('click', function() {
    fetch('/AuthController/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
    }).then(response => {
        if (response.ok) {
            window.location.href = '/login?logout'; // Redireciona após o logout
        } else {
            console.error('Erro ao tentar fazer logout');
        }
    }).catch(error => {
        console.error('Erro na requisição:', error);
    });
});
