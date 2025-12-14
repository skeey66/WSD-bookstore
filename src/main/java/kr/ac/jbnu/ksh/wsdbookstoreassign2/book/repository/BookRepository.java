package kr.ac.jbnu.ksh.wsdbookstoreassign2.book.repository;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    boolean existsByIsbn(String isbn);
}
