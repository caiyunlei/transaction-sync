package com.cyl.transaction.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Commodity {
    @Id
    @Column(name="ID",unique = true, nullable = false, insertable = true, updatable = true, length = 11)
    private int id;

    @Column
    private String name;

    @Column()
    private int stock;

    public Commodity(int id) {
        this.id = id;
    }

    public Commodity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
