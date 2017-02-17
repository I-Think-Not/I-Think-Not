package itn.issuemanager.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import itn.issuemanager.controller.LabelController;
import itn.issuemanager.utils.DateTimeUtils;

@Entity
public class Milestone {
	private static final Logger log = LoggerFactory.getLogger(LabelController.class);
	
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "subject", nullable = false)
	private String subject;
	@Column(name = "start_date", nullable = false)
	private Date startDate;
	@Column(name = "end_date", nullable = false)
	private Date endDate;
	@OneToMany(mappedBy="milestone")
	private List<Issue> issues;
	
	private int progressRate;
	private int openIssueCount;
	private int closeIssueCount;

	public Milestone(){
		this.openIssueCount = 0;
		this.closeIssueCount = 0;
		this.progressRate = 0;
	}
	
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
	
	// TODO 다음 메소드의 내용을 IssueStatus와 같은 새로운 클래스를 추가해 구현한 후 단위 테스트 코드를 추가해 본다.
	public void countIssueState(){
		this.closeIssueCount = 0;
		this.openIssueCount = 0;
		for(Issue i : this.issues){
			if(i.isClosed()){
				this.closeIssueCount++;
			}else if(!i.isClosed()){
				this.openIssueCount++;
			}
		}
		if(closeIssueCount!=0)
		progressRate=((closeIssueCount)*100/(openIssueCount+closeIssueCount));
		log.debug("progressRate:"+progressRate);
		log.debug("setIssue"+this.openIssueCount);
	}
	
	public Long getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public String getStartDate() {
		return DateTimeUtils.format(this.startDate, "yyyy-MM-dd");
	}

	public String getEndDate() {
		return DateTimeUtils.format(this.endDate, "yyyy-MM-dd");
	}
	
	public int getOpenIssueCount() {
		return openIssueCount;
	}

	public int getCloseIssueCount() {
		return closeIssueCount;
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
				+"]";
	}
	
	public int getProgressRate() {
		return progressRate;
	}

	public static Logger getLog() {
		return log;
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
