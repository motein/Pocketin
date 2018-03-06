package com.micro.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.micro.domain.Book;
import com.micro.domain.Books;

import java.util.*;
import java.util.Map.Entry;

@Path("books")
public class BookResource {
	private static final Log logger = LogFactory.getLog(BookResource.class);
	private static final HashMap<Long, Book> memoryBase;
	
    static {
        memoryBase = new HashMap<Long, Book>();
        memoryBase.put(1L, new Book(1L, "JSF2和RichFaces4使用指南"));
        memoryBase.put(2L, new Book(2L, "Java Restful Web Services实战"));
        memoryBase.put(3L, new Book(3L, "Java EE 7 精髓"));
        memoryBase.put(4L, new Book(4L, "Java Restful Web Services实战II"));
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("h")
    public Books getBooks() {
        final List<Book> bookList = new ArrayList<>();
        final Set<Map.Entry<Long, Book>> entries = BookResource.memoryBase.entrySet();
        final Iterator<Entry<Long, Book>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            final Entry<Long, Book> cursor = iterator.next();
            BookResource.logger.debug(cursor.getKey());
            bookList.add(cursor.getValue());
        }
        final Books books = new Books(bookList);
        BookResource.logger.debug(books);
        return books;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("y")
    public Book saveBook(final Book book) {
        book.setBookId(System.nanoTime());
        BookResource.memoryBase.put(book.getBookId(), book);
        return book;
    }
}
