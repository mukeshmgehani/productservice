/**
 * 
 */
package com.mukesh.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.mukesh.db.entity.Product;
import com.mukesh.repository.CategoryRepository;
import com.mukesh.repository.ProductRepository;
import com.mukesh.service.CategoryService;
import com.mukesh.service.ProductService;

/**
 * @author Mukesh
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerIntegrationTest {

	@MockBean
	private ProductService productService;

	@MockBean
	private CategoryService categoryService;

	@MockBean
	private ProductRepository productRepository;

	@MockBean
	private CategoryRepository categoryRepository;

	@Autowired
	ProductController productController;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void whenProductControllerInjected_thenNotNull() throws Exception {
		assertThat(productController).isNotNull();
	}

	@Test
	public void whenGetRequestToProducts_thenCorrectResponse() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/products").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void whenPostRequestToProductsAndValidProduct_thenCorrectResponse() throws Exception {
		String product = "{\"name\": \"product_4\",\"shortDescription\": \"shortDescription_4\",\"longDescription\": \"longDescription_4\",\"status\": \"BLOCKED\",\"price\": 3000,\"categoriesBos\": [{\"name\": \"category_product\"}]}";
		mockMvc.perform(MockMvcRequestBuilders.post("/products").content(product).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void whenPostRequestToProductsAndInvalldProductName_thenValidationFailedReponse() throws Exception {

		String product = "{\"name\": \"p\",\"shortDescription\": \"shortDescription_4\",\"longDescription\": \"longDescription_4\",\"status\": \"BLOCKED\",\"price\": 3000,\"categoriesBos\": [{\"name\": \"category_product\"}]}";
		mockMvc.perform(MockMvcRequestBuilders.post("/products").content(product).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isBadRequest());

		verify(productRepository, times(0)).save(Mockito.any(Product.class));
	}

	@Test
	public void whenGetRequestToProductBasedOnValidId_thenCorrectResponse() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/products/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void whenDeleteRequestToProductBasedOnValidId_thenCorrectResponse() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/products/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
		

	}

}
