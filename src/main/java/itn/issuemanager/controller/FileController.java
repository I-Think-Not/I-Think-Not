package itn.issuemanager.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import itn.issuemanager.config.LoginUser;
import itn.issuemanager.domain.UploadFile;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.FileRepository;

@RestController
@RequestMapping("/api/file")
public class FileController {
	
	@Autowired
	FileRepository fileRepository;
	
	@PostMapping("/")
	public String upload(@RequestParam("file")MultipartFile file,@LoginUser User uploadUser) throws IOException{
		UploadFile uploadFile = new UploadFile();
		
		uploadFile.tempUpload(file,uploadUser);
		fileRepository.save(uploadFile);
		
		return "";
	}
	@GetMapping("/{id}")
	public byte[] download(@PathVariable("id") long id){
		UploadFile file = fileRepository.findOne(id);
		
		return file.load();
	}
}
