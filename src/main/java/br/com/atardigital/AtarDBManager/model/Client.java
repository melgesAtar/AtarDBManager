package br.com.atardigital.AtarDBManager.model;

import jakarta.persistence.*;



@Entity
@Table(name ="clientes")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Integer id;
    @Column(name = "nome" , length = 60)
    private String name;

    @Column(name = "cidade")
    private String city;
    @Column(name = "usuario_facebook")
    private String nameUserFacebook;
    @Column(name = "instagram_User")
    private String nameUserInstagram;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNameUserFacebook() {
        return nameUserFacebook;
    }

    public void setNameUserFacebook(String nameUserFacebook) {
        this.nameUserFacebook = nameUserFacebook;
    }
}
