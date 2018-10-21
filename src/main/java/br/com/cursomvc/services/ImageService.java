package br.com.cursomvc.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.cursomvc.services.exceptions.FileException;

@Service
public class ImageService {

	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
		String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
		if("png".equals(ext) && !"jpg".equals(ext)) {
			throw new FileException("Somente imagens PNG e JPG são permitidas.");
		}
		try {
			BufferedImage img = ImageIO.read(uploadedFile.getInputStream());
			if("png".equals(ext)) { //teste para saber se a imagem é JPG
				img = pngToJpg(img);
			}
			return img;
		} catch (IOException e) {
			throw new FileException("Erro ao ler o arquivo.");
		}
	}

	public BufferedImage pngToJpg(BufferedImage img) {
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), 
				BufferedImage.TYPE_INT_ARGB);
		jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
		return null;
	}
	
	public InputStream getInputStream(BufferedImage img, String extension) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, extension, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}
	
	//recorta a imagem de forma que ela fica quadrada
	public BufferedImage cropSquare(BufferedImage sourceImg) {
		int min = (sourceImg.getHeight() <= sourceImg.getWidth()) ? sourceImg.getHeight() : sourceImg.getWidth(); //descobre o mínimo
		return Scalr.crop( //recortar a imagem
			sourceImg, 
			(sourceImg.getWidth()/2) - (min/2),  //metade da largura - metade do mínimo
			(sourceImg.getHeight()/2) - (min/2),  //metade da altura - metade do mínimo
			min, 
			min);		
	}
	
	//redimencionar a imagem
	public BufferedImage resize(BufferedImage sourceImg, int size) {
		return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);
	}
	
	
	
	
}
