package ru.edu.schedule_app.entities.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDto {

    private String id;
    private int number;
    private List<String> studentIds;
    private List<String> classIds;
}
