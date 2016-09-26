package ua.lviv.lgs.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ua.lviv.lgs.dto.QuestionDTO;
import ua.lviv.lgs.entity.Questions;
import ua.lviv.lgs.entity.Tests;
import ua.lviv.lgs.entity.Themes;

public interface QuestionsService {
	
	public final static int ITEMS_PER_PAGE = 10;
	
	Questions add(String text, MultipartFile picture, int themeId, int complexity, boolean isMultipleAnswers);
	
	Questions edit(Integer id, String text, MultipartFile file, int themeId, int complexity, boolean isMultipleAnswers);

	void delete(int id);

//	List<Questions> findAll();

	Questions findById(int id);

	void addQuestionsToGeneratedTest(int questionsGradeQuantity, /*int limitator,*/ int gradeLevel, Tests test,
			List<Themes> themes, List<Questions> questions);

	List<Questions> generateNewTest(int id, int clientId);
	
//	List<QuestionDTO> findAllQuestionsByThemeId(int id);

//	Questions findQuestionByText (String name);
	
	public List<QuestionDTO> findQuestionsByThemeAndPage(int themeId, int currentPage);
	
	public Integer findQuestionsCountByTheme(int themeId);
	
	public int calculatepageCount(int elementsCount, int elementperPage); 
}
