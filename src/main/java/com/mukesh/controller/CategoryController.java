package com.mukesh.controller;

import java.util.List;

import javax.validation.Valid;

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

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CategoryBO>> getAllCategories() {

		return categoryService.getAllCategories();
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryBO> getCategoryById(@PathVariable(value = "id") Long id) {

		return categoryService.getCategoryById(id);
	}

	@GetMapping(value = "/{categoryId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductBO>> getAllProductssByCategoryId(
			@PathVariable(value = "categoryId") Long categoryId) {
		return categoryService.getAllProductssByCategoryId(categoryId);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryBO> updateCategory(@PathVariable("id") long id, @Valid @RequestBody CategoryBO categoryBO) {
		return categoryService.updateCategory(id, categoryBO);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("id") Long id) {

		return categoryService.deleteCategory(id);
	}
}
