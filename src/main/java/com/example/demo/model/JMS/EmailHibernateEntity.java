package com.example.demo.model.JMS;

import jakarta.persistence.*;

@Entity
@Table(name = "email", schema = "myschema", catalog = "mydb")
public class EmailHibernateEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "email_id", nullable = false)
    private int emailId;
    @Basic
    @Column(name = "type_of_operation", nullable = false, length = -1)
    private String typeOfOperation;
    @Basic
    @Column(name = "email", nullable = false, length = -1)
    private String email;

    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    public String getTypeOfOperation() {
        return typeOfOperation;
    }

    public void setTypeOfOperation(String typeOfOperation) {
        this.typeOfOperation = typeOfOperation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
