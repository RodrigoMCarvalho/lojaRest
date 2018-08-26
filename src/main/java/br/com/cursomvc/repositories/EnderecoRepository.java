package br.com.cursomvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cursomvc.models.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

}
