/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.GroupMember;

/**
 *
 * @author gamma
 */
public class GroupMemberDTO {
    private Integer id;
    private String name;
    private String studentId;
    private String favouriteTvSeries;
    private String favouriteMusician;
    private int yearOfBirth;
    
    public GroupMemberDTO(GroupMember member) {
        this.id = member.getId();
        this.name = member.getName();
        this.studentId = member.getStudentId();
        this.favouriteTvSeries = member.getFavouriteTvSeries();
        this.favouriteMusician = member.getFavouriteMusician();
        this.yearOfBirth = member.getYearOfBirth();
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
    
}
