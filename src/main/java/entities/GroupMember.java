/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 *
 * @author gamma
 */
@Entity
@NamedQuery(name = "GroupMember.deleteAllRows", query = "DELETE from GroupMember")
public class GroupMember implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String studentId;
    private String favouriteTvSeries;
    private String favouriteMusician;
    private int yearOfBirth;
    private String city;

    public GroupMember() {
    }

    public GroupMember(String name, String studentId, String favouriteTvSeries, String favouriteMusician, int yearOfBirth, String city) {
        this.name = name;
        this.studentId = studentId;
        this.favouriteTvSeries = favouriteTvSeries;
        this.favouriteMusician = favouriteMusician;
        this.yearOfBirth = yearOfBirth;
        this.city = city;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFavouriteTvSeries() {
        return favouriteTvSeries;
    }

    public void setFavouriteTvSeries(String favouriteTvSeries) {
        this.favouriteTvSeries = favouriteTvSeries;
    }

    public String getFavouriteMusician() {
        return favouriteMusician;
    }

    public void setFavouriteMusician(String favouriteMusician) {
        this.favouriteMusician = favouriteMusician;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    
    
}
