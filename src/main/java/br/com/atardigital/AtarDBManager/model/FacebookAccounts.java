package br.com.atardigital.AtarDBManager.model;

import jakarta.persistence.*;

import java.util.Optional;


@Entity
@Table(name="contas_facebook")
public class FacebookAccounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facebook_id")
    private Integer id;

    @Column(name = "username_facebook")
    private String username;

    @Column(name = "senha_facebook")
    private String password;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id")
    private Client client;

    @Column(name = "status_conta")
    private boolean isActive;

    @Column(name="cidade")
    private String city;

    @Column(name = "bairro")
    private String neighborhood;


    @ManyToOne
    @JoinColumn(name = "nicho",  referencedColumnName = "idnichos")
    private Niche niche;

    @Column(name = "endereco")
    private String address;
    @Column(name = "uf")
    private String uf;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Niche getNiche() {
        return niche;
    }

    public void setNiche(Niche niche){
        this.niche = niche;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

}
