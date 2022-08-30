package com.mukesh.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mukesh.model.CategoryBO;
import com.mukesh.model.ProductBO;
import com.mukesh.service.CategoryService;

/**
 * @author Mukesh
 *
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/categories")
public class CategoryController {

	private static final Logger log = LogManager.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CategoryBO>> getAllCategories() {
		log.info("CategoryController.getAllCategories() has been called");
		return categoryService.getAllCategories();
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryBO> getCategoryById(@PathVariable(value = "id") Long id) {
		log.info("CategoryController.getCategoryById() has been called with category id = ", id);
		return categoryService.getCategoryById(id);
	}

	@GetMapping(value = "/{categoryId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductBO>> getAllProductssByCategoryId(
			@PathVariable(value = "categoryId") Long categoryId) {
		log.info("CategoryController.getAllProductssByCategoryId() has been called with category id = ", categoryId);
		return categoryService.getAllProductssByCategoryId(categoryId);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryBO> updateCategory(@PathVariable("id") long id,
			@Valid @RequestBody CategoryBO categoryBO) {
		log.info("CategoryServiceImpl.updateCategory() has been called with id = %d and category Data =%s "
				.formatted(id, categoryBO));
		return categoryService.updateCategory(id, categoryBO);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("id") Long id) {
		log.info("CategoryServiceImpl.deleteCategory() has been called with category Id =%d ".formatted(id));
		return categoryService.deleteCategory(id);
	}
}
