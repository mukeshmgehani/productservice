package com.mukesh.transformer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mukesh.db.entity.Category;
import com.mukesh.model.CategoryBO;

@Component
public class CategoryTransformer {

	private static final Logger log = LogManager.getLogger(CategoryTransformer.class);
	
	@Autowired
	private ModelMapper mapper;

	public CategoryBO entityToBo(Category category) {
		log.debug("CategoryTransformer.entityToBo() has been called with category -- ", category);
		return mapper.map(category, CategoryBO.class);
	}

		public Category boToEntity(CategoryBO categoryBO) {
		log.debug("CategoryTransformer.boToEntity() has been called with customer  -- ", categoryBO);
		return mapper.map(categoryBO, Category.class);
	}

}
