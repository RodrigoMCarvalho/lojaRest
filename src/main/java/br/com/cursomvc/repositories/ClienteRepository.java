package br.com.cursomvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cursomvc.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
