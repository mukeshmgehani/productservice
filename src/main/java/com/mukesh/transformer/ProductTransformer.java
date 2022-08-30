package com.mukesh.transformer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mukesh.db.entity.Product;
import com.mukesh.model.ProductBO;


/**
 * @author Mukesh
 *
 */
@Component
public class ProductTransformer {

	private static final Logger log = LogManager.getLogger(ProductTransformer.class);
	
	@Autowired
	private ModelMapper mapper;
	
	public ProductBO entityToBo(Product product) {
		log.debug("ProductTransformer.boToEntity() has been called with Product entity  -- ", product);
		return mapper.map(product,ProductBO.class);
	}

	public Product boToEntity(ProductBO productBO) {
		log.debug("ProductTransformer.boToEntity() has been called with Product data = %s".formatted(productBO.toString()));
		return mapper.map(productBO,Product.class);
	}

}
