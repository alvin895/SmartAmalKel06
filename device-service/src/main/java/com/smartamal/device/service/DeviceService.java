package com.smartamal.device.service;

import com.smartamal.device.model.Device;
import com.smartamal.device.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device save(Device device) {
        return deviceRepository.save(device);
    }

    public List<Device> getAll() {
        return deviceRepository.findAll();
    }

    public Device getById(Long id) {
        return deviceRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        deviceRepository.deleteById(id);
    }
}