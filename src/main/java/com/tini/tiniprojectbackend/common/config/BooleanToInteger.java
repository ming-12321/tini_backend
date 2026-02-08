package com.tini.tiniprojectbackend.common.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BooleanToInteger implements AttributeConverter<Boolean, Integer> {

  @Override
  public Integer convertToDatabaseColumn(Boolean aBoolean) {
    return (aBoolean != null && aBoolean) ? 1 : 0;
  }

  @Override
  public Boolean convertToEntityAttribute(Integer integer) {
    return 1 == integer;
  }
}
