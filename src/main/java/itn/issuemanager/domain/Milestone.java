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


@Entity
public class Milestone {

	@Id
	@GeneratedValue
	private long id;
	@Column(name = "subject", nullable = false, updatable = false)
	private String subject;
	@Column(name = "start_date", nullable = false, updatable = false)
	private Date startDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", nullable = false, updatable = false)
	private Date endDate;
	@OneToMany
	private List<Issue> issues;
	
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
	}

	@Override
	public String toString() {
		return "Milestone [id=" + id + ", subject=" + subject + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", issue=" + issues + "]";
	}

}
