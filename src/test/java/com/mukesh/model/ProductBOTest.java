/**
 * 
 */
package com.mukesh.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Mukesh
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = com.mukesh.Application.class)
public class ProductBOTest {

	private Validator validator;

	@BeforeEach
	public void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}


	@Test
	public void whenNameIsNull_thenValidationFails() {
		ProductBO productBO  = getProduct();
		productBO.setName(null);

		Set<ConstraintViolation<ProductBO>> violations = validator.validate(productBO);
		assertEquals(1, violations.size());

		assertEquals("Name cannot be null or empty", violations.iterator().next().getMessage());
	}

	@Test
	public void whenNameIsLessThan2Character_thenValidationFails() {
		ProductBO productBO  = getProduct();
		productBO.setName("n");

		Set<ConstraintViolation<ProductBO>> violations = validator.validate(productBO);
		assertEquals(1, violations.size());
		assertEquals("Product name should be greater than 2 characters", violations.iterator().next().getMessage());
	}
	/**
	 * @return
	 */
	private ProductBO getProduct() {
		ProductBO productBO = new ProductBO();
		productBO.setLongDescription("Long Description");
		productBO.setName("Name");
		productBO.setShortDescription("Short Description");
		productBO.setStatus(Status.ONLINE);
		productBO.setPrice(new BigDecimal(100.00));
		return productBO;
	}

}
