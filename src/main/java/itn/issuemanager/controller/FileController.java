package itn.issuemanager.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import itn.issuemanager.config.LoginUser;
import itn.issuemanager.domain.ForbiddenTypeFileException;
import itn.issuemanager.domain.UploadFile;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.FileRepository;
import itn.issuemanager.service.FileService;

@RestController
@RequestMapping("/api/file")
public class FileController {

	
	@Autowired
	FileRepository fileRepository;
	@Autowired
	FileService fileService;
	
	
	@PostMapping("/")
	public UploadFile upload(@RequestParam("file")MultipartFile file,@LoginUser User uploadUser) throws IOException, ForbiddenTypeFileException{
		UploadFile uploadFile = fileService.store(file, uploadUser);
		return uploadFile;
	}
	
	@GetMapping("/{id}")
	public void download(@PathVariable("id") long id, HttpServletResponse response) throws IOException{
		UploadFile file;
		if(id==0){
			file = new UploadFile();
			file.setLocation("default-user-image.png");
		}
		else
		file = fileRepository.findOne(id);
		
		fileService.load(file);
		file.load(response);
	}
	
	@DeleteMapping("/{id}")
	public UploadFile deleteFile(@PathVariable("id") long id,@LoginUser User uploadUser)
	{
		UploadFile deletedFile = fileRepository.findOne(id);
		if(deletedFile.uploaderCheck(uploadUser)){
			fileRepository.delete(deletedFile);
			return deletedFile;
		}
		return null;
	}
}
