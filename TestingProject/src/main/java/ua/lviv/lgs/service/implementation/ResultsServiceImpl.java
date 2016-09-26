package ua.lviv.lgs.service.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.lviv.lgs.dao.AnswersDao;
import ua.lviv.lgs.dao.QuestionsDao;
import ua.lviv.lgs.dao.ResultDao;
import ua.lviv.lgs.dao.TestsDao;
import ua.lviv.lgs.dao.UsersDao;
import ua.lviv.lgs.dto.ResultsDTO;
import ua.lviv.lgs.dto.TestQuestionDTO;
import ua.lviv.lgs.dto.TestResultViewDTO;
import ua.lviv.lgs.entity.Answers;
import ua.lviv.lgs.entity.Questions;
import ua.lviv.lgs.entity.Results;
import ua.lviv.lgs.service.ResultsService;

@Service
public class ResultsServiceImpl implements ResultsService {

	@Autowired
	private ResultDao resultDao;
	@Autowired
	private TestsDao testsDao;
	@Autowired
	UsersDao usersDao;
	@Autowired
	private QuestionsDao questionsDao;
	@Autowired
	private AnswersDao answersDao;
	
	@Transactional
	public void add(int mark, String comment, int userId, int testId) {
		Results result = new Results(mark, comment, userId, testId);
		resultDao.save(result);
	}

//	@Transactional
//	public void edit(int id, Integer mark, String comment, Integer userId, Integer testId) {
//		Results result = findById(id);
//		if (mark != null) {
//			result.setMark(mark);
//		}
//		if (comment != null) {
//			result.setComment(comment);
//		}
//		if (userId != null) {
//			result.setUser(userId);
//		}
//		if (testId != null) {
//			result.setTest(testId);
//		}
//		resultDao.save(result);
//	}

	@Transactional
	public void delete(int id) {
		Results result = findById(id);
		resultDao.delete(result);
	}

//	@Transactional
//	public List<Results> findAll() {
//		return resultDao.findAll();
//	}

	@Transactional
	public Results findById(int id) {
		return resultDao.findOne(id);
	}
	
//	@Transactional
//	public void add(double mark, String comment, Integer userId, int testId) {
//		Results result = new Results(mark, comment, userId, testId);
//		resultDao.save(result);
//	}
	
	@Transactional
	public void add(double mark, String comment, Integer userId, Integer testId, List<Questions> questions,
			List<Answers> answers) {
		Results result = new Results(mark, comment, userId, testId, answers, questions);
		resultDao.save(result);
	}
	
	private Integer getRoleid(String principalId){
		Integer principalRole = null;
		if(principalId.equals("admin")){
			principalRole = 0;
		}else{
			Integer id = parseInt(principalId);
			if(id!=null){
				try {
					principalRole = usersDao.findOne(id).getRole().getId();
				} catch (IllegalArgumentException e) {
					System.out.println(e);
				}
			}
		}
		return principalRole;
	}
	
	@Transactional
	public List<ResultsDTO> findAllByUserId(String studentId, String principalId){
		List<Results> results = new ArrayList<Results>();
		Integer principalRole =  getRoleid(principalId);
		Integer studentIdInt =	parseInt(studentId);
		if(!(principalRole == null) && studentIdInt!= null){
			if(principalRole == 2){
				results = resultDao.findAllByUserIdAndTeacherId(studentIdInt,Integer.parseInt(principalId));
			}else if(principalRole == 3){
				if(studentId.equals(principalId)){
					results = resultDao.findAllByUserId(studentIdInt);
				}				
			}else{
				results = resultDao.findAllByUserId(studentIdInt);
			}
		}

		List<ResultsDTO> resultsDTOs = new ArrayList<ResultsDTO>();
		for (Results result : results) {
			resultsDTOs.add(
					new ResultsDTO(
							""+result.getId(), 
							testsDao.findOne(result.getTest()).getName(),
							""+result.getMark()
							)
					);
		}
		return resultsDTOs;
	}

