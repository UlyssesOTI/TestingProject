package ua.lviv.lgs.dto;

import com.fasterxml.jackson.annotation.JsonView;

import ua.lviv.lgs.jsonview.Views;

public class QuestionDTO {
	
	@JsonView(Views.Public.class)
	int id;
	
	@JsonView(Views.Public.class)
	String text;
	
	String[] questionAnswers;
	
	public QuestionDTO(int id, String questionText) {
		super();
		this.id = id;
		this.text = questionText;
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

	public String[] getQuestionAnswers() {
		return questionAnswers;
	}

	public void setQuestionAnswers(String[] questionAnswers) {
		this.questionAnswers = questionAnswers;
	}
}
