package br.com.atardigital.AtarDBManager.controller;

import br.com.atardigital.AtarDBManager.DAO.IClient;
import br.com.atardigital.AtarDBManager.DAO.IEmployee;
import br.com.atardigital.AtarDBManager.DAO.IFacebookAccounts;
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

    @Autowired
    private IClient clientRepository;



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
    public ResponseEntity<FacebookAccounts> editFacebookAccount(@PathVariable Integer id, @RequestBody FacebookAccounts updateFacebookAccount){
        return IFacebookAccounts.findById(id).map(FacebookAccount ->{


                    FacebookAccount.setUsername(updateFacebookAccount.getUsername());
                    FacebookAccount.setPassword(updateFacebookAccount.getPassword());
                    Integer clientId = updateFacebookAccount.getClient().getId();

                    Client client = clientRepository.findById(clientId)
                            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + clientId));
                    updateFacebookAccount.setClient(client);

                    FacebookAccount.setActive(updateFacebookAccount.isActive());
                    FacebookAccount.setAddress(updateFacebookAccount.getAddress());
                    FacebookAccount.setCity(updateFacebookAccount.getCity());
                    FacebookAccount.setNeighborhood(updateFacebookAccount.getNeighborhood());
                    FacebookAccount.setUf(updateFacebookAccount.getUf());

                    FacebookAccounts updatedFacebookAccount = IFacebookAccounts.save(FacebookAccount);
                     return ResponseEntity.ok(updatedFacebookAccount);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada, com o ID : " + id ));
    }

    @PostMapping("/add")
    public FacebookAccounts addFacebookAccount(@RequestBody FacebookAccounts facebookAccountUpdate){
        Client client = clientRepository.findById(facebookAccountUpdate.getClient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with ID: " + facebookAccountUpdate.getClient().getId()));
        facebookAccountUpdate.setClient(client);


        return IFacebookAccounts.save(facebookAccountUpdate);
    }

}
