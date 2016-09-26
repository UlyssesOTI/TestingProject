package ua.lviv.lgs.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;

import ua.lviv.lgs.jsonview.Views;

@SuppressWarnings("serial")
@Embeddable
@Entity

public class Questions implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "question_id")
	@JsonView(Views.Public.class)
	private int id;
	@JsonView(Views.Public.class)
	@Column(name = "question_multipleAnswers")
	private boolean multipleAnswers = false;
	@Column(name = "question_text", nullable = false, unique = true)
	@JsonView(Views.Public.class)
	private String text;
	@Lob
	@Column(name = "question_image")
	private byte[] image;
	@Column(name = "question_grade", nullable = false)
	@JsonView(Views.Public.class)
	private int grade;
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private Themes theme;

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "question")
	@JsonView(Views.Public.class)
	private List<Answers> answers;
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	@JoinTable(name = "results_questions", joinColumns = @JoinColumn(name = "questions_id") , inverseJoinColumns = @JoinColumn(name = "results_id") )
	private List<Results> results;

	public Questions() {
	}

	public Questions(String text, int grade, boolean multipleAnswers) {
		this.text = text;
		this.grade = grade;
		this.multipleAnswers = multipleAnswers;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public List<Answers> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answers> answers) {
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "Questions [id=" + id + ", text=" + text + ", image=" + Arrays.toString(image) + ", grade=" + grade
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Questions other = (Questions) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public boolean isMultipleAnswers() {
		return multipleAnswers;
	}

	public void setMultipleAnswers(boolean multipleAnswers) {
		this.multipleAnswers = multipleAnswers;
	}

	public Themes getTheme() {
		return theme;
	}

	public void setTheme(Themes theme) {
		this.theme = theme;
	}

	public List<Results> getResults() {
		return results;
	}

	public void setResults(List<Results> results) {
		this.results = results;
	}

}
