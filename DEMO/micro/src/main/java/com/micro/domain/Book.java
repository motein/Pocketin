package com.micro.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Book {
	private Long bookId;
	String bookName;
	
	public Book() {
		
	}
	
	public Book(Long bookId, String bookName) {
		this.bookId = bookId;
		this.bookName = bookName;
	}
	
    @XmlAttribute(name = "bookId")
    public Long getBookId() {
        return bookId;
    }
    
    public void setBookId(final Long bookId) {
        this.bookId = bookId;
    }
    
    @XmlAttribute(name = "bookName")
    public String getBookName() {
        return bookName;
    }

    public void setBookName(final String bookName) {
        this.bookName = bookName;
    }
    
    @Override
    public String toString() {
    	return String.format("%s[bookId=%d]",
                bookName, bookId);
    }
}
