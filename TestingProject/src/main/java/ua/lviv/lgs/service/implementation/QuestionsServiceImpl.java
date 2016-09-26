package ua.lviv.lgs.service.implementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ua.lviv.lgs.dao.QuestionsDao;
import ua.lviv.lgs.dao.TestsDao;
import ua.lviv.lgs.dao.ThemesDao;
import ua.lviv.lgs.dao.UsersDao;
import ua.lviv.lgs.dto.QuestionDTO;
import ua.lviv.lgs.entity.Questions;
import ua.lviv.lgs.entity.Tests;
import ua.lviv.lgs.entity.Themes;
import ua.lviv.lgs.service.QuestionsService;

@Service
public class QuestionsServiceImpl implements QuestionsService {

	@Autowired
	private QuestionsDao questionsDao;

	@Autowired
	private TestsDao testsDao;

	@Autowired
	private ThemesDao themesDao;

	@Autowired
	private UsersDao usersDao;

	@Transactional
	public Questions add(String text, MultipartFile picture, int themeId, int grade, boolean isMultipleAnswers) {
		// the new one
		Questions question = new Questions(text, grade, isMultipleAnswers);

		question.setTheme(themesDao.findOne(Integer.valueOf(themeId)));
		if (picture != null) {
			try {
				question.setImage(picture.getBytes());
			} catch (IOException e) {
				question.setImage(new byte[1]);
			}
		}
		questionsDao.save(question);
		return question;
	}

	private QuestionDTO questionToDTO(Questions inpuQuestion) {
		QuestionDTO outputQuestion = new QuestionDTO(inpuQuestion.getId(), inpuQuestion.getText());
		return outputQuestion;
	}

	@Transactional
	public Questions edit(Integer id, String text, MultipartFile file, int themeId, int grade,
			boolean isMultipleAnswers) {
		Questions question = findById(id);

		question.setText(text);

		if (file != null) {
			try {
				question.setImage(file.getBytes());
			} catch (IOException e) {
				question.setImage(new byte[1]);
			}
		}
		question.setTheme(themesDao.findOne(Integer.valueOf(themeId)));
		question.setGrade(grade);
		question.setMultipleAnswers(isMultipleAnswers);
		questionsDao.save(question);
		return question;
	}

	@Transactional
	public void delete(int id) {
		questionsDao.delete(id);
	}

	// @Transactional
	// public List<Questions> findAll() {
	// return questionsDao.findAll();
	// }

	// @Transactional
	// public List<QuestionDTO> findAllQuestionsByThemeId(int id) {
	// Themes theme = themesDao.findOne(id);
	// List<Questions> questions = theme.getQuestions();
	// List<QuestionDTO> questionsDTO = new ArrayList<QuestionDTO>();
	// for (Questions question : questions) {
	// QuestionDTO questionDTO = questionToDTO(question);
	// questionsDTO.add(questionDTO);
	// }
	// return questionsDTO;
	//
	// }

	@Transactional
	public Questions findById(int id) {
		return questionsDao.findOne(id);
	}

	@Transactional
	public void addQuestionsToGeneratedTest(int questionsGradeQuantity, /*int limitator,*/ int gradeLevel, Tests test,
			List<Themes> themes, List<Questions> questions) {
		int limitator=0;
		for (; limitator < questionsGradeQuantity; ) {
			for (Themes theme : themes) {
				List<Questions> tempList = themesDao.findAllQuestionsFromThemeByGrade(theme.getId(), gradeLevel);
				System.out.println("teme "+theme.getName());
				System.out.println(""+questionsGradeQuantity+" questions by theme by grade="+gradeLevel);
				System.out.println(tempList);				
				
				Random r = new Random();
				if(tempList.size() == 0) {
					System.out.println("continue");
					continue;
				}				
				
				Questions quest = questionsDao.findOne(tempList.get(r.nextInt(tempList.size())).getId());
				if (!questions.contains(quest)) {
					System.out.println("genereted question");
					System.out.println(quest);
					questions.add(quest);
					limitator++;
				}
				
				if (limitator == questionsGradeQuantity) {
					System.out.println("break");
					break ;
				}
			}
		}
	}

	@Transactional
	public List<Questions> generateNewTest(int id, int clientId) {
		List<Tests> usersTests = usersDao.findOne(clientId).getTests();
		if (usersTests.contains(testsDao.findOne(id))) {
			Tests test = testsDao.findOne(id);
			List<Themes> themes = themesDao.findAllThemesByTestsId(test.getId());
			List<Questions> questions = new ArrayList<Questions>();
//			int highGradeQuestionsLimitator = 0;
//			int middleGradeQuestionsLimitator = 0;
//			int lowGradeQuestionsLimitator = 0;
			addQuestionsToGeneratedTest(test.getHighGradeQuestionQuantity(), /*highGradeQuestionsLimitator, */3, test,
					themes, questions);
			addQuestionsToGeneratedTest(test.getMiddleGradeQuestionQuantity(), /*middleGradeQuestionsLimitator,*/ 2, test,
					themes, questions);
			addQuestionsToGeneratedTest(test.getLowGradeQuestionQuantity(), /*lowGradeQuestionsLimitator,*/ 1, test, themes,
					questions);
			return questions;
		}
		return null;
	}

	// @Transactional
	// public Questions findQuestionByText(String name) {
	// return questionsDao.findQuestionByText(name);
	// }

	@Transactional
	public List<QuestionDTO> findQuestionsByThemeAndPage(int themeId, int currentPage) {
		List<Questions> questions = questionsDao.findAllByCoursePaginated(themeId,
				new PageRequest(currentPage, ITEMS_PER_PAGE));
		List<QuestionDTO> outputList = new ArrayList<QuestionDTO>();
		for (Questions questions2 : questions) {
			QuestionDTO dtoQuestion = questionToDTO(questions2);
			outputList.add(dtoQuestion);
		}
		return outputList;
	}

	@Transactional
	public Integer findQuestionsCountByTheme(int themeId) {
		return questionsDao.findQuestionCoutnByTheme(themeId);
	}

	public int calculatepageCount(int elementsCount, int elementperPage) {
		int result;
		if (elementsCount % elementperPage == 0) {
			result = elementsCount / elementperPage;
		} else {
			result = (elementsCount / elementperPage) + 1;
		}
		return result;
	}

}
