package com.franktran.enrolmentmanagement.student;

import com.franktran.enrolmentmanagement.util.Utility;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StudentMapper {

  @Mapping(target = "dob", source = "dto.dob", dateFormat = Utility.DATE_FORMAT)
  Student dtoToEntity(StudentDto dto);

  @Mapping(target = "dob", source = "entity.dob", dateFormat = Utility.DATE_FORMAT)
  StudentDto entityToDto(Student entity);
}
