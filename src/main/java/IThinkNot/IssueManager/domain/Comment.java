package IThinkNot.IssueManager.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Comment {
	
	@Id	
	@GeneratedValue
	private Long id;
	@ManyToOne	
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
	private User writer;
	@Temporal(TemporalType.TIMESTAMP)	
	@Column(name = "creation_Date", nullable = false, updatable = false)
	private Date creationDate;
	@Lob
	private String contents;

	public Comment() {}
	
	public Comment(Long id, User writer, Date creationDate, String contents) {
		super();
		this.id = id;
		this.writer = writer;
		this.creationDate = creationDate;
		this.contents = contents;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getWriter() {
		return writer;
	}
	public void setWriter(User writer) {
		this.writer = writer;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", writer=" + writer + ", creationDate=" + creationDate + ", contents=" + contents + "]";
	}
}
