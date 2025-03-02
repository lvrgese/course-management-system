package com.lvargese.courseapi.mapper;

import com.lvargese.courseapi.dto.TeacherDTO;
import com.lvargese.courseapi.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeacherMapper {
    TeacherMapper instance= Mappers.getMapper(TeacherMapper.class);
    TeacherDTO toEntity(Teacher teacher);
    Teacher toDTO(TeacherDTO teacherDTO);
}
