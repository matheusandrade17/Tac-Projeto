package com.gestao.academico.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
public class VersionController {

    @GetMapping("/api/v1/version")
    public Map<String, String> version() {

        Map<String, String> info = new HashMap<>();

        info.put("version", "1.0.0");
        info.put("environment", "development");
        info.put("buildDate", LocalDate.now().toString());

        return info;
    }
}