	@Transactional
	public String saveComment(int resultId, String principalId,String comment) {
		String message = "Comment not saved!";
		if(!principalId.equals("admin")){
			List<Integer> teacheId =  usersDao.findTeacherIdByResultId(resultId);
			Integer pirncipalIdInt = parseInt(principalId);
			if(pirncipalIdInt != null && teacheId.contains(pirncipalIdInt)){
				Results result = resultDao.getOne(resultId);
				result.setComment(comment);
				resultDao.save(result);
				message = "Comment saved!";
			}
		}
		return message;
	}
	
	//==============================================================================
	
	@Transactional
	public double calculateTestResult(List<TestQuestionDTO> testResultList) {
		double testPoint = 0;
		double point = 0;
		double absolute = 0;
		double qwestPointMax = 0;
		List<Answers> userAnswers = null;
		for (TestQuestionDTO testResult : testResultList) {
			Questions question = questionsDao.findOne(testResult.getQuestion().getId());
			qwestPointMax = question.getGrade() * 10;
			absolute += qwestPointMax;
			userAnswers = new ArrayList<Answers>();
			for (Integer answIds : testResult.getCheckedAnswersId()) {
				try {
					Answers answer = answersDao.findOne(answIds);
					userAnswers.add(answer);
				} catch (IllegalArgumentException e) {
					System.out.println(e);
				}
			}
				if (question.isMultipleAnswers()) {
				point = multipleResultCalculate(question, userAnswers);
				testPoint += point;
			} else {
				point = singleResultCalculate(question, userAnswers);
				testPoint += point;
			}
		}
		testPoint = (testPoint / absolute) * 100;
		testPoint = Math.round(testPoint * 100);
		testPoint = testPoint / 100;
		return testPoint;
	}
		
	@Transactional
	private double singleResultCalculate(Questions question, List<Answers> userAnswers) {			
		double qwestPointMax = question.getGrade() * 10;
		for (Answers answer : question.getAnswers()) {
			if(!answer.isStatus() && userAnswers.contains(answer)){	
				return 0;
			}
			if (answer.isStatus() && userAnswers.contains(answer)) {
				return qwestPointMax;
			}
		}
		return 0;
	}
		
	@Transactional
	private double multipleResultCalculate(Questions question, List<Answers> userAnswers) {
		double questPoint = 0;
		int rightCount = 0;
		int wrongCount = 0;
		
		for (Answers answerr : question.getAnswers()) {
			if (answerr.isStatus()) {	rightCount++;	} 
			else {	wrongCount++;	}
		}
		Map<Integer,Double> ansersPoints = null;
		
		if (rightCount < wrongCount) {
			ansersPoints = multipleResultFirstCase(question, userAnswers);
		} else if (rightCount > wrongCount) {
			ansersPoints = multipleResultSecondCase(question, userAnswers);
		} else {
			ansersPoints = multipleResultThirdCase(question, userAnswers);
		}
		
		if(!ansersPoints.isEmpty()){
			for (Entry<Integer,Double> entry : ansersPoints.entrySet()) {
				questPoint+=entry.getValue();
			}
		}
		
		if (questPoint < 0) {
			questPoint = 0;
		} else {
			questPoint = (double) Math.round(questPoint*100)/100;
		}
		System.out.println("Calculate "+question.getId()+" "+questPoint);
		return questPoint;
	}
	
	@Transactional
	public List<TestResultViewDTO> userTestResultView(String testId, String principalId) {
		List<TestResultViewDTO> userResultDTOs = new ArrayList<TestResultViewDTO>();
		Integer role  = getRoleid(principalId);		
		if(role==null){
			return userResultDTOs;
		}
		
		Results result = null;
		if (testId != null && !testId.isEmpty()) {
			Integer testIdInt = parseInt(testId);	
			if(testIdInt!=null){				
				if(role == 3){
					Integer userId = parseInt(principalId);
					List<Integer> testResultIds = null;
					if(userId!=null){
						testResultIds = resultDao.findAllIdsByUserId(userId);
					}
					if(testResultIds!=null && testResultIds.contains(testIdInt)){
						result = resultDao.findOne(testIdInt);
					}					
				}else if(role == 2){
					Integer teacherId = parseInt(principalId);
					List<Integer> testResultIds = null;
					if(teacherId!=null){
						testResultIds = resultDao.findAllIdByTeacherId(teacherId);
					}
					if(testResultIds!=null && testResultIds.contains(testIdInt)){
						result = resultDao.findOne(testIdInt);
					}
					
				}else{
					result = resultDao.findOne(testIdInt);
				}
			}
			
		}
		if (result != null) {
			for (Questions question : result.getQuestions()) {
				TestResultViewDTO userResultDTO = new TestResultViewDTO();
				if (!question.isMultipleAnswers()) {
					userResultDTO = singleResultView(question, result.getAnswers(), (role == 3 ? false : true));
				} else {
					userResultDTO = multipleResultView(question, result.getAnswers(), (role == 3 ? false : true));
				}
				userResultDTOs.add(userResultDTO);
			}
		}
		return userResultDTOs;
	}
	
