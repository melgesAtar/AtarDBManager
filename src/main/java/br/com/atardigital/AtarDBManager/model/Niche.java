package br.com.atardigital.AtarDBManager.model;

import jakarta.persistence.*;

@Entity
@Table(name="nichos")
public class Niche {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idnichos")
    private Integer id;

    @Column(name = "nome")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
