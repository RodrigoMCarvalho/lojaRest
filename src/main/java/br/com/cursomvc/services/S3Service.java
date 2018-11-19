package br.com.cursomvc.services;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import br.com.cursomvc.services.exceptions.FileException;

@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${s3.bucket}")
	String bucketName;

	 public void uploadFile(String localFilePath) {
	 try {
	 File file = new File(localFilePath);
	 LOG.info("Iniciando upload....");
	 s3client.putObject(new PutObjectRequest(bucketName, "teste.jpg", file));
	 LOG.info("Upload finalizado");
	 }
	 catch (AmazonServiceException e) {
	 LOG.info("AmazonServiceException: " + e.getErrorMessage());
	 LOG.info("Status code: " + e.getErrorCode());
	 }
	 catch (AmazonClientException e) {
	 LOG.info("AmazonClientException: " + e.getMessage());
	 }
	 }
//	public URI uploadFile(MultipartFile multipartFile) {
//		try {
//		String fileName = multipartFile.getOriginalFilename(); // obtém o nome do arquivo enviado
//		InputStream is = multipartFile.getInputStream();
//		String contentType = multipartFile.getContentType(); // tipo de arquivo. Ex:imagem, texto.
//		return uploadFile(is, fileName, contentType);
//		} catch (IOException e) {
//			throw new FileException("Erro de IO: " + e.getMessage()); //exceção personalizada
//		}
//	}

	public URI uploadFile(InputStream is, String fileName, String contentType) {
		try {
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(contentType);

			LOG.info("Iniciando upload....");
			s3client.putObject(bucketName, fileName, is, meta);
			LOG.info("Upload finalizado");

			return s3client.getUrl(bucketName, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new FileException("Erro para converter URL para URI");
		}
	}

}
