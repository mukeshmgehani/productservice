package com.mukesh.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.github.javafaker.Faker;
import com.mukesh.db.entity.Product;
import com.mukesh.model.Status;

@SpringBootTest(webEnvironment =SpringBootTest.WebEnvironment.RANDOM_PORT, classes = com.mukesh.Application.class)
@ActiveProfiles("test")
@TestPropertySource(locations = { "classpath:application-test.properties"})
public class ProductRepositoryTest {

	
	@Autowired
	private ProductRepository productRepository;
	
	
	@BeforeEach
    public void setup()  throws Exception {
		MockitoAnnotations.openMocks(this);
        productRepository.deleteAll();
	}
	
	@Test
    public void contextLoads() { 
    }
    
    

	@Test
	public void productRepositoryTest() {
		Product product = getProduct();
		

		productRepository.saveAndFlush(product);
		List<Product> products = (List<Product>) productRepository.findAll();
		assertFalse(products.isEmpty());

		String shortDescription = "MCD 11 KIDS BLAU";
		String longDescription = "MIKRO-STEREOANLAGE MIT CD-SPIELER";
		Product product2 = productRepository.findById(product.getId()).get();
		product2.setShortDescription(shortDescription);
		product2.setLongDescription(longDescription);
		productRepository.saveAndFlush(product2);

		product = productRepository.findById(product2.getId()).get();

		assertEquals(product.getShortDescription(),shortDescription);
		assertEquals(product.getLongDescription(), longDescription);

		productRepository.deleteAll();
		productRepository.findAll().forEach(System.out::println);
		product = productRepository.findById(product.getId()).orElse(null);
		assertNull(product);
	}


	
	@Test
	public void shouldNotAllowToPersistNullProductName() {
		Product product=getProduct();
		product.setName(null);
		assertThrows(ConstraintViolationException.class, ()-> productRepository.saveAndFlush(product));
    }
	
	/**
	 * @return
	 */
	private Product getProduct() {
		Product product = new Product();
		Faker faker = new Faker();
		product.setLongDescription(faker.address().streetAddress());
		product.setName(faker.address().firstName());
		product.setShortDescription(faker.address().cityName());
		product.setStatus(Status.ONLINE);
		product.setPrice(new BigDecimal(faker.number().digits(4)));
		return product;
	}

	
}
