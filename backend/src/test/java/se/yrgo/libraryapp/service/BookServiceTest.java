package se.yrgo.libraryapp.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import se.yrgo.libraryapp.dao.BookDao;
import se.yrgo.libraryapp.entities.BookEdition;
import se.yrgo.libraryapp.services.BookService;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class BookServiceTest {
    @Mock
    private BookDao bookDao;

    @Test
    void findBookEmptyStrings() {
        String isbn = "";
        String author = "";
        String title = "";

        BookService bookService = new BookService(bookDao);
        Set<BookEdition> maybeBooks = bookService.find(isbn, author, title);
        assertThat(maybeBooks).isEmpty();
    }

    @Test
    void findBookNull() {
        String isbn = null;
        String author = null;
        String title = null;

        BookService bookService = new BookService(bookDao);
        Set<BookEdition> maybeBooks = bookService.find(isbn, author, title);
        assertThat(maybeBooks).isEmpty();
    }

    @Test
    void findBookNullAndEmpty() {
        String isbn = null;
        String author = "";
        String title = null;

        BookService bookService = new BookService(bookDao);
        Set<BookEdition> maybeBooks = bookService.find(isbn, author, title);
        assertThat(maybeBooks).isEmpty();
    }
}
