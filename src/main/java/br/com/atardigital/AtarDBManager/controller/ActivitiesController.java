package br.com.atardigital.AtarDBManager.controller;


import br.com.atardigital.AtarDBManager.DAO.IActivities;
import br.com.atardigital.AtarDBManager.DAO.IClient;
import br.com.atardigital.AtarDBManager.model.Activities;
import br.com.atardigital.AtarDBManager.model.Client;
import br.com.atardigital.AtarDBManager.model.dto.ActivitiesDTO;
import br.com.atardigital.AtarDBManager.service.ActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activities")
public class ActivitiesController {

    @Autowired
    private IActivities activitiesRepository;
    @Autowired
    private ActivitiesService activitiesService;
    @Autowired
    private IClient clientRepository;


    @GetMapping("/all")
    public List<Activities> getAll(){
        return (List<Activities>) activitiesRepository.findAll();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ActivitiesDTO> getActivities(@PathVariable Integer employeeId){

        List<ActivitiesDTO> activitiesDTOs = activitiesService.getActivitiesWithClientNames(employeeId);

        for (ActivitiesDTO dto : activitiesDTOs) {
            if (dto.getClientName() == null) {
                dto.setClientName("Cliente desconhecido");
            }
        }

        return  activitiesService.getActivitiesWithClientNames(employeeId);
    }

    @PostMapping("/add")
    public Activities addActivity(@RequestBody Activities activity){
        Client client = clientRepository.findById(activity.getClient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with ID: " + activity.getClient().getId()));
        activity.setClient(client);
        return activitiesRepository.save(activity);
    }

    @PutMapping("edit/{id}")
    public Activities editActivity(@PathVariable Integer id, @RequestBody Activities updateActivity){
        return activitiesRepository.findById(id).map(activity ->{
            activity.setEmployeeID(updateActivity.getEmployeeID());

            Client client = clientRepository.findById(updateActivity.getClient().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Client not found with ID: " + updateActivity.getClient().getId()));
            activity.setClient(client);
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
