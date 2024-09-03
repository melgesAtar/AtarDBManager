package br.com.atardigital.AtarDBManager.controller;

import br.com.atardigital.AtarDBManager.DAO.IEmployee;
import br.com.atardigital.AtarDBManager.DAO.IFacebookAccounts;
import br.com.atardigital.AtarDBManager.model.Activities;
import br.com.atardigital.AtarDBManager.model.Client;
import br.com.atardigital.AtarDBManager.model.Employee;
import br.com.atardigital.AtarDBManager.model.FacebookAccounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("edit/{id}")
    public FacebookAccounts editFacebookAccount(@PathVariable Integer id, @RequestBody FacebookAccounts updateFacebookAccount){
        return IFacebookAccounts.findById(id).map(FacebookAccount ->{

                    FacebookAccount.setId(updateFacebookAccount.getId());
                    FacebookAccount.setUsername(updateFacebookAccount.getUsername());
                    FacebookAccount.setPassword(updateFacebookAccount.getPassword());
                    FacebookAccount.setClient(updateFacebookAccount.getClient());
                    FacebookAccount.setActive(updateFacebookAccount.isActive());
                    FacebookAccount.setAddress(updateFacebookAccount.getAddress());
                    FacebookAccount.setAddress(updateFacebookAccount.getCity());
                    FacebookAccount.setNeighborhood(updateFacebookAccount.getNeighborhood());
                    FacebookAccount.setUf(updateFacebookAccount.getUf());

                    return IFacebookAccounts.save(FacebookAccount);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada, com o ID : " + id ));
    }


}
