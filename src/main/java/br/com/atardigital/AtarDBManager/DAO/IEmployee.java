package br.com.atardigital.AtarDBManager.DAO;

import br.com.atardigital.AtarDBManager.model.Client;
import br.com.atardigital.AtarDBManager.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IEmployee extends CrudRepository<Employee, Integer>{
    Optional<Employee> findByEmail(String email);

    @Query("SELECT e.clients FROM Employee e WHERE e.id = :employeeId")
    List<Client> findClientsByEmployeeId(@Param("employeeId") Integer employeeId);
}
