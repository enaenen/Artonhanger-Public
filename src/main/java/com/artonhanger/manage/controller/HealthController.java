package com.artonhanger.manage.controller;

import com.artonhanger.manage.model.dto.ArtworkUploadDto;
import com.artonhanger.manage.model.dto.RegisterUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/healthz")
public class HealthController {

    @ResponseBody
    @GetMapping(value = "/startup")
    public ResponseEntity<?> startup() {
        return ResponseEntity.ok("ok");
    }

    @ResponseBody
    @GetMapping(value = "/readiness")
    public ResponseEntity<?> readiness() {
        return ResponseEntity.ok("ok");
    }

    @ResponseBody
    @GetMapping(value = "/liveness")
    public ResponseEntity<?> liveness() {
        return ResponseEntity.ok("ok");
    }
}
