package br.com.cursomvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cursomvc.models.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

}
