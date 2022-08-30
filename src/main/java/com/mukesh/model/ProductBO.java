package com.mukesh.model;

import java.math.BigDecimal;
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
public class ProductBO {

	private Long id;

	@NotBlank(message = "Name cannot be null or empty")
	@Size(min = 2, message = "Product name should be greater than 2 characters")
	private String name;

	private String shortDescription;

	private String longDescription;

	private Status status;

	private BigDecimal price;

	@JsonInclude(Include.NON_NULL)
	private Set<CategoryBO> categoriesBos;

	/**
	 * @param id
	 * @param name
	 * @param shortDescription
	 * @param longDescription
	 * @param status
	 * @param price
	 * @param categoriesBos
	 */
	public ProductBO(Long id, String name, String shortDescription, String longDescription, Status status,
			BigDecimal price, Set<CategoryBO> categoriesBos) {
		super();
		this.id = id;
		this.name = name;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.status = status;
		this.price = price;
		this.categoriesBos = categoriesBos;
	}

	public ProductBO() {
	}

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
	 * @return the shortDescription
	 */
	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * @param shortDescription the shortDescription to set
	 */
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	/**
	 * @return the longDescription
	 */
	public String getLongDescription() {
		return longDescription;
	}

	/**
	 * @param longDescription the longDescription to set
	 */
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the categoriesBos
	 */
	public Set<CategoryBO> getCategoriesBos() {
		return categoriesBos;
	}

	/**
	 * @param categoriesBos the categoriesBos to set
	 */
	public void setCategoriesBos(Set<CategoryBO> categoriesBos) {
		this.categoriesBos = categoriesBos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoriesBos, id, longDescription, name, price, shortDescription, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductBO other = (ProductBO) obj;
		return Objects.equals(categoriesBos, other.categoriesBos) && Objects.equals(id, other.id)
				&& Objects.equals(longDescription, other.longDescription) && Objects.equals(name, other.name)
				&& Objects.equals(price, other.price) && Objects.equals(shortDescription, other.shortDescription)
				&& status == other.status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductBO [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", shortDescription=");
		builder.append(shortDescription);
		builder.append(", longDescription=");
		builder.append(longDescription);
		builder.append(", status=");
		builder.append(status);
		builder.append(", price=");
		builder.append(price);
		builder.append(", categoriesBos=");
		builder.append(categoriesBos);
		builder.append("]");
		return builder.toString();
	}

}
