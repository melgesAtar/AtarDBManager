const formLogin = document.querySelector("[data-form]");
const inputUsername = document.querySelector("[name= username]");
const inputPassword = document.querySelector("[name = password]");

formLogin.addEventListener('submit' , function (event){
  event.preventDefault();
  logar();
});



function logar() {
    fetch('http://localhost:8080/employees/auth', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: inputUsername.value,
            password: inputPassword.value
        })

    })
    .then(response => response.json())
    .then(data => {

        if (data) {
            console.log('Login bem-sucedido');
            formLogin.reset();
            fetch('http://localhost:8080/session/session-info')
                .then(response => response.json())
                .then(data => {
                    console.log('Session Data:', data);
                })
                .catch(error => {
                    console.error('Erro ao obter informações da sessão:', error);
                });

        } else {
            console.log('Autenticação falhou');

        }
    })
    .catch(error => {
        console.error('Erro ao tentar logar:', error);

    });

}

