package ua.lviv.lgs.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.lviv.lgs.dao.CoursesDao;
import ua.lviv.lgs.dao.QuestionsDao;
import ua.lviv.lgs.dao.TestsDao;
import ua.lviv.lgs.dao.ThemesDao;
import ua.lviv.lgs.dao.UsersDao;
import ua.lviv.lgs.dto.TestDTO;
import ua.lviv.lgs.dto.ThemeDTO;
import ua.lviv.lgs.entity.Tests;
import ua.lviv.lgs.entity.Themes;
import ua.lviv.lgs.entity.Users;
import ua.lviv.lgs.service.TestsService;
import ua.lviv.lgs.service.UsersService;

@Service
public class TestsServiceImpl implements TestsService {

	@Autowired
	private TestsDao testsDao;
	@Autowired
	private ThemesDao themesDao;
	@Autowired
	private UsersDao usersDao;
	@Autowired
	private UsersService userSevice;
	@Autowired
	private CoursesDao coursesDao;
	@Autowired
	private QuestionsDao questionsDao;
	// Logger for TestsServiceImpl Class
	private static Logger log = Logger.getLogger(TestsServiceImpl.class);


	@Transactional
	public void add(String name, int time, int highGradeQuestionQuantity, int middleGradeQuestionQuantity,
			int lowGradeQuestionQuantity, int courseId, String themeIdArray) {
		Tests test;
		if (name != null) {
			test = new Tests(name, time, highGradeQuestionQuantity, middleGradeQuestionQuantity,
					lowGradeQuestionQuantity);
			test.setCourse(coursesDao.findOne(courseId));
			if (themeIdArray != null) {
				String[] themes = themeIdArray.split(",");
				for (String theme : themes) {
					int themeId = Integer.parseInt(theme);
					Themes findedTheme = themesDao.findOne(themeId);
					test.setTheme(findedTheme);
				}
			}
			testsDao.save(test);
		}
	}

	@Transactional
	public void edit(int id, String name, int time, int highGradeQuestionQuantity, int middleGradeQuestionQuantity,
			int lowGradeQuestionQuantity, int courseId, String themeIdArray) {
		Tests test = testsDao.findOne(id);
		if (name != null) {
			test.setName(name);
		}
		if (time > 0) {
			test.setTime(time);
		}
		if (highGradeQuestionQuantity >= 0) {
			test.setHighGradeQuestionQuantity(highGradeQuestionQuantity);
		}
		if (middleGradeQuestionQuantity >= 0) {
			test.setMiddleGradeQuestionQuantity(middleGradeQuestionQuantity);
		}
		if (lowGradeQuestionQuantity >= 0) {
			test.setLowGradeQuestionQuantity(lowGradeQuestionQuantity);
		}
		if (courseId > 0) {
			test.setCourse(coursesDao.findOne(courseId));
		}
		if (themeIdArray != null) {
			String[] temp = themeIdArray.split(",");
			List<Themes> themes = new ArrayList<Themes>();
			for (String string : temp) {
				// int themeId = Integer.parseInt(string);
				// if (themeId > 0) {
				// Themes theme = themesDao.findOne(themeId);
				// themes.add(theme);
				// }
				themes.add(themesDao.findOne(Integer.parseInt(string)));
			}
			test.setThemes(themes);
		}
		testsDao.save(test);
	}

	@Transactional
	public boolean questionQuntityValidation(String name, int time, int highGradeQuestionQuantity,
			int middleGradeQuestionQuantity, int lowGradeQuestionQuantity, int courseId, String themeIdArray) {
		int easy = 0;
		int medium = 0;
		int difficult = 0;
		String[] temp = themeIdArray.split(",");
		for (String string : temp) {
			List<Integer> questionsComplexity = questionsDao.findQuestionsComplexityByThemeId(Integer.parseInt(string));
			for (Integer complexity : questionsComplexity) {
				if (complexity == 1) {
					easy++;
				} else if (complexity == 2) {
					medium++;
				} else {
					difficult++;
				}
			}
		}

		if (lowGradeQuestionQuantity > easy || middleGradeQuestionQuantity > medium
				|| highGradeQuestionQuantity > difficult) {
			return true;
		}
		return false;
	}

	@Transactional
	public void delete(int id) {
		Tests test = testsDao.findOne(id);
		testsDao.delete(test);
		log.info("[INFO] deleted test with id="+id);
	}

//	@Transactional
//	public List<TestDTO> findAll() {
//		List<Tests> tests = testsDao.findAll();
//		List<TestDTO> testsDTO = new ArrayList<TestDTO>();
//		for (Tests test : tests) {
//			TestDTO testDTO = new TestDTO(test.getId(), test.getName(), test.getCourse().getId(),
//					test.getCourse().getName());
//			testsDTO.add(testDTO);
//		}
//		return testsDTO;
//	}

	@Transactional
	public List<TestDTO> findTestsByCourses(int id) {
		List<Tests> tests = coursesDao.findOne(id).getTests();
		List<TestDTO> courseTests = new ArrayList<TestDTO>();
		for (Tests test : tests) {
			TestDTO testDTO = new TestDTO(test.getId(), test.getName(), id, coursesDao.findOne(id).getName());
			courseTests.add(testDTO);
		}
		return courseTests;
	}

	@Transactional
	public TestDTO findDTOById(int id) {
		try {
			Tests test = testsDao.findOne(id);
			TestDTO testDTO = new TestDTO(id, test.getName(), test.getTime(), test.getHighGradeQuestionQuantity(),
					test.getMiddleGradeQuestionQuantity(), test.getLowGradeQuestionQuantity(), test.getCourse().getId(),
					test.getCourse().getName());
			return testDTO;
		} catch (Exception e) {
			return null;
		}
	}

//	@Transactional
//	public Tests findById(int id) {
//		Tests test = testsDao.findOne(id);
//		return test;
//	}

	@Transactional
	public void removeTestsFromStudent(int userId, int testId) {
		List<Tests> userTests = userSevice.findUserById(userId).getTests();
		Tests currentTest = testsDao.findOne(testId);
		if (userTests.contains(currentTest)) {
			userTests.remove(currentTest);
		}
	}

	@Transactional
	public List<Tests> findtestsByCourse(int courseId) {
		return testsDao.findAllTestsByCourse(courseId);
	}

	@Transactional
	public int findTestIdByName(String name) {
		return testsDao.findTestIdByName(name);
	}

	@Transactional
	public List<ThemeDTO> findTestThemesByTestId(int testId) {
		Tests test = testsDao.findOne(testId);
		List<Themes> themes = test.getThemes();
		List<ThemeDTO> themesDTO = new ArrayList<ThemeDTO>();
		for (Themes theme : themes) {
			ThemeDTO themeDTO = new ThemeDTO(theme.getId(), theme.getName());
			themesDTO.add(themeDTO);
		}
		return themesDTO;
	}

	@Transactional
	public void setTestsToStudents(String inputString, String test) {
		List<Users> searchedList = new ArrayList<Users>();
		if (inputString.length() >= 1) {
			String[] splitedInputString = inputString.split(",");
			for (String string : splitedInputString) {
				searchedList.add(usersDao.findOne(Integer.parseInt(string)));
			}
		}

		int testId = Integer.parseInt(test);
		Tests testingTest = testsDao.findOne(testId);
		log.info("[INFO] Setting test with id="+test+" for users with id="+inputString+"]");
		for (Users students : searchedList) {
			List<Tests> temproraryTestList = students.getTests();
			if (!temproraryTestList.contains(testingTest)) {
				students.setTest(testsDao.findOne(testId));
				usersDao.save(students);
			}

		}
	}


}
