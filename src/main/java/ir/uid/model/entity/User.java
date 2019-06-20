package ir.uid.model.entity;

import javax.persistence.Transient;

public class User {
    private String name;

    private String dob;

    private String sex;

    private String key;

    @Transient
    private String lid;


    public User(String name, String dob, String sex, String key) {
        this.name = name;
        this.dob = dob;
        this.sex = sex;
    }

    public User(String name, String dob, String sex) {
        this.name = name;
        this.dob = dob;
        this.sex = sex;
    }

    public User() {
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
