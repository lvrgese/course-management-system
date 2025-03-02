package com.lvargese.courseapi.mapper;

import com.lvargese.courseapi.dto.CourseMaterialDTO;
import com.lvargese.courseapi.entity.Course;
import com.lvargese.courseapi.entity.CourseMaterial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CourseMaterialMapper {
    CourseMaterialMapper instance = Mappers.getMapper(CourseMaterialMapper.class);

    @Mapping(source = "course.courseId",target = "courseId")
    CourseMaterialDTO toDTO(CourseMaterial courseMaterial);
    @Mapping(source = "courseId",target = "course.courseId")
    Course toEntity(CourseMaterialDTO courseMaterialDTO);
}
