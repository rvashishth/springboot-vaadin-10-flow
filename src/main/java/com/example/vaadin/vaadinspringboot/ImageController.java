package com.example.vaadin.vaadinspringboot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ImageController {

	//TODO: Read image url from application.properties
	
	@RequestMapping(value = "/airtel-logo", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage() {
		File file2 = new File("/Users/b0202777/sample-projects/vaadin-spring-boot/src/main/resources/airtel-logo.png");
		byte[] image = new byte[5000];
		try (FileInputStream fis = new FileInputStream(file2)){
		    fis.read(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
	}
	
}
