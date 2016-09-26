package ua.lviv.lgs.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Results {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "result_id")
	private int id;
	@Column(name = "result_mark", nullable = false)
	private double mark;
	@Column(name = "result_comment")
	private String comment;
	@Column(name = "result_user", nullable = false)
	private int user;
	@Column(name = "result_test", nullable = false)
	private int test;
	@ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "results_answers", joinColumns = @JoinColumn(name = "results_id") , inverseJoinColumns = @JoinColumn(name = "answers_id") )
	private List<Answers> answers;
	@ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "results_questions", joinColumns = @JoinColumn(name = "results_id") , inverseJoinColumns = @JoinColumn(name = "questions_id") )
	private List<Questions> questions;

	public Results() {
	}

	public Results(double mark, String comment, int user, int test) {
		this.mark = mark;
		this.comment = comment;
		this.user = user;
		this.test = test;
	}
	
	public Results(double mark, String comment, int user, int test, List<Answers> answers, List<Questions> questions) {
		super();
		this.mark = mark;
		this.comment = comment;
		this.user = user;
		this.test = test;
		this.answers = answers;
		this.questions = questions;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getMark() {
		return mark;
	}

	public void setMark(double mark) {
		this.mark = mark;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public int getTest() {
		return test;
	}

	public void setTest(int test) {
		this.test = test;
	}

	public List<Answers> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answers> answers) {
		this.answers = answers;
	}

	public List<Questions> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Questions> questions) {
		this.questions = questions;
	}

}
