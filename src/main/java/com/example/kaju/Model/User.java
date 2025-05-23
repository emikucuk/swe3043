package com.example.kaju.Model;
import com.example.kaju.Dtos.UserDto;
import lombok.Data;

@Data
public class User {
    private long id;
    private Long version = 0L;
    private String name;
    private String email;
    private String address;

    public User() {
        this.version = 0L;
    }

    public User(long id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.version = 0L;
    }

    public User(UserDto userDto) {
        this.id = userDto.getId();
        this.name = userDto.getName();
        this.email = userDto.getEmail();
        this.address = userDto.getAddress();
        this.version = 0L;
    }

    public UserDto viewAsUserDto() {
        return new UserDto(id, name, email, address);
    }

    // Getters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }   

    public String getAddress() {
        return address;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {    
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
