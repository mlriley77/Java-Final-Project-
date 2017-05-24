package com.grandcircus.spring.models;

import javax.persistence.*;

/**
 * Class description
 *
 * @author Sarah Guarino
 * @version 1.0
 */
@Entity
@Table(name = "locations", schema = "checkin", catalog = "")
public class LocationsEntity {
    private int locationid;
    private String latitude;
    private String longitude;
    private String name;
    private String icon;
    private String route;
    private String locality;
    private String state;
    private int zipcode;
    private FamiliesEntity familiesByFamilyid;

    @Id
    @Column(name = "locationid", nullable = false)
    public int getLocationid() {
        return locationid;
    }

    public void setLocationid(int locationid) {
        this.locationid = locationid;
    }

    @Basic
    @Column(name = "latitude", nullable = false, length = 45)
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude", nullable = false, length = 45)
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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
    @Column(name = "icon", nullable = true, length = 60)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "route", nullable = false, length = 45)
    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    @Basic
    @Column(name = "locality", nullable = false, length = 45)
    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    @Basic
    @Column(name = "state", nullable = false, length = 45)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "zipcode", nullable = false)
    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationsEntity that = (LocationsEntity) o;

        if (locationid != that.locationid) return false;
        if (zipcode != that.zipcode) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (icon != null ? !icon.equals(that.icon) : that.icon != null) return false;
        if (route != null ? !route.equals(that.route) : that.route != null) return false;
        if (locality != null ? !locality.equals(that.locality) : that.locality != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = locationid;
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (route != null ? route.hashCode() : 0);
        result = 31 * result + (locality != null ? locality.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + zipcode;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "familyid", referencedColumnName = "familyid", nullable = false)
    public FamiliesEntity getFamiliesByFamilyid() {
        return familiesByFamilyid;
    }

    public void setFamiliesByFamilyid(FamiliesEntity familiesByFamilyid) {
        this.familiesByFamilyid = familiesByFamilyid;
    }
}
