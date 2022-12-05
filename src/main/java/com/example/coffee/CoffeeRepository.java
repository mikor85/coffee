package com.example.coffee;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoffeeRepository extends CrudRepository<Coffee, String> {
    // Coffee - класс, который будет через Repository сохраняться в таблице базы данных
    // String - тип ключа этого класса

    //@Query("SELECT * FROM Coffee WHERE name LIKE %:name%")
    List<Coffee> findByNameLike(/*@Param("name")*/ String name);
    //List<Coffee> findByNameContaining(/*@Param("name")*/ String name);
}