package itn.issuemanager.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
//TODO 사용하지 않는 import 제거한다.
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

// TODO 사용하지 않는 import 제거한다.
import org.aspectj.apache.bcel.generic.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import itn.issuemanager.controller.LabelController;

@Entity
public class Issue {

    private static final Logger log = LoggerFactory.getLogger(Issue.class);
    @Id
    @GeneratedValue
    private Long id;
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
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Label> labels;

    @OneToMany
    private List<User> assignee;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    public Issue() {
    }

    public Issue(String subject, String contents, User writer) {
        this.writer = writer;
        this.subject = subject;
        this.contents = contents;
        this.creationDate = new Date();
        this.updateDate = this.creationDate;
        this.state = IssueState.OPEN;
    }

    public boolean isClosed() {
        return state == IssueState.CLOSED;
    }

    public void update(String subject, String contents) {
        this.subject = subject;
        this.contents = contents;
        this.updateDate = new Date();
    }

    public void closeIssue() {
        this.state = IssueState.CLOSED;
    }

    public void reopenIssue() {
        this.state = IssueState.OPEN;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    // 에러처리 문장 수정해야함
    public void addLabel(Label labels) throws Exception {
        if (this.getLabels().contains(labels)) {
            throw new Exception("already exists label");
        }
        this.labels.add(labels);
    }

    // 에러처리 문장 수정해야함
    public void addAssignee(User assignee) throws Exception {
        if (this.getAssignee().contains(assignee)) {
            throw new Exception("already exists Assignee");
        }
        this.assignee.add(assignee);
    }

    public List<User> getAssignee() {
        return assignee;
    }

    public void setAssignee(List<User> assignee) {
        this.assignee = assignee;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public int getCommentCounter() {
        return this.comments.size();
    }

    public void setComments(Comment comments) {
        this.comments.add(comments);
    }

    @Override
    public String toString() {
        return "Issue [id=" + id + ", subject=" + subject + ", contents=" + contents + ", creationDate=" + creationDate
                + ", updateDate=" + updateDate + ", state=" + state + ", writer=" + writer + ", milestone=" + milestone
                + ", labels=" + labels + ", assignee=" + assignee + "]";
    }

    public boolean removeLabel(Label label) {
        this.labels.remove(label);
        log.debug("labels : {}", this.labels);
        return true;
    }

}
