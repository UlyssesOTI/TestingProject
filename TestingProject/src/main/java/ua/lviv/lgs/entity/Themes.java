package ua.lviv.lgs.entity;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;

import ua.lviv.lgs.jsonview.Views;

@SuppressWarnings("serial")
@Entity
public class Themes implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "theme_id")
	@JsonView(Views.Public.class)
	private int id;
	
	@Column(name = "theme_name", nullable = false, unique = true)
	@JsonView(Views.Public.class)
	private String name;
	
	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "theme", orphanRemoval = true)
	private List<Questions> questions;
	
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name="tests_themes", joinColumns=@JoinColumn(name="themes_id"), inverseJoinColumns=@JoinColumn(name="tests_id"))
	private List<Tests> tests;
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private Courses course;

	public Themes() {
		super();
	}

	public Themes(String name) {
		this.name = name;
	}

	public Courses getCourse() {
		return course;
	}

	public void setCourse(Courses course) {
		this.course = course;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Questions> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Questions> questions) {
		this.questions = questions;
	}

	public List<Tests> getTests() {
		return tests;
	}

	public void setTests(List<Tests> tests) {
		this.tests = tests;
	}
	
}
