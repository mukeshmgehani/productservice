package com.mukesh.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mukesh.model.CategoryBO;
import com.mukesh.model.ProductBO;
import com.mukesh.model.Status;

public interface ProductService {

	public ResponseEntity<List<ProductBO>> getAllProducts();
	
	  public ResponseEntity<ProductBO> getProductById(Long id);
	  
	  public ResponseEntity<HttpStatus> deleteProductById( Long id);
	  
	  public ResponseEntity<ProductBO> updateProduct( Long id,  ProductBO productBO);
	  
	  public ResponseEntity<ProductBO> createProduct( ProductBO productBO);

	public ResponseEntity<List<CategoryBO>> getAllCategoriesByProductId(Long productId); 
	
	public ResponseEntity<ProductBO> addCategoryToProduct(Long productId, CategoryBO categoryBO);

	public ResponseEntity<ProductBO> deleteCategoryFromProduct(Long productId, Long categoryId);

	ResponseEntity<List<ProductBO>> findByStatus(Status status);

}
