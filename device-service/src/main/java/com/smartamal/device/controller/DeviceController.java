package com.smartamal.device.controller;

import com.smartamal.device.model.Device;
import com.smartamal.device.service.DeviceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public Device save(@RequestBody Device device) {
        return deviceService.save(device);
    }

    @GetMapping
    public List<Device> getAll() {
        return deviceService.getAll();
    }

    @GetMapping("/{id}")
    public Device getById(@PathVariable Long id) {
        return deviceService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        deviceService.delete(id);
    }
}