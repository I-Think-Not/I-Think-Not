package itn.issuemanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class User {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, length = 20)
	private String name;
	
	@Column(nullable = false, length = 20, unique = true)
	private String userId;
	
	@JsonIgnore
	private String password;
	
	private String profile;

	public User(){}
	
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

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	//패스워드 일치 여부 확인
	public boolean matchPassword(String newPassword){
		if(newPassword == null){
			return false;
		}
		return newPassword.equals(password);
	}
	
	public boolean matchedId(Long newId){
		if(newId == null){
			return false;
		}
		return newId.equals(id);
	}
	//개인정보 수정시 변경사항 비밀번호, 사진경로
	public void update(User newUser){
		this.password = newUser.password;
		this.profile = newUser.profile;
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
