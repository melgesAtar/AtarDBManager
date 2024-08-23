document.addEventListener('DOMContentLoaded', () => {
    fetch('http://localhost:8080/session/session-info',{
        method : 'GET',
        credentials: 'include'
    })
    .then(response => response.json())
    .then(data =>{
        const name = data.name;
        if(name){
            document.getElementById('userSpan').innerText = name;
        }else{
            console.log('Nome de usuário não encontrado')
        }
    })
    .catch(error =>{
        console.error('Erro ao buscar as informações da sessão', error)
    });

        });