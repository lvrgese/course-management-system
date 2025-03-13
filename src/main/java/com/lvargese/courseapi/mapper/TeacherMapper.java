package com.lvargese.courseapi.mapper;

import com.lvargese.courseapi.dto.TeacherDto;
import com.lvargese.courseapi.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeacherMapper {
    TeacherMapper instance= Mappers.getMapper(TeacherMapper.class);
    TeacherDto toEntity(Teacher teacher);
    Teacher toDTO(TeacherDto teacherDTO);
}
