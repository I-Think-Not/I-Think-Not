package itn.issuemanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class User {
	@Id
	@GeneratedValue
	private Long id;
	
	@NotBlank
	@Column(length = 20)
	private String name;
	
	@NotBlank
	@Email(message="이메일 형식을 사용해야 합니다.")
	@Column(length = 30, unique = true)
	private String userId;
	
	@NotBlank
	@JsonIgnore
	@Password
	private String password;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_user_file"))
	private UploadFile profile;
	
	@Transient
	private BCryptPasswordEncoder passwordEncoder;
	
	public User(){
	    // TODO 인스턴스를 매번 생성하지 말고 Spring에 빈으로 등록한 후 사용하도록 한다.
		passwordEncoder =new BCryptPasswordEncoder();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEncryptPassword(){
		this.password = passwordEncoder.encode(this.password);
	}
	public String getProfile() {
		if(profile==null)
			return "/api/file/0";
		else
		return profile.getDownloadUrl();
	}

	public void setProfile(UploadFile profile) {
		this.profile = profile;
	}

	//패스워드 일치 여부 확인
	public boolean isPassword(String newPassword){
		if(newPassword == null){
			return false;
		}
		return passwordEncoder.matches(newPassword, password);
	}
	
	public boolean isSameUser(User user){
		return isSameUser(user.getUserId());
	}
	
	public boolean isSameUser(String newUserId){
		if(this.userId == null){
			return false;
		}
		return this.userId.equals(newUserId);
	}
	//개인정보 수정시 변경사항 비밀번호, 사진경로
	public void update(User newUser, String newPassword){
		this.password = passwordEncoder.encode(newPassword);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", password=" + password + ", name=" + name + ""
				+ ", profile="+profile + "]";
	}
}
