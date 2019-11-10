package com.makarewk.angulartutorial.webservice.repositories;

import com.makarewk.angulartutorial.webservice.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
