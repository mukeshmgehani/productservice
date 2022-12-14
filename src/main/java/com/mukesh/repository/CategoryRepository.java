package com.mukesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mukesh.db.entity.Category;

/**
 * @author Mukesh
 *
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
