package br.com.cursomvc.resources.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;

public class URL { //parâmetros passados na URL como string, converter para Integer
	public static List<Integer> decodeIntList(String s){
//		String[] vet = s.split(",");
//		List<Integer> list = new ArrayList<>();
//		
//		for(int i=0; i<vet.length; i++) {
//			list.add(Integer.parseInt(vet[i]));
//		}
//		
//		return list;
		return Arrays.asList(s.split(",")).stream().map(x 
				-> Integer.parseInt((String) x)).collect(Collectors.toList());
	}
}
