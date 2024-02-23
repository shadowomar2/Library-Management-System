package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String contactInformation;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContactInformation() {
		return contactInformation;
	}
	public void setContactInformation(String contactInformation) {
		this.contactInformation = contactInformation;
	}
	public Patron(Long id, String name, String contactInformation) {
		super();
		this.id = id;
		this.name = name;
		this.contactInformation = contactInformation;
	}
	public Patron() {
		super();
	}
	@Override
	public String toString() {
		return "Patron [id=" + id + ", name=" + name + ", contactInformation=" + contactInformation + "]";
	}
 
}