	private Integer parseInt(String intVal){
		Integer val = null;
		try {
			val = Integer.parseInt(intVal);
		} catch (NumberFormatException e) {
			System.out.println(e);
		}
		return val;
	}
	
	@Transactional
	private TestResultViewDTO singleResultView(Questions question, List<Answers> userAnswers, boolean showPoints) {
		List<List<String>> listList = new ArrayList<List<String>>();
		List<String> stringList = new ArrayList<String>();
		TestResultViewDTO userResultDTO = new TestResultViewDTO();
		userResultDTO.setQuestText(question.getText());
		double questPoints = 0;
		boolean error = false;
		boolean userAnswer = false;
		double qwestPointMax = question.getGrade() * 10;
		double UserPoint = 0;
		for (Answers answer : question.getAnswers()) {
			UserPoint = 0;
			userAnswer = false;
			error = false;
			if (userAnswers.contains(answer)) {
				userAnswer = true;
			}
			if (answer.isStatus() && userAnswers.contains(answer)) {
				UserPoint = qwestPointMax;
			} else if (!answer.isStatus() && userAnswers.contains(answer)) {
				error = true;
			}
			questPoints += UserPoint;
			stringList = new ArrayList<String>();
			stringList.add(answer.getText());
			stringList.add("" + answer.isStatus());
			if(showPoints){
				stringList.add("" + UserPoint);
			}else{
				stringList.add("");
			}
			stringList.add("" + error);
			stringList.add("" + userAnswer);
			listList.add(stringList);
		}
		userResultDTO.setId(question.getId());
		userResultDTO.setQuestText(question.getText());
		userResultDTO.setAnswersList(listList);
		if(showPoints){
			userResultDTO.setQuestGrade(question.getGrade());
			userResultDTO.setQuestPoints(questPoints);
		}
		userResultDTO.setInputType("radio");
			return userResultDTO;
	} 
		
	private TestResultViewDTO multipleResultView(Questions question, List<Answers> userAnswers, boolean showPoints){
		
		List<List<String>> listList = new ArrayList<List<String>>();	
		List<String> stringList = null;
		TestResultViewDTO userResultDTO = new TestResultViewDTO();
		
		Map<Integer,Double> ansersPoints = null;
		double questPoint = 0;
		
		if(showPoints){
			int rightCount = 0;
			int wrongCount = 0;
			
			for (Answers answerr : question.getAnswers()) {
				if (answerr.isStatus()) {	rightCount++;	} 
				else {	wrongCount++;	}
			}
						
			if (rightCount < wrongCount) {
				ansersPoints = multipleResultFirstCase(question, userAnswers);
			} else if (rightCount > wrongCount) {
				ansersPoints = multipleResultSecondCase(question, userAnswers);
			} else {
				ansersPoints = multipleResultThirdCase(question, userAnswers);
			}
			
			if(!ansersPoints.isEmpty()){
				for (Entry<Integer,Double> entry : ansersPoints.entrySet()) {
					questPoint+=entry.getValue();
				}
			}
			
			if (questPoint < 0) {
				questPoint = 0;
			} else {
				questPoint = (double) Math.round(questPoint*100)/100;
			}
		}
		System.out.println("View "+question.getId()+" "+questPoint);
		
				
		boolean error = false;
		boolean userAnswer = false;
		for (Answers answer : question.getAnswers()) {
		error = false;
		userAnswer = false;
		if (userAnswers.contains(answer)) {
			userAnswer = true;
			}
			if (!answer.isStatus() && userAnswers.contains(answer)) {
				error = true;
			}
		
			stringList = new ArrayList<String>();
			stringList.add(answer.getText());
			stringList.add("" + answer.isStatus());
			if(showPoints){
				Double point = ansersPoints.get(answer.getId());
				stringList.add("" + (point == null ? 0 : point) );
			}else{
				stringList.add("");
			}
			
			stringList.add("" + error);
			stringList.add("" + userAnswer);
			listList.add(stringList);
		}
		userResultDTO.setId(question.getId());
		userResultDTO.setQuestText(question.getText());
		userResultDTO.setAnswersList(listList);
		if(showPoints){
			userResultDTO.setQuestGrade(question.getGrade());
			userResultDTO.setQuestPoints(questPoint);
		}
		userResultDTO.setInputType("checkbox");	
		return userResultDTO;
	}
		
