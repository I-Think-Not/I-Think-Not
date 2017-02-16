package itn.issuemanager.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Label {

	@Id
	@GeneratedValue
	private Long id;
	@Column
	private String name;
	@Column
	private String color;
	@ManyToMany
	private List<Issue> issues;

	public Label() {}

	// TODO 생성자를 사용하는 곳이 없다. 굳이 필요한가?
	public Label(Long id, String name, String color, List<Issue> issues) {
		super();
		this.id = id;
		this.name = name;
		this.color = color;
		this.issues = issues;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Label other = (Label) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Label [id=" + id + ", name=" + name + ", color=" + color + ", issue=" + issues + "]";
	}
	
}
