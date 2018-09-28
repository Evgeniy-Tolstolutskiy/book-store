package com.tolstolutskyi.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private Date date;
}
