package com.mukesh.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
public class CategoryBOTest {
	private Validator validator;

	@BeforeEach
	public void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void whenNameIsNull_thenValidationFails() {
		CategoryBO categoryBO = getCategory();
		categoryBO.setName(null);

		Set<ConstraintViolation<CategoryBO>> violations = validator.validate(categoryBO);
		assertEquals(1, violations.size());
		assertEquals("Name cannot be null or empty", violations.iterator().next().getMessage());
	}

	@Test
	public void whenNameIsLessThan2Character_thenValidationFails() {
		CategoryBO categoryBO = getCategory();
		categoryBO.setName("n");

		Set<ConstraintViolation<CategoryBO>> violations = validator.validate(categoryBO);
		assertEquals(1, violations.size());
		assertEquals("Name should be greater than 2 characters", violations.iterator().next().getMessage());
	}

	

	/**
	 * @return
	 */
	private CategoryBO getCategory() {
		CategoryBO categoryBO = new CategoryBO();
		categoryBO.setName("Name");
		return categoryBO;
	}
}
