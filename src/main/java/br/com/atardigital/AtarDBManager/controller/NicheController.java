package br.com.atardigital.AtarDBManager.controller;


import br.com.atardigital.AtarDBManager.DAO.INiche;
import br.com.atardigital.AtarDBManager.model.Niche;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;
@RestController
@RequestMapping("/api/nicheController")
public class NicheController {
    @Autowired
    INiche INiche;

    @GetMapping("/niches")
    public Iterable<Niche> getAllNiches(){
        return  INiche.findAll();
    }
}
