package com.makarewk.angulartutorial.webconfig.repositories;

import com.makarewk.angulartutorial.webconfig.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String userLogin);
}