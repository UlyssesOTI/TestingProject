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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonView;

import ua.lviv.lgs.jsonview.Views;

@SuppressWarnings("serial")
@Entity
public class Answers implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "answer_id")
	@JsonView(Views.Public.class)
	private int id;
	@Column(name = "answer_text", nullable = false, unique = false)
	@JsonView(Views.Public.class)
	private String text;
	@Lob
	@Column(name = "answer_image")
	private byte[] image;
	@Column(name = "answer_status", nullable = false)
	private boolean status;
	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	private Questions question;
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "results_answers", joinColumns = @JoinColumn(name = "answers_id") , inverseJoinColumns = @JoinColumn(name = "results_id") )
	private List<Results> result;

	public Answers() {
	}

	public Answers(String text, byte[] image, boolean status, Questions question) {
		this.text = text;
		this.image = image;
		this.status = status;
		this.question = question;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Questions getQuestion() {
		return question;
	}

	public void setQuestion(Questions question) {
		this.question = question;
	}

	public List<Results> getResult() {
		return result;
	}

	public void setResult(List<Results> result) {
		this.result = result;
	}
	
}
