package br.com.cursomvc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cursomvc.models.Cidade;
import br.com.cursomvc.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public List<Cidade> findByEstado(Integer estado_id){
		return cidadeRepository.findCidades(estado_id);
	}
}
