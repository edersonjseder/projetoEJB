package br.com.beauty.criptografia;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {
	
	public byte[] criptografarInformacao(String informacao){
		MessageDigest md;
		byte[] criptografado = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			criptografado = md.digest(informacao.getBytes());
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return criptografado;
	}

}
