package itn.issuemanager.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Issue {
	@Id
	@GeneratedValue
	private long id;
	@Column(length = 100, nullable = false)
	private String subject;
	@Lob
	private String contents;
	@Column(nullable = false)
	private Date creationDate;
	@ManyToOne
	private User writer;
	@ManyToOne
	private Milestone milestone;
	@OneToMany
	private List<Label> labels;
	@OneToMany
	private List<User> assines;
	@OneToMany
	private List<Comment> comments;
	
	public Issue() {}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public Milestone getMilestone() {
		return milestone;
	}

	public void setMilestone(Milestone milestone) {
		this.milestone = milestone;
	}

	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	public List<User> getAssines() {
		return assines;
	}

	public void setAssines(List<User> assines) {
		this.assines = assines;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Issue [id=" + id + ", subject=" + subject + ", contents=" + contents + ", creationDate=" + creationDate
				+ ", writer=" + writer + ", milestone=" + milestone + ", labels=" + labels + ", assines=" + assines
				+ ", comments=" + comments + "]";
	}
	
}
