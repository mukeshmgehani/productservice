package com.mukesh.model;

import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Mukesh
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class CategoryBO {

	private Long id;

	@NotBlank(message = "Name cannot be null or empty")
	@Size(min = 2, message = "Name should be greater than 2 characters")
	private String name;

	@JsonInclude(Include.NON_NULL)
	private Set<ProductBO> productBOs;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the productBOs
	 */
	public Set<ProductBO> getProductBOs() {
		return productBOs;
	}

	/**
	 * @param productBOs the productBOs to set
	 */
	public void setProductBOs(Set<ProductBO> productBOs) {
		this.productBOs = productBOs;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, productBOs);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryBO other = (CategoryBO) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(productBOs, other.productBOs);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CategoryBO [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", productBOs=");
		builder.append(productBOs);
		builder.append("]");
		return builder.toString();
	}

}
