package com.tolstolutskyi.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue
    private Long id;
}
