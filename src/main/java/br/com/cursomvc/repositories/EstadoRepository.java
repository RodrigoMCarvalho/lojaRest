package br.com.cursomvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cursomvc.models.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {

}
