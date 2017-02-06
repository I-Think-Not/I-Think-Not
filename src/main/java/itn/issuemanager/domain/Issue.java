package itn.issuemanager.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.aspectj.apache.bcel.generic.Type;

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
	@Column
	private Date updateDate;
	@Enumerated(EnumType.STRING)
	private IssueState state;
	@ManyToOne
	private User writer;
	@ManyToOne
	private Milestone milestone;
	@ManyToMany
	private List<Label> labels;
	@OneToMany
	private List<User> assines;
	@OneToMany
	private List<Comment> comments;
	
	public Issue() {}
	
	public Issue(String subject, String contents, User writer) {
		this.writer = writer;
		this.subject = subject;
		this.contents = contents;
		this.creationDate = new Date();
		this.updateDate = this.creationDate;
		this.state = IssueState.OPEN;
	}

	public boolean isState() {
		return state == IssueState.CLOSED;
	}

	public void update(String subject, String contents) {
		this.subject = subject;
		this.contents = contents;
		this.updateDate = new Date();
	}
	
	public void closeIssue(){
		this.state = IssueState.CLOSED;		
	}
	
	public void reopenIssue(){
		this.state = IssueState.OPEN;		
	}
	
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
	
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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

	public void setLabels(Label labels) {
		this.labels.add(labels);
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

	public void setComments(Comment comments) {
		this.comments.add(comments);
	}

	@Override
	public String toString() {
		return "Issue [id=" + id + ", subject=" + subject + ", contents=" + contents + ", creationDate=" + creationDate
				+ ", writer=" + writer + ", milestone=" + milestone + ", labels=" + labels + ", assines=" + assines
				+ ", comments=" + comments + "]";
	}
	
}
