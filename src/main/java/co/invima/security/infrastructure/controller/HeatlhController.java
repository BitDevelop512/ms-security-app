package co.invima.security.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HeatlhController {

    @GetMapping("/ready")
    public ResponseEntity<String> getReady() {

        return ResponseEntity.ok("Ready");
    }

    @GetMapping()
    public ResponseEntity<String> getHealth() {

        return ResponseEntity.ok("UP");
    }
}
