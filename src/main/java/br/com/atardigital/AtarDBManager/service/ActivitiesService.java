package br.com.atardigital.AtarDBManager.service;

import br.com.atardigital.AtarDBManager.DAO.IActivities;
import br.com.atardigital.AtarDBManager.model.Activities;
import br.com.atardigital.AtarDBManager.model.dto.ActivitiesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ActivitiesService {

    @Autowired
    private IActivities activitiesRepository;

    public List<ActivitiesDTO> getActivitiesWithClientNames(Integer employeeId) {
        List<Activities> activities = activitiesRepository.findByEmployeeID(employeeId);

        return activities.stream().map(activity -> {
            ActivitiesDTO dto = new ActivitiesDTO();
            dto.setId(activity.getId());
            dto.setNameActivity(activity.getNameActivity());
            dto.setDate(activity.getDate());
            dto.setQuantity(activity.getQuantity());


            // Verifique se o cliente não é nulo antes de acessar seu nome
            if (activity.getClient() != null) {
                dto.setClientID(activity.getClient().getId());
                dto.setClientName(activity.getClient().getName());
            } else {
                dto.setClientName("Cliente não atribuído");
            }

            return dto;
        }).collect(Collectors.toList());
    }

}
