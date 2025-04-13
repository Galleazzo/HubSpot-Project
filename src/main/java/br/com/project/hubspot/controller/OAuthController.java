package br.com.project.hubspot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    @Value("${hubspot.client-id}")
    private String clientId;

    @Value("${hubspot.redirect-uri}")
    private String redirectUri;

    @Value("${hubspot.scopes}")
    private String scopes;

    @Value("${hubspot.client-secret}")
    private String clientSecret;

    @GetMapping("/authorize")
    public String generateAuthorizationUrl() {
        String url = "https://app.hubspot.com/oauth/authorize" +
                "?client_id=" + clientId +
                "&scope=" + scopes +
                "&redirect_uri=" + redirectUri;
        return url;
    }

    @PostMapping("/callback")
    public ResponseEntity<String> handleCallback(@RequestParam("code") String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("redirect_uri", redirectUri);
        formData.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://api.hubapi.com/oauth/v1/token",
                    request,
                    String.class
            );

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao trocar o code: " + e.getMessage());
        }
    }

}
