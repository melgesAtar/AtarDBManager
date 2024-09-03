 function searchCEP() {
        const cep = document.getElementById('buscaCep').value;
        let address = document.getElementById('address');
        let neighborhood = document.getElementById('neighborhood');
        let city = document.getElementById('city');
        let uf = document.getElementById('uf');


        if(!cep){
            console.error('Campo do CEP vazio')
            return;
        }


        fetch(`https://viacep.com.br/ws/${cep}/json/`)
        .then(response => {
            if(!response){
                throw new Error('Erro na resposta da rede ')
            }
            return response.json();
        })
        .then(data =>{
            address.value = data.logradouro ;
            neighborhood.value = data.bairro;
            city.value = data.localidade;
            uf.value = data.uf;

        })
        .catch(error => console.error('Erro ao carregar os dados do CEP: ', error))
        }

