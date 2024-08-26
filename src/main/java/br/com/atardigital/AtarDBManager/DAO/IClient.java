package br.com.atardigital.AtarDBManager.DAO;

import br.com.atardigital.AtarDBManager.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClient extends CrudRepository<Client, Integer> {
}
