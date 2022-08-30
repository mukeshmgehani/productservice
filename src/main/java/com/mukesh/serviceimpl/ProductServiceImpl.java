package com.mukesh.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mukesh.db.entity.Category;
import com.mukesh.db.entity.Product;
import com.mukesh.exception.DataNotFoundException;
import com.mukesh.model.CategoryBO;
import com.mukesh.model.ProductBO;
import com.mukesh.model.Status;
import com.mukesh.repository.CategoryRepository;
import com.mukesh.repository.ProductRepository;
import com.mukesh.service.ProductService;
import com.mukesh.transformer.CategoryTransformer;
import com.mukesh.transformer.ProductTransformer;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	private static final Logger log = LogManager.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductTransformer productTransformer;

	@Autowired
	private CategoryTransformer categoryTransformer;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public ResponseEntity<List<ProductBO>> getAllProducts() {
		log.info("ProductServiceImpl.getAllProducts() has been called ");
		List<ProductBO> productBOs = new ArrayList<ProductBO>();
		productBOs = productRepository.findAll().stream() //
				.map(productTransformer::entityToBo) //
				.collect(Collectors.toList());
		if (productBOs.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(productBOs, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ProductBO> getProductById(Long id) {
		log.info("ProductServiceImpl.getProductById() has been called with Id =", id);
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Not found Product with id = " + id));

		return new ResponseEntity<>(productTransformer.entityToBo(product), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<ProductBO>> findByStatus(Status status) {
		log.info("ProductServiceImpl.findByStatus() has been called with status =", status);
		List<ProductBO> productBos = productRepository.findByStatus(status).stream()
				.map(productTransformer::entityToBo).collect(Collectors.toList());

		if (productBos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(productBos, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<HttpStatus> deleteProductById(Long id) {
		log.info("ProductServiceImpl.deleteProductById() has been called with Id =", id);
		productRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<ProductBO> updateProduct(Long id, ProductBO productBO) {
		log.info("ProductServiceImpl.updateProduct() has been called with Id =%d and product Data = ".formatted(id,
				productBO));
		Product _product = productRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Not found Product with id = " + id));
		_product.setName(productBO.getName());
		_product.setShortDescription(productBO.getShortDescription());
		_product.setLongDescription(productBO.getLongDescription());
		_product.setPrice(productBO.getPrice());
		_product.setStatus(productBO.getStatus());
		return new ResponseEntity<>(productTransformer.entityToBo(productRepository.save(_product)), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ProductBO> createProduct(ProductBO productBO) {
		log.info("ProductServiceImpl.createProduct() has been called with product Data  =", productBO);
		Product _product = productRepository.save(productTransformer.boToEntity(productBO));
		return new ResponseEntity<>(productTransformer.entityToBo(_product), HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<List<CategoryBO>> getAllCategoriesByProductId(Long productId) {
		log.info("ProductServiceImpl.getAllCategoriesByProductId() has been called with product id = ", productId);

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new DataNotFoundException("Not found Product with id = " + productId));

		List<CategoryBO> categoryBOs = product.getCategories().stream().map(categoryTransformer::entityToBo)
				.collect(Collectors.toList());
		return new ResponseEntity<>(categoryBOs, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<ProductBO> addCategoryToProduct(Long productId, CategoryBO categoryBO) {
		log.info("ProductServiceImpl.addCategoryToProduct() has been called with product id = %d and category Data =%s ".formatted(productId,categoryBO));
		Product productWithCategories = productRepository.findById(productId).map(product -> {
			Long categoryId = categoryBO.getId();

			// Category is existed
			if (ObjectUtils.isNotEmpty(categoryId)) {
				Category _category = categoryRepository.findById(categoryId)
						.orElseThrow(() -> new DataNotFoundException("Not found Category with id = " + categoryId));
				product.addCategory(_category);
				return productRepository.save(product);
			}

			// add and create new Category
			product.addCategory(categoryTransformer.boToEntity(categoryBO));
			
			return product;
		}).orElseThrow(() -> new DataNotFoundException("Not found Product with id = " + productId));

		return new ResponseEntity<>(productTransformer.entityToBo(productWithCategories), HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ProductBO> deleteCategoryFromProduct(Long productId, Long categoryId) {
		log.info("ProductServiceImpl.deleteCategoryFromProduct() has been called with Product id = %d and category Id =%d ".formatted(productId,categoryId));
		
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new DataNotFoundException("Not found Product with id = " + productId));

		product.removeCategory(categoryId);
		product=productRepository.save(product);
		
		return new ResponseEntity<>(productTransformer.entityToBo(product), HttpStatus.ACCEPTED);
	}

	
}
