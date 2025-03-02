package com.lvargese.courseapi.mapper;

import com.lvargese.courseapi.dto.GradeDTO;
import com.lvargese.courseapi.entity.Grade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GradeMapper {
    GradeMapper instance= Mappers.getMapper(GradeMapper.class);

    @Mapping(source = "student.studentId",target = "studentId")
    @Mapping(source = "assignedBy.teacherId",target = "teacherId")
    @Mapping(source = "course.courseId",target = "courseId")
    GradeDTO toDTO(Grade grade);

    @Mapping(source = "studentId",target ="student.studentId" )
    @Mapping(source = "teacherId",target = "assignedBy.teacherId")
    @Mapping(source = "courseId",target = "course.courseId")
    Grade toEntity(GradeDTO gradeDTO);
}
