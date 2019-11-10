package com.makarewk.angulartutorial.webservice.mappers;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@ToString
public class EquipmentMapper {
    private String name;
    private String category;
    private JsonNode productsList;
}
