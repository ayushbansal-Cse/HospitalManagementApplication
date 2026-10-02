package com.ayush.hms.model;

public class DoctorView {

    private int id;
    private String name;
    private String specialization;
    private String experience;
    private String phone;


    public DoctorView() {
    }


    public DoctorView(
            int id,
            String name,
            String specialization,
            String experience,
            String phone) {

        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.experience = experience;
        this.phone = phone;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }


    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}