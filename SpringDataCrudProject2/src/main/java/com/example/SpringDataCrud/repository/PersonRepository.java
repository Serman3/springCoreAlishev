package com.example.SpringDataCrud.repository;

import com.example.SpringDataCrud.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query(value = """
            SELECT p.*
            FROM people p
            JOIN books b on b.person_id = p.id
            WHERE b.id =:bookId
            """, nativeQuery = true)
    List<Person> getAllPersonByBookId(@Param(value = "bookId") int bookId);
}
