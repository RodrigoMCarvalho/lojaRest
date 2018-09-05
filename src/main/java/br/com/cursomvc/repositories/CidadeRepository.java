package br.com.cursomvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cursomvc.models.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

}
