package com.makarewk.angulartutorial.initializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makarewk.angulartutorial.webconfig.entities.User;
import com.makarewk.angulartutorial.webconfig.repositories.UserRepository;
import com.makarewk.angulartutorial.webservice.entities.Category;
import com.makarewk.angulartutorial.webservice.entities.Equipment;
import com.makarewk.angulartutorial.webservice.enums.CategoryEnum;
import com.makarewk.angulartutorial.webservice.mappers.EquipmentMapper;
import com.makarewk.angulartutorial.webservice.repositories.CategoryRepository;
import com.makarewk.angulartutorial.webservice.repositories.EquipmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Stream;

@Slf4j
@Component
public class DbInitializer {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DbInitializer(EquipmentRepository equipmentRepository, CategoryRepository categoryRepository, ResourceReader resourceReader, UserRepository userRepository) {
        initializeEquipmentList(equipmentRepository, resourceReader);
        initializeCategoryList(categoryRepository);
        initializeUsers(userRepository);

    }

    private void initializeEquipmentList(EquipmentRepository equipmentRepository, ResourceReader resourceReader) {
        Resource productsResourcee = resourceReader.getProduct();

        try {
            EquipmentMapper equipmentMapper = objectMapper.readValue(productsResourcee.getInputStream(), EquipmentMapper.class);

            equipmentMapper.getProductsList().forEach(
                    product -> {
                        Equipment equipment = null;
                        try {
                            CategoryEnum categoryName = convert(product.get("category").textValue());
                            if(categoryName != null) {
                                equipment = new Equipment(product.get("name").textValue(), categoryName,
                                        objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(product.get("details")));
                            }
                            else{
                                log.info("Pominięto produkt. Kategoria nie została zdefiniowana");
                            }
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        equipmentRepository.save(equipment);
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeCategoryList(CategoryRepository categoryRepository) {
        Stream.of("Fridge", "Headphones", "Tv", "Tuner").forEach(
                cat -> {
                    Category category = new Category();
                    category.setCategoryName(cat);
                    categoryRepository.save(category);
                }
        );
    }

    private void initializeUsers(UserRepository userRepository) {
        userRepository.save(
                User.builder()
                        .username("makarewk")
                        .password("makarewk")
                        .role(User.Role.ROLE_ADMIN)
                        .build());
    }

    private static CategoryEnum convert(String categoryName) {
        switch (categoryName) {
            case ("fridge"):
                return CategoryEnum.FRIDGE;
            case ("phone"):
                return CategoryEnum.PHONE;
            case ("headphones"):
                return CategoryEnum.HEADPHONES;
            default:
                log.info("Not match to any category");
                return null;
        }
    }
}
