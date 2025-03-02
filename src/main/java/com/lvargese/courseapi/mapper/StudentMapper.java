package com.lvargese.courseapi.mapper;

import com.lvargese.courseapi.dto.StudentDTO;
import com.lvargese.courseapi.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {
    StudentMapper instance = Mappers.getMapper(StudentMapper.class);
    @Mapping(source = "courses",target = "courses")
    @Mapping(source = "guardian.name",target = "guardianName")
    @Mapping(source = "guardian.email",target = "guardianEmail")
    @Mapping(source = "guardian.mobile",target = "guardianMobile")
    StudentDTO toDTO(Student student);

    @Mapping(source = "courses",target = "courses")
    @Mapping(source = "guardianName",target = "guardian.name")
    @Mapping(source = "guardianEmail",target = "guardian.email")
    @Mapping(source = "guardianMobile",target = "guardian.mobile")
    Student toEntity(StudentDTO studentDTO);
}
