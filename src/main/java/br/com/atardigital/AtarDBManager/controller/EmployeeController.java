package br.com.atardigital.AtarDBManager.controller;


import br.com.atardigital.AtarDBManager.DAO.IEmployee;

import br.com.atardigital.AtarDBManager.model.Client;

import br.com.atardigital.AtarDBManager.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class EmployeeController {

    @Autowired
    private IEmployee employeeRepository;

    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/current")
    public ResponseEntity<Map<String, Object>> getCurrentUser(HttpSession session){
        Boolean isAuthenticated = (Boolean) session.getAttribute("isAuthenticated");

        if(isAuthenticated != null && isAuthenticated){
            Map<String, Object> response = new HashMap<>();
            response.put("username", session.getAttribute("username"));
            response.put("name", session.getAttribute("name"));
            response.put("isAuthenticated", session.getAttribute("isAuthenticated"));
            response.put("userId", session.getAttribute("userId"));
            response.put("isAdmin", session.getAttribute("isAdmin"));

            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    @GetMapping("/clients/{employeeId}")
    public List<Client> getClients(@PathVariable Integer employeeId){
        return employeeService.getClientsByEmployeeId(employeeId);
    }
}
