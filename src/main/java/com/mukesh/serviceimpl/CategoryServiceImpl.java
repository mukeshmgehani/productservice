package com.mukesh.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mukesh.db.entity.Category;
import com.mukesh.exception.DataNotFoundException;
import com.mukesh.model.CategoryBO;
import com.mukesh.model.ProductBO;
import com.mukesh.repository.CategoryRepository;
import com.mukesh.service.CategoryService;
import com.mukesh.transformer.CategoryTransformer;
import com.mukesh.transformer.ProductTransformer;

/**
 * @author Mukesh
 *
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	private static final Logger log = LogManager.getLogger(CategoryServiceImpl.class);

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryTransformer categoryTransformer;

	@Autowired
	private ProductTransformer productTransformer;
	
	
	@Override
	public ResponseEntity<List<CategoryBO>> getAllCategories() {
		log.info("CategoryServiceImpl.getAllCategories() has been called");
		List<CategoryBO> categorieBos = new ArrayList<CategoryBO>();

		categorieBos = categoryRepository.findAll().stream().map(categoryTransformer::entityToBo)
				.collect(Collectors.toList());

		if (categorieBos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(categorieBos, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<CategoryBO> getCategoryById(Long id) {
		log.info("CategoryServiceImpl.getCategoryById() has been called with category id = ",id);
	    Category category = categoryRepository.findById(id)
	           .orElseThrow(() -> new DataNotFoundException("Not found Category with id = " + id));

	        return new ResponseEntity<>(categoryTransformer.entityToBo(category), HttpStatus.OK);
	      }

	@Override
	public ResponseEntity<List<ProductBO>> getAllProductssByCategoryId(Long categoryId) {
		log.info("CategoryServiceImpl.getAllProductssByCategoryId() has been called with category id = ",categoryId);
		
		Category category = categoryRepository.findById(categoryId)
		           .orElseThrow(() -> new DataNotFoundException("Not found Category with id = " + categoryId));

		List<ProductBO> productBOs=category.getProducts().stream().map(productTransformer::entityToBo).collect(Collectors.toList());
	      return new ResponseEntity<>(productBOs, HttpStatus.OK);
	    }


	@Override
	public ResponseEntity<CategoryBO> updateCategory(long id, CategoryBO categoryBO) {
		log.info("CategoryServiceImpl.updateCategory() has been called with id = %d and category Data =%s ".formatted(id,categoryBO));
		
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("CategoryId " + id + "not found"));
		category.setName(categoryBO.getName());
		return new ResponseEntity<>(categoryTransformer.entityToBo(categoryRepository.save(category)), HttpStatus.OK);
	}


	@Override
	public ResponseEntity<HttpStatus> deleteCategory(Long id) {
		log.info("CategoryServiceImpl.deleteCategory() has been called with category Id =%d ".formatted(id));
		categoryRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
