package ru.roman.courseproject.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.roman.courseproject.models.Book;
import ru.roman.courseproject.models.User;
import ru.roman.courseproject.repository.BooksRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public  Book findOne(int id){
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage){
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        Book bookToBeUpdated = booksRepository.findById(id).get();

        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());

        booksRepository.save(updatedBook);
    }

    public List<Book> searchByTitle(String query){
        return booksRepository.findByTitleStartingWith(query);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public User getBookOwner(int id){
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void release(int id){
        booksRepository.findById(id).ifPresent(
                book -> book.setOwner(null)
        );
    }

    @Transactional
    public void assign(int id, User user){
        booksRepository.findById(id).ifPresent(
                book -> book.setOwner(user)
        );
    }

}
