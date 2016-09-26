package ua.lviv.lgs.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import ua.lviv.lgs.jsonview.Views;

public class TestResultViewDTO {
	
	@JsonView(Views.Public.class)
	public int id;
	
	@JsonView(Views.Public.class)
	public String questText;
	
	@JsonView(Views.Public.class)
	public int questGrade;
	
	@JsonView(Views.Public.class)
	public double questPoints;
	
	@JsonView(Views.Public.class)
	public String inputType;
	
	@JsonView(Views.Public.class)
	public List<List<String>> answersList;

	public TestResultViewDTO() {
		super();
	}

	public TestResultViewDTO(int id, double questPoints, String questText, String inputType,
			List<List<String>> answersList) {
		super();
		this.id = id;
		this.questPoints = questPoints;
		this.questText = questText;
		this.inputType = inputType;
		this.answersList = answersList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public double getQuestPoints() {
		return questPoints;
	}

	public void setQuestPoints(double questPoints) {
		this.questPoints = questPoints;
	}

	public String getQuestText() {
		return questText;
	}

	public void setQuestText(String questText) {
		this.questText = questText;
	}
	
	public int getQuestGrade() {
		return questGrade;
	}

	public void setQuestGrade(int questGrade) {
		this.questGrade = questGrade;
	}
	
	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public List<List<String>> getAnswersList() {
		return answersList;
	}

	public void setAnswersList(List<List<String>> answersList) {
		this.answersList = answersList;
	}

}