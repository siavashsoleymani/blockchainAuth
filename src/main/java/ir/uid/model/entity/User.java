package ir.uid.model.entity;

public class User {
    private String name;

    private String dob;

    private String sex;

    public User(String name, String dob, String sex) {
        this.name = name;
        this.dob = dob;
        this.sex = sex;
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
