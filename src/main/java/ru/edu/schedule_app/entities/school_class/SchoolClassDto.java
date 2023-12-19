package ru.edu.schedule_app.entities.school_class;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolClassDto {

    private String id;
    private String weekday;
    private int hour;
    private int minute;
    private String subjectId;
    private String teacherId;
    private List<String> groupIds;
}
