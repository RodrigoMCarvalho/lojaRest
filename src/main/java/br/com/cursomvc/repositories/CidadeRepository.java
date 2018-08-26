package br.com.cursomvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cursomvc.models.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

}
