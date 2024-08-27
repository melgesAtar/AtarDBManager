package br.com.atardigital.AtarDBManager.DAO;

import br.com.atardigital.AtarDBManager.model.Client;
import br.com.atardigital.AtarDBManager.model.FacebookAccounts;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IFacebookAccounts extends CrudRepository<FacebookAccounts, Integer> {

    List<FacebookAccounts> findByClientIn(List<Client> clients);

}
