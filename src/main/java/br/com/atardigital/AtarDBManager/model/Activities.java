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

    @Column(name = "cliente_id")
    private Integer clientID;

    @Column(name = "tipo_de_acao", length = 60)
    private String nameActivity;

    @Column(name = "quantidade")
    private Integer quantity;

    @Column(name = "data")
    private LocalDate date;

    public Integer getId() {
        return id;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public Integer getClientID() {
        return clientID;
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

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
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
