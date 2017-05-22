package com.grandcircus.spring.models;

import javax.persistence.*;

/**
 * Class description
 *
 * @author Sarah Guarino
 * @version 1.0
 */
@Entity
@Table(name = "families", schema = "checkin", catalog = "")
public class FamiliesEntity {
    private int familyid;
    private String name;

    @Id
    @Column(name = "familyid", nullable = false)
    public int getFamilyid() {
        return familyid;
    }

    public void setFamilyid(int familyid) {
        this.familyid = familyid;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FamiliesEntity that = (FamiliesEntity) o;

        if (familyid != that.familyid) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = familyid;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
