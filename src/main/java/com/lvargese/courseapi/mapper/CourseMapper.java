package com.lvargese.courseapi.mapper;

import com.lvargese.courseapi.dto.CourseDto;
import com.lvargese.courseapi.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface CourseMapper {
    CourseMapper instance = Mappers.getMapper(CourseMapper.class);
    @Mapping(source = "teacher.teacherId",target = "teacherId")
    @Mapping(source = "courseMaterial.courseMaterialId",target = "courseMaterialId")
    CourseDto toDTO(Course course);

    @Mapping(source = "teacherId",target="teacher.teacherId")
    @Mapping(source = "courseMaterialId",target = "courseMaterial.courseMaterialId")
    Course toCourse(CourseDto courseDTO);
}
