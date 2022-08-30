package com.mukesh.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mukesh.model.CategoryBO;
import com.mukesh.model.ProductBO;
import com.mukesh.model.Status;
import com.mukesh.service.ProductService;

/**
 * @author Mukesh
 *
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/products")
public class ProductController {
	
	private static final Logger log = LogManager.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<List<ProductBO>> getAllProducts(@RequestParam(required = false) String status) {
			if(ObjectUtils.isNotEmpty(status)) {
				log.info("ProductServiceImpl.getAllProducts() has been called with status =", status);
				return productService.findByStatus(Status.fromString(status));
			}
			log.info("ProductController.getAllProducts() has been called ");
			return productService.getAllProducts();
	}

	@GetMapping(value="/{productId}/categories",produces =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CategoryBO>> getAllCategoriesByProductId(
			@PathVariable(value = "productId") Long productId) {
		log.info("CategoryServiceImpl.getAllCategoriesByProductId() has been called with product id = ", productId);

		return productService.getAllCategoriesByProductId(productId);
	}

	@GetMapping(value="/{id}",produces =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductBO> getProductById(@PathVariable("id") Long id) {
		log.info("ProductController.getProductById() has been called with Id =", id);
		return productService.getProductById(id);
	}

	@PostMapping(produces =  MediaType.APPLICATION_JSON_VALUE,consumes =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductBO> createProduct(@Valid @RequestBody ProductBO productBO) {
		log.info("ProductController.createProduct() has been called with product Data  =", productBO);
		return productService.createProduct(productBO);
	}

	@PutMapping(value="/{id}",produces =  MediaType.APPLICATION_JSON_VALUE,consumes =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductBO> updateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductBO productBO) {
		log.info("ProductController.updateProduct() has been called with Id =%d and product Data = ".formatted(id,
				productBO));
		return productService.updateProduct(id, productBO);
	}

	@DeleteMapping(value="/{id}",produces =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> deleteProductById(@PathVariable("id") Long id) {
		log.info("ProductController.deleteProductById() has been called with Id =", id);
		return productService.deleteProductById(id);
	}

	
	@PostMapping(value="/{productId}/category",produces =  MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductBO> addCategoryToProduct(@PathVariable(value = "productId") Long productId,
			@Valid @RequestBody CategoryBO categoryBO) {
		log.info("ProductController.addCategory() has been called with product id = %d and category Data =%s ".formatted(productId,categoryBO));
		return productService.addCategoryToProduct(productId, categoryBO);
	}
	
	@DeleteMapping(value="/{productId}/categories/{categoryId}",produces =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductBO> deleteCategoryFromProduct(@PathVariable(value = "productId") Long productId,
			@PathVariable(value = "categoryId") Long categoryId) {
		log.info("ProductController.deleteCategoryFromProduct() has been called with Product id = %d and category Id =%d ".formatted(productId,categoryId));
		return productService.deleteCategoryFromProduct(productId, categoryId);
	}
}
