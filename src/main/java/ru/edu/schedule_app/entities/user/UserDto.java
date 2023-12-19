package ru.edu.schedule_app.entities.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {


    private String id;
    private String email;
    private String password;
    private String name;
    private String role;

    private String groupId;

    private List<String> classToTeachIds;
    private List<String> subjectToTeachIds;
}
