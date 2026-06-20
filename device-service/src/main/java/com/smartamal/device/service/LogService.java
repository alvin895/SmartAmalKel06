package com.smartamal.device.service;

import com.smartamal.device.model.Log;
import com.smartamal.device.repository.LogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public Log save(Log log) {
        return logRepository.save(log);
    }

    public List<Log> getAll() {
        return logRepository.findAll();
    }

    public Log getById(Long id) {
        return logRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        logRepository.deleteById(id);
    }
}