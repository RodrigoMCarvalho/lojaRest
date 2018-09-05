package br.com.cursomvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cursomvc.models.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

}
