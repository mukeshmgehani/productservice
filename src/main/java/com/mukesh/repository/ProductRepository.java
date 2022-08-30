package com.mukesh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mukesh.db.entity.Product;
import com.mukesh.model.Status;

/**
 * @author Mukesh
 *
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByStatus(Status status);
}
