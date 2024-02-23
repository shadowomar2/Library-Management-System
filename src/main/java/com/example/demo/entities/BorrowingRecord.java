package com.example.demo.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Book book;
    @ManyToOne
    private Patron patron;
    private LocalDate borrowDate;
    private LocalDate returnDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Patron getPatron() {
		return patron;
	}
	public void setPatron(Patron patron) {
		this.patron = patron;
	}
	public LocalDate getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(LocalDate borrowDate) {
		this.borrowDate = borrowDate;
	}
	public LocalDate getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
	@Override
	public String toString() {
		return "BorrowingRecord [id=" + id + ", book=" + book + ", patron=" + patron + ", borrowDate=" + borrowDate
				+ ", returnDate=" + returnDate + "]";
	}
	public BorrowingRecord(Long id, Book book, Patron patron, LocalDate borrowDate, LocalDate returnDate) {
		super();
		this.id = id;
		this.book = book;
		this.patron = patron;
		this.borrowDate = borrowDate;
		this.returnDate = returnDate;
	}
	public BorrowingRecord() {
		super();
	}
 
}