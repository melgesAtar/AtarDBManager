package br.com.atardigital.AtarDBManager.controller;


import br.com.atardigital.AtarDBManager.DAO.IActivities;
import br.com.atardigital.AtarDBManager.model.Activities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivitiesController {

    @Autowired
    private IActivities activitiesRepository;

    @GetMapping("/all")
    public List<Activities> getAll(){
        return (List<Activities>) activitiesRepository.findAll();
    }

    @PostMapping("/add")
    public Activities addActivity(@RequestBody Activities activity){
        return activitiesRepository.save(activity);
    }

    @PutMapping("edit/{id}")
    public Activities editActivity(@PathVariable Integer id, @RequestBody Activities updateActivity){
        return activitiesRepository.findById(id).map(activity ->{
            activity.setEmployeeID(updateActivity.getEmployeeID());
            activity.setClientID(updateActivity.getClientID());
            activity.setNameActivity(updateActivity.getNameActivity());
            activity.setQuantity(updateActivity.getQuantity());
            activity.setDate(updateActivity.getDate());

            return activitiesRepository.save(activity);
        })
                .orElseThrow(() -> new ResourceNotFoundException("Atividade não encontrada, com o ID : " + id ));
    }

    @DeleteMapping("delete/{id}")
    public void deleteActivity(@PathVariable Integer id){
        activitiesRepository.deleteById(id);
    }
}
