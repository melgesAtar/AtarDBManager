package br.com.atardigital.AtarDBManager.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@RequestMapping("/session")
public class SessionController {

    @GetMapping("/session-info")
    public Map<String, Object> showSessionAttributes(HttpSession session) {
        Map<String, Object> sessionData = new HashMap<>();
        Enumeration<String> attributeNames = session.getAttributeNames();

        while (attributeNames.hasMoreElements()){
            String attributeName = attributeNames.nextElement();
            Object attributeValue = session.getAttribute(attributeName);
            sessionData.put(attributeName, attributeValue);
        }
        return sessionData;
    }



    @GetMapping("/debug-session")
    public void debugSessionAttributes(HttpSession session) {
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            Object attributeValue = session.getAttribute(attributeName);
            System.out.println(attributeName + ": " + attributeValue);
        }
    }
}

