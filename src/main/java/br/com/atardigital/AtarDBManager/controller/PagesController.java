package br.com.atardigital.AtarDBManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/tables")
    public String showTablesPage() {
        return "tables";
    }

    @GetMapping("edit-table/activities")
    public String showTablesActivityPage() {
        return "editTableActivities";
    }

    @GetMapping("edit-table/facebook_accounts")
    public String showTableFacebookAccounts(){
        return "editTableFacebookAccounts";
    }
}