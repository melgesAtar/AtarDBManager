package br.com.atardigital.AtarDBManager.model;

import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "atividades")
public class Activities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atividade_id")
    private Integer id;

    @Column(name = "funcionario_id")
    private Integer employeeID;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id")
    private Client client;

    @Column(name = "tipo_de_acao", length = 60)
    private String nameActivity;

    @Column(name = "quantidade")
    private Integer quantity;

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "data")
    private LocalDate date;

    public Integer getId() {
        return id;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }
    public Client getClient() {
        return client;
    }



    public String getNameActivity() {
        return nameActivity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    public void setNameActivity(String nameActivity) {
        this.nameActivity = nameActivity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
