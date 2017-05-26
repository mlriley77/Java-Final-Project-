package com.grandcircus.spring.models;

import javax.persistence.*;
import java.util.Collection;

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
    private String lastlat;
    private String lastlong;
    private Collection<UsersEntity> usersByFamilyid;

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

    @Basic
    @Column(name = "lastlat", nullable = true, length = 15)
    public String getLastlat() {
        return lastlat;
    }

    public void setLastlat(String lastlat) {
        this.lastlat = lastlat;
    }

    @Basic
    @Column(name = "lastlong", nullable = true, length = 15)
    public String getLastlong() {
        return lastlong;
    }

    public void setLastlong(String lastlong) {
        this.lastlong = lastlong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FamiliesEntity that = (FamiliesEntity) o;

        if (familyid != that.familyid) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (lastlat != null ? !lastlat.equals(that.lastlat) : that.lastlat != null) return false;
        if (lastlong != null ? !lastlong.equals(that.lastlong) : that.lastlong != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = familyid;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastlat != null ? lastlat.hashCode() : 0);
        result = 31 * result + (lastlong != null ? lastlong.hashCode() : 0);
        return result;
    }

    public Collection<UsersEntity> getUsersByFamilyid() {
        return usersByFamilyid;
    }

    public void setUsersByFamilyid(Collection<UsersEntity> usersByFamilyid) {
        this.usersByFamilyid = usersByFamilyid;
    }
}