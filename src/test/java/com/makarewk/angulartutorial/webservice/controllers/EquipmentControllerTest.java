package com.makarewk.angulartutorial.webservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makarewk.angulartutorial.webservice.entities.Equipment;
import com.makarewk.angulartutorial.webservice.enums.CategoryEnum;
import com.makarewk.angulartutorial.webservice.enums.StatusEnum;
import com.makarewk.angulartutorial.webservice.repositories.EquipmentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class EquipmentControllerTest {
    @Mock
    private EquipmentRepository equipmentRepository;

    private MockMvc mockMvc;

    @Before
    public void init() {
        EquipmentController equipmentController = new EquipmentController(equipmentRepository);

        mockMvc = MockMvcBuilders.standaloneSetup(equipmentController).build();
    }

    @Test
    public void getEquipmentTest() throws Exception {
        List<Equipment> equipmentList = getEquipmentList();
        when(equipmentRepository.findAll()).thenReturn(equipmentList);

        mockMvc.perform(get("/equipment")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(Long.valueOf("1")))
                .andExpect(jsonPath("$[0].name", is("Beko")))
                .andExpect(jsonPath("$[0].categoryName", is("FRIDGE")))
                .andExpect(jsonPath("$[0].details", is("good fridge")))
                .andExpect(jsonPath("$[0].statusEnum", is("SPRAWNY")))
                .andExpect(jsonPath("$[0]comment", is("Sprzęt jest sprawny")))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void getEquipmentDetailsTest() throws Exception {
        Equipment equipment = getEquipmentDetails();

        when(equipmentRepository.findById(1L)).thenReturn(java.util.Optional.of(equipment));

        mockMvc.perform(get("/equipment/equipmentDetails/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(Long.valueOf("1")))
                .andExpect(jsonPath("$.name", is("Beko")))
                .andExpect(jsonPath("$.categoryName", is("FRIDGE")))
                .andExpect(jsonPath("$.details", is("good fridge")))
                .andExpect(jsonPath("$.statusEnum", is("SPRAWNY")))
                .andExpect(jsonPath("$.comment", is("Sprzęt jest sprawny")))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void reportSomeDamageTest() throws Exception {
        Equipment equipment = getEquipmentDetails();

        mockMvc.perform(post("/equipment/equipmentDetails/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(setEquipmentDetailsAsString(equipment)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    private List<Equipment> getEquipmentList() {
        return Collections.singletonList(
                new Equipment(1L, "Beko", CategoryEnum.FRIDGE, "good fridge", StatusEnum.SPRAWNY, "Sprzęt jest sprawny"));
    }

    private Equipment getEquipmentDetails() {
        return new Equipment(1L, "Beko", CategoryEnum.FRIDGE, "good fridge", StatusEnum.SPRAWNY, "Sprzęt jest sprawny");
    }

    private String setEquipmentDetailsAsString(Equipment equipment) {
        try {
            return new ObjectMapper().writeValueAsString(equipment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
