package br.com.atardigital.AtarDBManager.controller;

import br.com.atardigital.AtarDBManager.DAO.IEmployee;
import br.com.atardigital.AtarDBManager.model.Employee;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@Controller
@RequestMapping("/AuthController")
public class AuthController {

    @Autowired
    private IEmployee dao;

    @PostMapping("/auth")
    public String authenticUser(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {

        Optional<Employee> employeeOpt = dao.findByEmail(email);

        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            if (employee.getPassword().equals(password)) {
                session.setAttribute("username", employee.getEmail());
                session.setAttribute("name", employee.getName());
                session.setAttribute("isAuthenticated", true);
                session.setAttribute("userId", employee.getID());
                session.setAttribute("isAdmin", employee.getAdmin());
                return "redirect:/tables";
            }
        }

        return "redirect:/login?error=true";
    }



    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
