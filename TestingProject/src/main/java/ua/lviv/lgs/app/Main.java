package ua.lviv.lgs.app;

public class Main {

//	public static void main(String[] args) {
//		/* ConfigurableApplicationContext cfg = new
//		 ClassPathXmlApplicationContext("classpath:/META-INF/appContext.xml");
//		 TestsService ts = cfg.getBean(TestsService.class);
//		 ThemesService ths = cfg.getBean(ThemesService.class);
//		 QuestionsService qs = cfg.getBean(QuestionsService.class);
//		 QuestionsDao qd = cfg.getBean(QuestionsDao.class);
//		 TestsDao td = cfg.getBean(TestsDao.class);
//		 UsersService us = cfg.getBean(UsersService.class);
//		 GroupsService gs = cfg.getBean(GroupsService.class);
//		 CoursesService cs = cfg.getBean(CoursesService.class);
//		 AnswersService as = cfg.getBean(AnswersService.class);
//		/*
//		 * Random r = new Random();
//		 * 
//		 * Tests test = td.getOne(1);
//		 * 
//		 * for (int i = 0; i < 40; i++) { Questions questions = new
//		 * Questions(""+(i+1),r.nextInt(2)+1,true); List<Answers> answers = new
//		 * ArrayList<Answers>(); for (int j = 0; j < r.nextInt(3)+2; j++) {
//		 * answers.add(new
//		 * Answers("answer"+(j+1),null,r.nextBoolean(),questions)); }
//		 * questions.setAnswers(answers); qd.save(questions); }
//		 */
//
//		// ts.add("Test1", 10, 1, 2, 3);
//		// ths.add("Theme1");
//		// ths.add("Theme2");
//		// ths.add("Theme3");
//		// ths.add("Theme4");
//		// ths.add("Theme5");
//		// ths.add("Theme6");
//		// ths.add("Theme7");
//		// ths.add("Theme8");
//		// ths.add("Theme9");
//		// ths.add("Theme10");
//		// qs.add("text1", null, "Theme1", 1, 1);
//		// qs.add("text2", null, "Theme2", 1, 2);
//		// qs.add("text3", null, "Theme3", 1, 3);
//		// qs.add("text4", null, "Theme4", 1, 3);
//		// qs.add("text5", null, "Theme5", 1, 2);
//		// qs.add("text6", null, "Theme6", 1, 4);
//		// qs.add("text7", null, "Theme7", 1, 5);
//		// qs.add("text8", null, "Theme8", 1, 1);
//		// qs.add("text9", null, "Theme8", 1, 2);
//		// qs.add("text10", null, "Theme10", 1, 1);
//		// qs.add("text11", null, "Theme11", 1, 1);
//		// qs.add("text12", null, "Theme12", 1, 3);
//		// qs.add("text13", null, "Theme13", 1, 1);
//		// qs.add("text14", null, "Theme14", 1, 1);
//		// qs.add("text15", null, "Theme15", 1, 2);
//		// qs.add("text16", null, "Theme16", 1, 6);
//		// qs.add("text17", null, "Theme17", 1, 7);
//		// qs.add("text18", null, "Theme18", 1, 1);
//		// qs.add("text19", null, "Theme19", 1, 3);
//		// qs.add("text20", null, "Theme20", 1, 1);
//
//		// Tests test = ts.findById(1);
//		// List<Themes> themes = ths.findAllThemesByTestId(test.getId());
//		// List<Questions> questions = new ArrayList<Questions>();
//		// int j = 0;
//		// int k = 0;
//		// int l = 0;
//		// Random r = new Random();
//		// for (int i = 0; i < test.getHighGradeQuestionQuantity(); i++) {
//		// for (Themes theme : themes) {
//		// List<Questions> tempList =
//		// ths.findAllQuestionsFromThemeByGrade(theme.getId(), 3);
//		//
//		// if (j >= test.getHighGradeQuestionQuantity()) {
//		// break;
//		// }
//		// Questions quest =
//		// qs.findById(tempList.get(r.nextInt(tempList.size())).getId());
//		// if (!questions.contains(quest)) {
//		// questions.add(quest);
//		// j++;
//		// }
//		// }
//		// }
//		// for (int i = 0; i < test.getMiddleGradeQuestionQuantity(); i++) {
//		// for (Themes theme : themes) {
//		// List<Questions> tempList =
//		// ths.findAllQuestionsFromThemeByGrade(theme.getId(), 2);
//		//
//		// if (k >= test.getMiddleGradeQuestionQuantity()) {
//		// break;
//		// }
//		// Questions quest =
//		// qs.findById(tempList.get(r.nextInt(tempList.size())).getId());
//		// if (!questions.contains(quest)) {
//		// questions.add(quest);
//		// k++;
//		// }
//		// }
//		// }
//		// for (int i = 0; i < test.getLowGradeQuestionQuantity(); i++) {
//		// for (Themes theme : themes) {
//		// List<Questions> tempList =
//		// ths.findAllQuestionsFromThemeByGrade(theme.getId(), 1);
//		//
//		// if (l >= test.getLowGradeQuestionQuantity()) {
//		// break;
//		// }
//		// Questions quest =
//		// qs.findById(tempList.get(r.nextInt(tempList.size())).getId());
//		// if (!questions.contains(quest)) {
//		// questions.add(quest);
//		// l++;
//		// }
//		// }
//		// }
//		// for (Questions q : questions) {
//		// System.out.println(q);
//		// }
//
//		// for(Integer i=0; i <= 100; i++){
//		// String b = ;
//
//		/*
//		 * List<Themes> themes = ths.findAllThemesByTestId(test.getId());
//		 * List<Questions> questions = new ArrayList<Questions>(); int j = 0;
//		 * int k = 0; int l = 0; Random r = new Random(); for (int i = 0; i <
//		 * test.getHighGradeQuestionQuantity(); i++) { for (Themes theme :
//		 * themes) { List<Questions> tempList =
//		 * ths.findAllQuestionsFromThemeByGrade(theme.getId(), 3);
//		 * 
//		 * if (j >= test.getHighGradeQuestionQuantity()) { break; } Questions
//		 * quest =
//		 * qs.findById(tempList.get(r.nextInt(tempList.size())).getId()); if
//		 * (!questions.contains(quest)) { questions.add(quest); j++; } } } for
//		 * (int i = 0; i < test.getMiddleGradeQuestionQuantity(); i++) { for
//		 * (Themes theme : themes) { List<Questions> tempList =
//		 * ths.findAllQuestionsFromThemeByGrade(theme.getId(), 2);
//		 * 
//		 * if (k >= test.getMiddleGradeQuestionQuantity()) { break; } Questions
//		 * quest =
//		 * qs.findById(tempList.get(r.nextInt(tempList.size())).getId()); if
//		 * (!questions.contains(quest)) { questions.add(quest); k++; } } } for
//		 * (int i = 0; i < test.getLowGradeQuestionQuantity(); i++) { for
//		 * (Themes theme : themes) { List<Questions> tempList =
//		 * ths.findAllQuestionsFromThemeByGrade(theme.getId(), 1);
//		 * 
//		 * if (l >= test.getLowGradeQuestionQuantity()) { break; } Questions
//		 * quest =
//		 * qs.findById(tempList.get(r.nextInt(tempList.size())).getId()); if
//		 * (!questions.contains(quest)) { questions.add(quest); l++; } } }
//		 */
//
//		// List<Questions> questions = qs.generateNewTest(1);
//		//
//		// for (Questions q : questions) {
//		// System.out.println(q);
//		// }
//
//		// List<Questions> ques = qs.generateNewTest(1);
//		// for (int m = 0; m < ques.size(); m++) {
//		// System.out.println(ques.get(m));
//		// }
//		/*
//		 * BCryptPasswordEncoder b = new BCryptPasswordEncoder(); String s =
//		 * "pass"; String pas=b.encode(s); UsersService us =
//		 * cfg.getBean(UsersService.class); us.add("manger", "manger", "manger",
//		 * "manger", pas, 2, "dev","1");
//		 */
//
//		// System.out.println(System.getProperty("catalina.h"));
//
//		// cfg.close();
//
//		String[] names = { "Олег", "Петро", "Святослав", "Тарас", "Роман", "Сергій", "Микола", "Христя", "Галя", "Мар'яна", "Іра" };
//		String[] secondnames = {"Бульбук", "Гук", "Жеребецька(кий)", "Коцюба", "Желековська", "Дзиндра", "Скоропад"};
//		String[] courses = {"HTML", "Java Begin", "Java Core", "Java Advanced", "MySQL", "C++", "C#", "Javascript"};
//		
//		int usersQuantity = 0;
//		String number = "380973970907";
//		for (int i = 0; i < usersQuantity; i++) {
//			Random tempRandomName = new Random();
//			int randomName = tempRandomName.nextInt(names.length);
//			Random tempRandomSecondname = new Random();
//			int randomSecondname = tempRandomSecondname.nextInt(secondnames.length);
//			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//			String password = bCryptPasswordEncoder.encode(names[randomName]);
//			//us.add(names[randomName], secondnames[randomSecondname], (names[randomName]+secondnames[randomSecondname]+"@gmail.com"), Integer.toString(Integer.parseInt(number+i)), password, roleId, groupName, course);
//		}
//		
//		
//		int courseQuantity = 0;
//		for(int i=0; i<courseQuantity; i++){
//			Random tempCourseName = new Random();
//			int courseName = tempCourseName.nextInt(courses.length);
//			cs.add(courses[courseName]);
//		}
//		
//		
//		String[]groupNames = {"17:30, 19:00, 20:30, 16:00"};
//		List<Courses> allCourses = cs.findAllCourses();
//		int groupsQuantity = 0;
//		for(int i =0; i<groupsQuantity; i++){
//			Random tempGroupname = new Random();
//			int groupName = tempGroupname.nextInt(groupNames.length);
//			Random tempCourse = new Random();
//			int course = tempCourse.nextInt(allCourses.size());
//			gs.add("JavaDev"+groupNames[groupName], cs.findOneById(course).getId());
//		}
//		
//		List<Themes>allThemes = ths.findAllThemes();
//		String questionText = "Text of question ";
//		String answerText = "Text of answer ";
//		int questionQuantity = 0;
//		for(int i =0; i<questionQuantity; i++){
//			Random tempTheme = new Random();
//			int theme = tempTheme.nextInt(allThemes.size());
//			Random tempComplexity = new Random();
//			int complexity = tempComplexity.nextInt(3)+1;
//			String isMulti = "false";
//			Random isMulty = new Random();
//			int multi = isMulty.nextInt(10)+1;
//			if(multi%2==0){
//				isMulti = "true";
//			}
//			qs.add(questionText+i, (MultipartFile)null, Integer.toString(ths.findOneById(theme).getId()), (String)null, isMulti);
//			Random answerQuantity = new Random();
//			int answers = answerQuantity.nextInt(6);
//			if(answers==0){
//				answers+=3;	
//			}
//			else if(answers==1){
//				answers+=2;
//			}
//			else if (answers==2) {
//				answers+=1;
//			}
//			for(int j = 0; j<answers; j++){
//				String status = "false";
//				if(isMulti=="true"){
//					Random rand = new Random();
//					int tempRand = rand.nextInt(10)+1;
//					if(tempRand%2==1){
//						status="true";
//					}
//				}
//				as.add(answerText+j, (MultipartFile)null, status, Integer.toString(qs.findQuestionByName(questionText+i).getId()));
//		
//			}
//		}
//	}


}
