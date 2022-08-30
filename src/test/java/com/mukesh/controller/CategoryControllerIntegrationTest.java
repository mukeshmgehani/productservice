/**
 * 
 */
package com.mukesh.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mukesh.db.entity.Category;
import com.mukesh.repository.CategoryRepository;
import com.mukesh.repository.ProductRepository;
import com.mukesh.service.CategoryService;
import com.mukesh.service.ProductService;

/**
 * @author Mukesh
 *
 */
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CategoryControllerIntegrationTest {

	@MockBean
	private ProductService productService;

	@MockBean
	private CategoryService categoryService;

	@MockBean
	private ProductRepository productRepository;

	@MockBean
	private CategoryRepository categoryRepository;

	@InjectMocks
	private CategoryController categoryController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
	}

	@Test
	public void whenCustomerControllerInjected_thenNotNull() throws Exception {
		assertThat(categoryController).isNotNull();
	}

	@Test
	public void whenGetRequestToCategories_thenCorrectResponse() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/categories").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void whenPutRequestToUpdateCategoryWithValidCategory_thenCorrectResponse() throws Exception {
		String category = "{ \"id\": 1,\"name\": \"category_testing\"}";
		mockMvc.perform(
				MockMvcRequestBuilders.put("/categories/1").content(category).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
		}

	@Test
	public void whenPuttRequestToUpdateCategoryAndCategoryNameIsInvalid_thenValidationFailedReponse() throws Exception {

		String category = "{\"id\": 105,\"name\": \"c\"}";
		mockMvc.perform(
				MockMvcRequestBuilders.put("/categories/1").content(category).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isBadRequest());

		verify(categoryRepository, times(0)).save(Mockito.any(Category.class));
	}

	@Test
	public void whenGetRequestToCategoryBasedOnValidId_thenCorrectResponse() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/categories/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void whenDeleteRequestToCategoryBasedOnValidId_thenCorrectResponse() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/categories/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

}
