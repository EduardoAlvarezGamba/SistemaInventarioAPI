package org.example.SistemaInventarioAPI.repository;

import org.example.SistemaInventarioAPI.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long>{ }
