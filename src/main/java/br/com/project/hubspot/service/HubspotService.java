package br.com.project.hubspot.service;

import br.com.project.hubspot.dto.ContactDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class HubspotService {

    private static final String CREATE_CONTACT_URL = "https://api.hubapi.com/crm/v3/objects/contacts";

    public ResponseEntity<String> createContact(ContactDTO contact, String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> properties = new HashMap<>();
        properties.put("firstname", contact.getFirstName());
        properties.put("lastname", contact.getLastName());
        properties.put("email", contact.getEmail());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("properties", properties);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        return restTemplate.postForEntity(CREATE_CONTACT_URL, entity, String.class);
    }
}