	private Map<Integer,Double> multipleResultFirstCase(Questions question, List<Answers> userAnswers){
				
		Map<Integer,Double> ansersPoints = new HashMap<Integer, Double>();
		int rightCount = 0;		
		for (Answers answerr : question.getAnswers()) {
			if (answerr.isStatus()) {
				rightCount++;
			} 
		}
		
		double coef = 0;
		if (rightCount < 4) {
			coef = 4;
		} else if (rightCount >= 4) {
			coef = 2;
		}
		
		int proportion = 1;
		for (int i = 1; i < rightCount - 1; i++) {
			proportion += i * coef;
		}
		
		double qwestPointMax = question.getGrade() * 10;
		double rightAnswPoint = (double) qwestPointMax / (double) rightCount;
		rightAnswPoint = (double) Math.round(rightAnswPoint * 100) / 100;
		double wrongAnswerBase = rightAnswPoint / 2;
		double wrongSummaryBase = wrongAnswerBase * rightCount;
		double wrongSummaryPoint = (double) qwestPointMax * 0.95;
		double wrongAnswerDelta = (double) (wrongSummaryPoint - (wrongSummaryBase)) / proportion;
			
		int wrongAnswCnt = 0;
		double userPoint;
		for (Answers answer : question.getAnswers()) {		
			userPoint = 0;
			if (answer.isStatus() && userAnswers.contains(answer)) {
				userPoint = rightAnswPoint;
				ansersPoints.put(answer.getId(), userPoint);
			} else if (!answer.isStatus() && userAnswers.contains(answer)) {
				if (wrongAnswCnt == 0) {
					userPoint = -(wrongAnswerBase);
				} else if (wrongAnswCnt == 1) {
					userPoint = -(wrongAnswerBase + wrongAnswerDelta);
				} else {
					userPoint = -(wrongAnswerBase + (wrongAnswerDelta * (wrongAnswCnt - 1) * coef));
				}
				wrongAnswCnt++;
				userPoint = (double) Math.round(userPoint * 100)/100;
				ansersPoints.put(answer.getId(), userPoint);
			}					
		}
		return ansersPoints;
	}
		
