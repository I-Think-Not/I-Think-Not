package IThinkNot.IssueManager.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

public class Label {

	@Id
	@GeneratedValue
	private Long id;
	@Column
	private String name;
	@Column
	private String color;
	@OneToMany(mappedBy="label")
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_label_to_issue"))
	private List<Issue> issue;

	public Label() {}

	public Label(Long id, String name, String color, List<Issue> issue) {
		super();
		this.id = id;
		this.name = name;
		this.color = color;
		this.issue = issue;
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

	public List<Issue> getIssue() {
		return issue;
	}

	public void setIssue(List<Issue> issue) {
		this.issue = issue;
	}

	
	@Override
	public String toString() {
		return "Label [id=" + id + ", name=" + name + ", color=" + color + ", issue=" + issue + "]";
	}
	
}
