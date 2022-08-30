/**
 * 
 */
package com.mukesh.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.transaction.BeforeTransaction;

import com.github.javafaker.Faker;
import com.mukesh.db.entity.Category;

/**
 * @author Mukesh
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = com.mukesh.Application.class)
@ActiveProfiles("test")
@TestPropertySource(locations = { "classpath:application-test.properties"})
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;

	@BeforeTransaction
	public void setUp() throws Exception {
		categoryRepository.deleteAll();
	}

	@Test
	public void categoryRepositoryTest() {
		Category category = getCategory();

		category = categoryRepository.saveAndFlush(category);

		List<Category> categories = (List<Category>) categoryRepository.findAll();
		assertFalse(categories.isEmpty());

		String name = "John";
		Category category2 = categoryRepository.findById(category.getId()).get();
		category2.setName(name);
		categoryRepository.saveAndFlush(category2);

		category2 = categoryRepository.findById(category2.getId()).get();
		assertEquals(category2.getName(), name);

		categoryRepository.deleteById(category.getId());

		category = categoryRepository.findById(category.getId()).orElse(null);
		assertNull(category);
	}

	@Test
	public void shouldNotAllowToPersistNullCategoryName() {
		Category category = new Category();
		category.setName(null);
		assertThrows(ConstraintViolationException.class, () -> categoryRepository.saveAndFlush(category));
	}

	/**
	 * @return
	 */
	private Category getCategory() {
		Category category = new Category();
		Faker faker = new Faker();
		category.setName(faker.name().name());
		return category;
	}
}
