package com.grandcircus.spring.models;
import javax.persistence.*;
import java.sql.Timestamp;
/**
 * Class description
 *
 * @author Sarah Guarino
 * @version 1.0
 */
@Entity
@Table(name = "users", schema = "checkin")
public class UsersEntity {
    private int userid;
    private int usergroup;
    private String email;
    private String password;
    private String fname;
    private String lname;
    private int familyid;
    private String lastlat;
    private String lastlong;
    private Timestamp lasttime;
    //    private FamiliesEntity familiesByFamilyid;
    @Id
    @Column(name = "userid", nullable = false)
    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    @Basic
    @Column(name = "usergroup", nullable = false)
    public int getUsergroup() {
        return usergroup;
    }
    public void setUsergroup(int usergroup) {
        this.usergroup = usergroup;
    }
    @Basic
    @Column(name = "email", nullable = false, length = 60)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Basic
    @Column(name = "password", nullable = false, length = 45)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Basic
    @Column(name = "fname", nullable = false, length = 45)
    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    @Basic
    @Column(name = "lname", nullable = false, length = 45)
    public String getLname() {
        return lname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }
    @Basic
    @Column(name = "familyid", nullable = false)
    public int getFamilyid() {
        return familyid;
    }
    public void setFamilyid(int familyid) {
        this.familyid = familyid;
    }
    @Basic
    @Column(name = "lastlat", nullable = true, length = 25)
    public String getLastlat() {
        return lastlat;
    }
    public void setLastlat(String lastlat) {
        this.lastlat = lastlat;
    }
    @Basic
    @Column(name = "lastlong", nullable = true, length = 25)
    public String getLastlong() {
        return lastlong;
    }
    public void setLastlong(String lastlong) {
        this.lastlong = lastlong;
    }
    @Basic
    @Column(name = "lasttime", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Timestamp getLasttime() {
        return lasttime;
    }
    public void setLasttime(Timestamp lasttime) {
        this.lasttime = lasttime;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        if (userid != that.userid) return false;
        if (usergroup != that.usergroup) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (fname != null ? !fname.equals(that.fname) : that.fname != null) return false;
        if (lname != null ? !lname.equals(that.lname) : that.lname != null) return false;
        if (familyid != that.familyid) return false;
        if (lastlat != null ? !lastlat.equals(that.lastlat) : that.lastlat != null) return false;
        if (lastlong != null ? !lastlong.equals(that.lastlong) : that.lastlong != null) return false;
        if (lasttime != null ? !lasttime.equals(that.lasttime) : that.lasttime != null) return false;
        return true;
    }
    @Override
    public int hashCode() {
        int result = userid;
        result = 31 * result + usergroup;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (fname != null ? fname.hashCode() : 0);
        result = 31 * result + (lname != null ? lname.hashCode() : 0);
        result = 31 * result + familyid;
        result = 31 * result + (lastlat != null ? lastlat.hashCode() : 0);
        result = 31 * result + (lastlong != null ? lastlong.hashCode() : 0);
        result = 31 * result + (lasttime != null ? lasttime.hashCode() : 0);
        return result;
    }
//    @ManyToOne
//    @JoinColumn(name = "familyid", referencedColumnName = "familyid", nullable = false)
//    public FamiliesEntity getFamiliesByFamilyid() {
//        return familiesByFamilyid;
//    }
//
//    public void setFamiliesByFamilyid(FamiliesEntity familiesByFamilyid) {
//        this.familiesByFamilyid = familiesByFamilyid;
//    }
}