package com.example.SpringDataCrud.repository;

import com.example.SpringDataCrud.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query(value = """
            SELECT * FROM books WHERE person_id =:personId
            """, nativeQuery = true)
    List<Book> findAllByPeopleId(@Param(value = "personId") int personId);

    @Query(value = """
            SELECT * FROM books WHERE name LIKE %:searchResponse%
            """, nativeQuery = true)
    Optional<List<Book>> searchBooks(@Param("searchResponse") String searchResponse);
}

   // List<Movie> findByTitleContaining(String title);
  //  List<Movie> findByTitleContains(String title);
  //  List<Movie> findByTitleIsContaining(String title);