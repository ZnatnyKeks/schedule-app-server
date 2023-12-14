package ru.edu.schedule_app.entities.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String id;

    private String email;
    private String name;
    private int age;
    private String info;
    private String imageUrl;

    private String role;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.age = user.getAge();
        this.info = user.getInfo();
        this.imageUrl = user.getImageUrl();
        this.role = user.getRole().name();
    }
}
