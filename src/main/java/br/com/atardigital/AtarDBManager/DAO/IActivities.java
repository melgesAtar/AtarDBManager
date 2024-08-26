package br.com.atardigital.AtarDBManager.DAO;

import br.com.atardigital.AtarDBManager.model.Activities;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface IActivities extends CrudRepository<Activities, Integer>{

    List<Activities> findByEmployeeID(Integer employeeID);


}
