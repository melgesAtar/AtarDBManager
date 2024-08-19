package br.com.atardigital.AtarDBManager.controller;

import br.com.atardigital.AtarDBManager.DAO.IEmployee;
import br.com.atardigital.AtarDBManager.model.Employee;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private IEmployee dao;

    @GetMapping
    public List<Employee> employeesList() {
        return (List<Employee>) dao.findAll();
    }

    @PostMapping("/auth")
    public boolean authenticUser(@RequestBody Employee loginRequest, HttpSession session) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();


        Optional<Employee> employeeOpt = dao.findByEmail(username);

        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            if (employee.getPassword().equals(password)) {
                employee.setAuthenticated(true);

                session.setAttribute("username", username);
                session.setAttribute("isAuthenticated", employee.getAuthenticated());
                session.setAttribute("userId", employee.getID());
                session.setAttribute("isAdmin", employee.getAdmin());
                session.setAttribute("clients", employee.getClients());

                return true;
            }
        }

        return false;
    }

    @GetMapping("/user-info")
    public String getUserInfo(HttpSession session) {
        String username = (String) session.getAttribute("username");
        Long userId = (Long) session.getAttribute("userId");

        if (username != null && userId != null) {
            return "Username: " + username + ", User ID: " + userId;
        } else {
            return "No user is currently logged in.";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "You have been logged out.";
    }

}
