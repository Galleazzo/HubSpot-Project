package br.com.project.hubspot.controller;

import br.com.project.hubspot.dto.ContactDTO;
import br.com.project.hubspot.service.HubspotService;
import br.com.project.hubspot.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hubspot")
public class HubspotController {

    @Autowired
    private HubspotService hubspotService;

    @PostMapping("/contacts")
    public ResponseEntity<String> createContact(@RequestBody ContactDTO contact, @RequestHeader("Authorization") String accessToken) {
        return hubspotService.createContact(contact, StringUtils.extractToken(accessToken));
    }

}
