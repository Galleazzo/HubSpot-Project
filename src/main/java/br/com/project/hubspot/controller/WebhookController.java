package br.com.project.hubspot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @PostMapping("/contact")
    public ResponseEntity<String> handleContactWebhook(@RequestBody List<Map<String, Object>> payload) {
        System.out.println("Webhook recebido (lista de eventos):");
        for (Map<String, Object> event : payload) {
            System.out.println("Evento individual: " + event);
        }
        return ResponseEntity.ok("Eventos processados com sucesso");
    }
}
