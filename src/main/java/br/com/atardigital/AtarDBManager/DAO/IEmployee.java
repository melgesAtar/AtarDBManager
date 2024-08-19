package br.com.atardigital.AtarDBManager.DAO;

import br.com.atardigital.AtarDBManager.model.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IEmployee extends CrudRepository<Employee, Integer>{
    Optional<Employee> findByEmail(String email);
}
