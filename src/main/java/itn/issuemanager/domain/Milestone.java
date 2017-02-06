package itn.issuemanager.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Milestone {

	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "subject", nullable = false)
	private String subject;
	@Temporal(TemporalType.DATE)
	@Column(name = "start_date", nullable = false)
	private Date startDate;
	@Temporal(TemporalType.DATE)
	@Column(name = "end_date", nullable = false)
	private Date endDate;
	@OneToMany(mappedBy="milestone")
	private List<Issue> issues;
	
	private int openIssue;
	private int closeIssue;
	
	public Milestone(){}
	
	public Milestone(String subject, Date startDate,Date endDate) {
		super();
		this.subject=subject;
		this.startDate=startDate;
		this.endDate=endDate;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public void setIssue(List<Issue> issue) {
		this.issues = issue;
		for(Issue i : issue){
			
		}
	}
	
	public Long getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	
	@JsonIgnore
	public List<Issue> getIssues() {
		return issues;
	}

	public void update(String subject,Date sdate,Date edate){
		this.subject = subject;
		this.startDate = sdate;
		this.endDate = edate;
	}
	
	@Override
	public String toString() {
		return "Milestone [id=" + id + ", subject=" + subject + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", issue=" + issues + "]";
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
		Milestone other = (Milestone) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
