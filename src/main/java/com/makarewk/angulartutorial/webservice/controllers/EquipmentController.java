package com.makarewk.angulartutorial.webservice.controllers;

import com.makarewk.angulartutorial.webservice.enums.StatusEnum;
import com.makarewk.angulartutorial.webservice.repositories.*;
import com.makarewk.angulartutorial.webservice.entities.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path="/equipment")
@CrossOrigin(origins = "http://localhost:4200")
public class EquipmentController {

    private final EquipmentRepository equipmentRepository;

    public EquipmentController(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @GetMapping
    public List<Equipment> getEquipment() {
        return (List<Equipment>)equipmentRepository.findAll();
    }

    @GetMapping("/equipmentDetails/{id}")
    public Optional<Equipment> getEquipmentDetails(@PathVariable("id") String id){
        return equipmentRepository.findById(Long.valueOf(id));
    }

    @PostMapping("/equipmentDetails/{id}")
    public void reportSomeDamage(@RequestBody Equipment equipment, @PathVariable String id) {
        equipment.setStatusEnum(StatusEnum.AWARIA);
        equipmentRepository.save(equipment);
    }

}
