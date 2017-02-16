package itn.issuemanager.service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import itn.issuemanager.domain.UploadFile;
import itn.issuemanager.domain.User;
import itn.issuemanager.repository.FileRepository;

@Service
public class FileService {
	
	@Autowired
	private FileRepository fileRepository;
	
	private final Path rootLocation;
	
	private static final Logger log = LoggerFactory.getLogger(FileService.class);

	@Autowired
	public FileService(FileServiceProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
		log.debug(this.rootLocation.toString());
	}
	public void downloadComplete(UploadFile file) {
		file.uploadComplete();
		fileRepository.save(file);
	}
	public UploadFile store(MultipartFile file,User uploadUser) throws IOException {
		UploadFile uploadFile = new UploadFile();
		uploadFile.settingPath(this.rootLocation);
		uploadFile.tempUpload(file, uploadUser);
		
		fileRepository.save(uploadFile);
		
		return uploadFile;
	}
	public UploadFile imageUpload(MultipartFile file) throws IOException {
		UploadFile uploadFile = new UploadFile();
		uploadFile.settingPath(this.rootLocation);
		uploadFile.tempUpload(file, null);
		
		fileRepository.save(uploadFile);
		uploadFile.uploadComplete();
		fileRepository.save(uploadFile);g
		
		return uploadFile;
	}
	
	
	public UploadFile load(UploadFile file)	{
		file.settingPath(this.rootLocation);
		return file;
	}
	
	public void delete(UploadFile file){
		file.settingPath(this.rootLocation);
	}
}
