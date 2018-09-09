package br.com.cursomvc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL { //parâmetros passados na URL como string, converter para Integer
	public static List<Integer> decodeIntList(String s){
		String[] vet = s.split(",");
		List<Integer> list = new ArrayList<>();
		
		for(int i=0; i<vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		
		return list;
	}
	
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";   //caso dê algum erro, retorna uma string vazia
		}
	}
}
