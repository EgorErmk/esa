package com.example.demo.model.JMS;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "logs", schema = "myschema", catalog = "mydb")
public class LogsHibernateEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "change_type", nullable = false, length = 30)
    private String changeType;
    @Basic
    @Column(name = "changed_entity", nullable = false, length = -1)
    private String changedEntity;
    @Basic
    @Column(name = "changed_values", nullable = true)
    private String changedValues;
    @Basic
    @Column(name = "date_of_change", nullable = true)
    private Date dateOfChange;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getChangedEntity() {
        return changedEntity;
    }

    public void setChangedEntity(String changedEntity) {
        this.changedEntity = changedEntity;
    }

    public Object getChangedValues() {
        return changedValues;
    }

    public void setChangedValues(String changedValues) {
        this.changedValues = changedValues;
    }

    public Date getDateOfChange() {
        return dateOfChange;
    }

    public void setDateOfChange(Date dateOfChange) {
        this.dateOfChange = dateOfChange;
    }
}
