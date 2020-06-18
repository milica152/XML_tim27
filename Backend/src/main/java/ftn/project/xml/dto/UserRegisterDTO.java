package ftn.project.xml.dto;

public class UserRegisterDTO {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String username;
    private String profession;

    public UserRegisterDTO(String email, String password, String name, String surname, String username, String profession) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.profession = profession;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
