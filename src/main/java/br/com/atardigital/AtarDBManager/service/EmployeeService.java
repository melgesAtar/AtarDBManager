package br.com.atardigital.AtarDBManager.service;

import br.com.atardigital.AtarDBManager.DAO.IEmployee;
import br.com.atardigital.AtarDBManager.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private IEmployee employeeRepository;

    public List<Client> getClientsByEmployeeId(Integer employeeId){
        return employeeRepository.findClientsByEmployeeId(employeeId);
    }
}
