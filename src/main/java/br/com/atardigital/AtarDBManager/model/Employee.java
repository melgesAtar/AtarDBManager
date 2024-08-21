package br.com.atardigital.AtarDBManager.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name ="funcionarios")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funcionario_id")
    Integer id;
    @Column(name = "nome" , length = 60)
    String name;
    @Column(name = "email", length = 60)
    String email;
    @Column(name = "senha", columnDefinition = "TEXT")
    String password;
    @Column(name = "administrador")
    Boolean admin;

    Boolean isAuthenticated;

    public void setAuthenticated(Boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public Boolean getAuthenticated() {
        return isAuthenticated;
    }

    @OneToMany
    @JoinTable(
            name = "funcionarios_clientes",
            joinColumns = @JoinColumn(name = "funcionario_id"),
            inverseJoinColumns = @JoinColumn(name = "cliente_id")
    )
    private List<Client> clients;

    public Boolean getAdmin() {
        return admin;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Integer getID() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
