package itn.issuemanager.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Label {

	@Id
	@GeneratedValue
	private Long id;
	@Column
	private String name;
	@Column
	private String color;
	@OneToMany
	private List<Issue> issues;

	public Label() {}

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
	public String toString() {
		return "Label [id=" + id + ", name=" + name + ", color=" + color + ", issue=" + issues + "]";
	}
	
}
