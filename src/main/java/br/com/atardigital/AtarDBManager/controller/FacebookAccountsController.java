package br.com.atardigital.AtarDBManager.controller;

import br.com.atardigital.AtarDBManager.DAO.IEmployee;
import br.com.atardigital.AtarDBManager.DAO.IFacebookAccounts;
import br.com.atardigital.AtarDBManager.model.Client;
import br.com.atardigital.AtarDBManager.model.Employee;
import br.com.atardigital.AtarDBManager.model.FacebookAccounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/facebookAccounts")
public class FacebookAccountsController {

    @Autowired
    private IFacebookAccounts IFacebookAccounts;

    @Autowired
    private IEmployee IEmployee;

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<FacebookAccounts>> getFacebookAccountsByEmployee(@PathVariable Integer employeeId){

        Optional<Employee> employeeOpt = IEmployee.findById(employeeId);

        if(employeeOpt.isPresent()){
            Employee employee = employeeOpt.get();
            List<Client> clients = employee.getClients();

            List<FacebookAccounts> facebookAccounts = IFacebookAccounts.findByClientIn(clients);

            return ResponseEntity.ok(facebookAccounts);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
