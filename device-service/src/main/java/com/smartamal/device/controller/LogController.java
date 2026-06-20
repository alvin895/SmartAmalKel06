package com.smartamal.device.controller;

import com.smartamal.device.model.Log;
import com.smartamal.device.service.LogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping
    public Log save(@RequestBody Log log) {
        return logService.save(log);
    }

    @GetMapping
    public List<Log> getAll() {
        return logService.getAll();
    }

    @GetMapping("/{id}")
    public Log getById(@PathVariable Long id) {
        return logService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        logService.delete(id);
    }
}