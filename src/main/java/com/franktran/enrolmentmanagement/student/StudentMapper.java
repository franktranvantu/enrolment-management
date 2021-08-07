package com.franktran.enrolmentmanagement.student;

import com.franktran.enrolmentmanagement.util.Utility;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {

  StudentMapper MAPPER_INSTANCE = Mappers.getMapper(StudentMapper.class);

  @Mapping(target = "dob", source = "dto.dob", dateFormat = Utility.DATE_FORMAT)
  Student dtoToEntity(StudentDto dto);

  @Mapping(target = "dob", source = "entity.dob", dateFormat = Utility.DATE_FORMAT)
  StudentDto entityToDto(Student entity);
}
