package itn.issuemanager.domain;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


@Entity
public class UploadFile {
	@Transient
	private static final Logger log = LoggerFactory.getLogger(UploadFile.class);
	@Transient
	private final Path rootLocation;
	
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
		this.rootLocation = Paths.get("src\\main\\resources\\static\\file");
	}
	
	public void tempUpload(MultipartFile uploadFile, User uploadUser) throws IOException {
		
		this.location = this.uploadDate.atZone(ZoneId.of("America/Los_Angeles")).toInstant().toEpochMilli() + uploadFile.getOriginalFilename();
		
		log.debug(this.location);
		Files.copy(uploadFile.getInputStream(), this.rootLocation.resolve(this.location));
		this.uploadUser = uploadUser;
		this.fileName = uploadFile.getOriginalFilename();
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

	public byte[] load() {
		return null;
	}
	
	
	
}
