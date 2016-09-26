package ua.lviv.lgs.service;

import java.util.List;

import ua.lviv.lgs.dto.ResultsDTO;
import ua.lviv.lgs.dto.TestQuestionDTO;
import ua.lviv.lgs.dto.TestResultViewDTO;
import ua.lviv.lgs.entity.Answers;
import ua.lviv.lgs.entity.Questions;
import ua.lviv.lgs.entity.Results;

public interface ResultsService {
	
//	void add(double mark, String comment, Integer userId, int testId);

	void add(double mark, String comment, Integer userId, Integer testId, List<Questions> questions, List<Answers> answers);

//	void edit(int id, Integer mark, String comment, Integer userId, Integer testId);

	void delete(int id);

//	List<Results> findAll();

	Results findById(int id);
	
	public List<ResultsDTO> findAllByUserId(String studentId, String principalId);
	
	public String saveComment(int resultId,String principalId,String comment);
	
	public double calculateTestResult(List<TestQuestionDTO> testResults);

	public List<TestResultViewDTO> userTestResultView(String testId,String principalId);
	
}
