package ua.lviv.lgs.service;

import java.util.List;

import ua.lviv.lgs.dto.TestDTO;
import ua.lviv.lgs.dto.ThemeDTO;
import ua.lviv.lgs.entity.Tests;

public interface TestsService {
	void add(String name, int time, int highGradeQuestionQuantity, int middleGradeQuestionQuantity,
			int lowGradeQuestionQuantity, int courseId, String themeIdArray);

	void edit(int id, String name, int time, int highGradeQuestionQuantity, int middleGradeQuestionQuantity,
			int lowGradeQuestionQuantity, int courseId, String themeIdArray);

	void delete(int id);

//	List<TestDTO> findAll();

	TestDTO findDTOById(int id);
	
//	Tests findById(int id);

	List<TestDTO> findTestsByCourses(int courseId);

	List<Tests> findtestsByCourse(int courseId);

	boolean questionQuntityValidation(String name, int time, int highGradeQuestionQuantity, int middleGradeQuestionQuantity,
			int lowGradeQuestionQuantity, int courseId, String themeIdArray);

	int findTestIdByName(String name);

	List<ThemeDTO> findTestThemesByTestId(int id);

	void removeTestsFromStudent(int userId, int testId);

	void setTestsToStudents(String inputString, String test);

	

}
