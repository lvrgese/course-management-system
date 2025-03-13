package com.lvargese.courseapi.service;

import com.lvargese.courseapi.dto.CourseDto;

public interface CourseService {
    CourseDto getCourseById(Long id);
}
