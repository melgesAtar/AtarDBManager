package br.com.atardigital.AtarDBManager.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name ="clientes")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    Integer id;
    @Column(name = "nome" , length = 60)
    String name;
    @Column(name = "email", length = 60)
    String email;
    @Column(name = "senha", columnDefinition = "TEXT")
    String password;
    @Column(name = "cidade")
    String city;
    @Column(name = "usuario_facebook")
    String nameUserFacebook;
    @Column(name = "instagram_User")
    String nameUserInstagram;


}
