package com.mukesh.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mukesh.model.CategoryBO;
import com.mukesh.model.ProductBO;

/**
 * @author Mukesh
 *
 */
public interface CategoryService {

	public ResponseEntity<List<CategoryBO>> getAllCategories();

	public ResponseEntity<CategoryBO> getCategoryById(Long id);

	public ResponseEntity<List<ProductBO>> getAllProductssByCategoryId(Long categoryId);

	public ResponseEntity<CategoryBO> updateCategory(long id, CategoryBO categoryBO);

	public ResponseEntity<HttpStatus> deleteCategory(Long id);
}
