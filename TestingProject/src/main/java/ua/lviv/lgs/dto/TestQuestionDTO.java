package ua.lviv.lgs.dto;

import java.io.Serializable;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonView;

import ua.lviv.lgs.entity.Questions;
import ua.lviv.lgs.jsonview.Views;

public class TestQuestionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonView(Views.Public.class)
	private int numberInList;
	@JsonView(Views.Public.class)
	private Questions question;
	@JsonView(Views.Public.class)
	private int[] checkedAnswersId;

	public TestQuestionDTO(Questions question, int numberInList) {
		this.question = question;
		this.numberInList = numberInList;
		checkedAnswersId = new int[question.getAnswers().size()];
	}

	public int getNumberInList() {
		return numberInList;
	}

	public void setNumberInList(int numberInList) {
		this.numberInList = numberInList;
	}

	public Questions getQuestion() {
		return question;
	}

	public void setQuestion(Questions question) {
		this.question = question;
	}

	public int[] getCheckedAnswersId() {
		return checkedAnswersId;
	}

	public void setCheckedAnswersId(int[] checkedAnswersId) {
		this.checkedAnswersId = checkedAnswersId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

	@Override
	public String toString() {
		return "TestQuestion [questions=" + question + ", checkedAnswersId=" + Arrays.toString(checkedAnswersId) + "]";
	}

}
