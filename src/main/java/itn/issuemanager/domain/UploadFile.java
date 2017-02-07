package itn.issuemanager.domain;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

@Entity
public class UploadFile {
	
	private static final Logger log = LoggerFactory.getLogger(UploadFile.class);
	
	@Id
	@GeneratedValue
	private Long id;
	@Column
	private LocalDateTime uploadDate;
	@Column
	private String fileName;
	@ManyToOne
	private User uploadUser;
	@Column
	private boolean enabled;
	@Column
	private String location;
	@Column
	private String fileType;
	@Column
	private String downloadUrl;
	
	public UploadFile() {
		this.enabled = false;
		this.uploadDate = LocalDateTime.now();
	}
	
	public void tempUpload(MultipartFile uploadFile, User uploadUser) throws IOException {
		String fileName = uploadFile.getOriginalFilename();
		File fileLocation = new File("static/file/" + fileName);
		fileLocation.createNewFile();
		FileOutputStream output = new FileOutputStream(fileLocation,false);
		output.write(uploadFile.getBytes());
		output.close();
		
		this.uploadUser = uploadUser;
		this.fileName = uploadFile.getOriginalFilename();
		this.location = fileLocation.getAbsolutePath();
		this.fileType = uploadFile.getContentType();
		
		this.uploadUser = uploadUser;
	}
	public void uploadComplete() {
		this.enabled = true;
		downloadUrl = "/api/file/download/" + this.id;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(LocalDateTime uploadDate) {
		this.uploadDate = uploadDate;
	}
	public User getUploadUser() {
		return uploadUser;
	}
	public void setUploadUser(User uploadUser) {
		this.uploadUser = uploadUser;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	@Override
	public String toString() {
		return "UploadFile [id=" + id + ", uploadDate=" + uploadDate + ", fileName=" + fileName + ", uploadUser="
				+ uploadUser + ", enabled=" + enabled + ", location=" + location + ", fileType=" + fileType
				+ ", downloadUrl=" + downloadUrl + "]";
	}
	
	
	
}
