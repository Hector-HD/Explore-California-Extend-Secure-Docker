package io.hectorduenas.explorecali.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class RegionConverter implements AttributeConverter<Region,String>{

	@Override
	public String convertToDatabaseColumn(Region attribute) {
		// TODO Auto-generated method stub
		return attribute.getLabel();
	}

	@Override
	public Region convertToEntityAttribute(String dbData) {
		return Region.findByLabel(dbData);
	}

}
