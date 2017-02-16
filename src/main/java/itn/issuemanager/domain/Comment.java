package itn.issuemanager.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import itn.issuemanager.utils.DateTimeUtils;

@Entity
public class Comment {
	private static final Logger log = LoggerFactory.getLogger(Comment.class);
	
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
	@Column
	private Date updateDate;
	@Lob
	private String contents;
	@ManyToMany(cascade=CascadeType.REMOVE)
	private List<UploadFile> files;

	public Comment() {
		files = new ArrayList<UploadFile>();
	}

	public Comment(Comment paramComment, User writer, Issue issue) {
		this.writer = writer;
		this.creationDate = new Date();
		this.contents = paramComment.contents;
		this.issue = issue;
		files = new ArrayList<UploadFile>();
	}
	
	public void addFile(UploadFile file) {
		this.files.add(file);
	}
	
	public List<UploadFile> getFiles() {
		return files;
	}

	public void setFiles(List<UploadFile> files) {
		this.files = files;
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

	public void update(Comment comment){
		this.contents = comment.contents;
		this.updateDate = new Date();
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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
        return "Comment [id=" + id + ", issue=" + issue + ", writer=" + writer + ", creationDate=" + creationDate
                + ", updateDate=" + updateDate + ", contents=" + contents + ", files=" + files + "]";
    }

    public boolean isSameWriter(User user) {
		log.debug("isSameWriter : {}", user.toString());
		return user.isSameUser(this.writer);
	}
}
