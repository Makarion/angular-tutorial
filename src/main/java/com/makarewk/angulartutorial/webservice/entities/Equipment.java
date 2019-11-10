package com.makarewk.angulartutorial.webservice.entities;

import com.makarewk.angulartutorial.webservice.enums.CategoryEnum;
import com.makarewk.angulartutorial.webservice.enums.StatusEnum;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private CategoryEnum categoryName;
    private String details;
    private StatusEnum statusEnum = StatusEnum.SPRAWNY;
    private String comment;

    public Equipment(String name, CategoryEnum categoryName, String details) {
        this.name = name;
        this.categoryName = categoryName;
        this.details = details;
    }
}
