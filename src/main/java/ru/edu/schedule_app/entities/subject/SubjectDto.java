package ru.edu.schedule_app.entities.subject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDto {

    private String id;
    private String name;
    private List<String> teacherIds;
    private List<String> classIds;
}
