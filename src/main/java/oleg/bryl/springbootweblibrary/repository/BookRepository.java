package oleg.bryl.springbootweblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import oleg.bryl.springbootweblibrary.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
     /**
      *
      * @param available
      * @return
      */
     List<Book> findAllByAvailable(Boolean available);
}
