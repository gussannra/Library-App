package se.yrgo.libraryapp.services;

import se.yrgo.libraryapp.dao.BookDao;
import se.yrgo.libraryapp.entities.BookEdition;
import se.yrgo.libraryapp.entities.DdsClassification;

import javax.inject.Inject;
import java.util.Set;

public class BookService {
    private BookDao bookDao;

    @Inject
    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    // BookController calls this instead of directly BookDao
    public Set<BookEdition> withClass(DdsClassification classification) {
        return bookDao.withClass(classification);
    }

    // BookController calls this instead of directly BookDao
    // Validation moved from BookDao's find method here
    public Set<BookEdition> find(String isbn, String title, String author) {
        if ((isbn == null || isbn.trim().length() == 0)
                && (title == null || title.trim().length() == 0)
                && (author == null || author.trim().length() == 0)) {
            return Set.of();
        }

        return bookDao.find(isbn, title, author);
    }
}
