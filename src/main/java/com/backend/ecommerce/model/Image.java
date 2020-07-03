package com.backend.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private String b64Image;

    private Status status;

    public Image() {}

    public Image(String b64Image, Status status) {
        this.b64Image = b64Image;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getB64Image() {
        return b64Image;
    }

    public void setB64Image(String b64Image) {
        this.b64Image = b64Image;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}