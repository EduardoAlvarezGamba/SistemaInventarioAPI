package org.example.SistemaInventarioAPI.repository;

import org.example.SistemaInventarioAPI.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> { }
