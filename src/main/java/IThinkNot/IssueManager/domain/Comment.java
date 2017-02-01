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
	@Column(name = "write_date", nullable = false, updatable = false)
	private Date writeDate;
	
	@Lob
	private String contents;
	
	
	public Comment() {}
	
	public Comment(Long id, User writer, Date writeDate, String contents) {
		super();
		this.id = id;
		this.writer = writer;
		this.writeDate = writeDate;
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
	public Date getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", writer=" + writer + ", writeDate=" + writeDate + ", contents=" + contents + "]";
	}
}
