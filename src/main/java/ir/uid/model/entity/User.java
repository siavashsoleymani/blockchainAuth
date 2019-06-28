package ir.uid.model.entity;

import javax.persistence.Transient;

public class User {
    private String name;

    private String family;

    private String email;

    private String dob;

    private String sex;

    private String key;

    @Transient
    private String lid;


    public User(String name, String family, String email, String dob, String sex, String key, String lid) {
        this.name = name;
        this.family = family;
        this.email = email;
        this.dob = dob;
        this.sex = sex;
        this.key = key;
        this.lid = lid;
    }

    public User(String name, String family, String email, String dob, String sex) {
        this.name = name;
        this.family = family;
        this.email = email;
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

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
