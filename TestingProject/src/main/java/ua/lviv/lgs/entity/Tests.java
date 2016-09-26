package ua.lviv.lgs.entity;

import java.util.ArrayList;
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

@Entity
public class Tests {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "test_id")
	private int id;
	@Column(name = "test_name", nullable = false)
	private String name;
	@Column(name = "test_time", nullable = false)
	private int time;
	@Column(name = "test_high_grade_question_quantity")
	private int highGradeQuestionQuantity;
	@Column(name = "test_middle_grade_question_quantity")
	private int middleGradeQuestionQuantity;
	@Column(name = "test_low_grade_question_quantity")
	private int lowGradeQuestionQuantity;
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private Courses course;
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "tests_themes", joinColumns = @JoinColumn(name = "tests_id") , inverseJoinColumns = @JoinColumn(name = "themes_id") )
	private List<Themes> themes;
	
	@ManyToMany(cascade={CascadeType.MERGE, CascadeType.PERSIST}, fetch=FetchType.LAZY)
	@JoinTable(name="users_tests", joinColumns=@JoinColumn(name="tests_id"), inverseJoinColumns=@JoinColumn(name="users_id"))
	@Column(name = "Tests_UsersId", nullable = true)
	private List<Users> UsersId;

	public Tests() {
	}


	public List<Users> getUsersId() {
		return UsersId;
	}


	public void setUsersId(List<Users> usersId) {
		UsersId = usersId;
	}


	public Tests(String name, int time, int highGradeQuestionQuantity, int middleGradeQuestionQuantity,
			int lowGradeQuestionQuantity) {
		super();
		this.name = name;
		this.time = time;
		this.highGradeQuestionQuantity = highGradeQuestionQuantity;
		this.middleGradeQuestionQuantity = middleGradeQuestionQuantity;
		this.lowGradeQuestionQuantity = lowGradeQuestionQuantity;
		themes = new ArrayList<Themes>();
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

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getHighGradeQuestionQuantity() {
		return highGradeQuestionQuantity;
	}

	public void setHighGradeQuestionQuantity(int highGradeQuestionQuantity) {
		this.highGradeQuestionQuantity = highGradeQuestionQuantity;
	}

	public int getMiddleGradeQuestionQuantity() {
		return middleGradeQuestionQuantity;
	}

	public void setMiddleGradeQuestionQuantity(int middleGradeQuestionQuantity) {
		this.middleGradeQuestionQuantity = middleGradeQuestionQuantity;
	}

	public int getLowGradeQuestionQuantity() {
		return lowGradeQuestionQuantity;
	}

	public void setLowGradeQuestionQuantity(int lowGradeQuestionQuantity) {
		this.lowGradeQuestionQuantity = lowGradeQuestionQuantity;
	}

	public Courses getCourse() {
		return course;
	}

	public void setCourse(Courses course) {
		this.course = course;
	}


	public List<Themes> getThemes() {
		return themes;
	}


	public void setThemes(List<Themes> themes) {
		this.themes = themes;
	}
	public void setTheme(Themes theme) {
		themes.add(theme);
	}
}
