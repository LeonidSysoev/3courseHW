package ru.hogwarts.school.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.services.InfoService;

@RestController
@RequestMapping
public class InfoController {
    private final InfoService service;

    public InfoController(InfoService service) {
        this.service = service;
    }
    @GetMapping("/getPort")
    public int getPort() {
        return service.getPort();
    }
}
