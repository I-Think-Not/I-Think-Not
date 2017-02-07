package itn.issuemanager.domain;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.slipp.utils.DateTimeUtils;

@Entity
public class Comment {

	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_comment_to_issue"))
	@JsonIgnore
	private Issue issue;
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_comment_writer"))
	private User writer;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_Date", nullable = false, updatable = false)
	private Date creationDate;
	@Lob
	private String contents;

	public Comment() {
	}

	public Comment(Comment paramComment, User writer, Issue issue) {
		this.id  = paramComment.id;
		this.writer = writer;
		this.creationDate = new Date();
		this.contents = paramComment.contents;
		this.issue = issue;
	}
	
	public Comment(Long id, User writer, String contents) {
		this.id = id;
		this.writer = writer;
		this.creationDate = new Date();
		this.contents = contents;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public void setContents(String contents) {
		this.contents = contents;
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
	
	public String getFormattedCreationDate() {
		return DateTimeUtils.format(creationDate, "yyyy.MM.dd HH:mm:ss");
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
		Comment other = (Comment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Comment [id=" + id + ", writer=" + writer + ", creationDate=" + creationDate + ", contents=" + contents
				+ "]";
	}
}
