package com.erenavci.projecteren.repository;

import com.erenavci.projecteren.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
	