	private Map<Integer,Double> multipleResultSecondCase(Questions question, List<Answers> userAnswers){
		
		Map<Integer,Double> ansersPoints = new HashMap<Integer, Double>();
		int rightCount = 0;
		int wrongCount = 0;
		
		for (Answers answerr : question.getAnswers()) {
			if (answerr.isStatus()) {
				rightCount++;
			} else {
				wrongCount++;
			}
		}
		
		int coef = 2;
		double wrongAnswerBase = 0;
		double wrongAnswerDelta = 0;
		double wrongSummaryBase = 0;
		double qwestPointMax = question.getGrade() * 10;
		double rightAnswPoint = (double) qwestPointMax / (double) rightCount;
		rightAnswPoint = (double) Math.round(rightAnswPoint * 100) / 100;	
		double wrongSummaryPoint = (double) qwestPointMax;
		if(wrongCount > 1){
			int proportion = 1;
			for (int i = 1; i < wrongCount - 1; i++) {
				proportion += i * coef;
			}
			wrongAnswerBase = rightAnswPoint / 2;
			wrongSummaryBase = wrongAnswerBase * wrongCount;
			wrongAnswerDelta = (double) (wrongSummaryPoint - (wrongSummaryBase)) / proportion;
		}else{
			wrongAnswerBase = wrongSummaryPoint;
		}
		int wrongAnswCnt = 0;
		double userPoint;
		for (Answers answer : question.getAnswers()) {		
			userPoint = 0;
			if (answer.isStatus() && userAnswers.contains(answer)) {
				userPoint = rightAnswPoint;
				ansersPoints.put(answer.getId(), userPoint);
			} else if (!answer.isStatus() && userAnswers.contains(answer)) {
				if (wrongAnswCnt == 0) {
					userPoint = -(wrongAnswerBase);
				} else if (wrongAnswCnt == 1) {
					userPoint = -(wrongAnswerBase + wrongAnswerDelta);
				} else {
					userPoint = -(wrongAnswerBase + (wrongAnswerDelta * (wrongAnswCnt - 1) * coef));
				}
				wrongAnswCnt++;
				userPoint = (double) Math.round(userPoint * 100)/100;
				ansersPoints.put(answer.getId(), userPoint);
			}					
		}
		return ansersPoints;
		
//			double qwestPointMax = question.getGrade() * 10;
//			double rightAnswPoint = (double) qwestPointMax / (double) rightCount;
//			rightAnswPoint = (double) Math.round(rightAnswPoint * 100)/100;			
//			int proprtion = 0;
//			for (int i = 1; i <= wrongCount - 1; i++) {
//				proprtion += i;
//			}
//			double wrongAnswPoint = (double) ((double) qwestPointMax / 100 * 95) / proprtion;
//			int wrongAnswCnt = 0;
//			double userPoint;
//			for (Answers answer : question.getAnswers()) {
//				userPoint = 0;
//				if (answer.isStatus() && userAnswers.contains(answer)) {
//					userPoint = rightAnswPoint;
//					ansersPoints.put(answer.getId(), userPoint);
//				} else if (!answer.isStatus() && userAnswers.contains(answer)) {
//					wrongAnswCnt++;
//					userPoint = -(wrongAnswPoint * wrongAnswCnt);
//					userPoint = (double) Math.round(userPoint * 100)/100;
//					ansersPoints.put(answer.getId(), userPoint);
//				}			
//			}
//			return ansersPoints;
		}
		
		

	private Map<Integer,Double> multipleResultThirdCase(Questions question, List<Answers> userAnswers){
		
		Map<Integer,Double> ansersPoints = new HashMap<Integer, Double>();
	
		int rightCount = 0;	
		for (Answers answerr : question.getAnswers()) {
			if (answerr.isStatus()) {
				rightCount++;
			} 
		}
		
		double coef = 0;
		if (rightCount < 4) {
			coef = 4;
		} else if (rightCount >= 4) {
			coef = 2;
		}
			int proportion = 1;
		for (int i = 1; i < rightCount - 1; i++) {
			proportion += i * coef;
		}
		double qwestPointMax = question.getGrade() * 10;
		double rightAnswPoint = (double) qwestPointMax / (double) rightCount;
		rightAnswPoint = (double) Math.round(rightAnswPoint * 100)/100;
		double wrongAnswerBase = rightAnswPoint / 2;
		double wrongSummaryBase = wrongAnswerBase * rightCount;
		double wrongSummaryPoint = (double) qwestPointMax;
		double wrongAnswerDelta = (double) (wrongSummaryPoint - (wrongSummaryBase)) / proportion;
		int wrongAnswCnt = 0;
		double userPoint;
		for (Answers answer : question.getAnswers()) {
			userPoint = 0;
			if (answer.isStatus() && userAnswers.contains(answer)) {
				userPoint = rightAnswPoint;
				ansersPoints.put(answer.getId(), userPoint);
			} else if (!answer.isStatus() && userAnswers.contains(answer)) {
				if (wrongAnswCnt == 0) {
				userPoint = -(wrongAnswerBase);
			} else if (wrongAnswCnt == 1) {
				userPoint = -(wrongAnswerBase + wrongAnswerDelta);
				} else {
					userPoint = -(wrongAnswerBase + (wrongAnswerDelta * (wrongAnswCnt - 1) * coef));
				}
				wrongAnswCnt++;
				userPoint = (double) Math.round(userPoint * 100)/100;	
				ansersPoints.put(answer.getId(), userPoint);
			}
			
		}
		return ansersPoints;
	}

}
