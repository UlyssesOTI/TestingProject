package ua.lviv.lgs.dto;

public class TestDTO {
	
	int id;
	String name;
	
	private int time;
	private int highGradeQuestionQuantity;
	public TestDTO(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}


	private int middleGradeQuestionQuantity;
	private int lowGradeQuestionQuantity;
	private int courseId;
	private String courseName;

	

	public TestDTO(int id, String name, int time, int highGradeQuestionQuantity, int middleGradeQuestionQuantity,
			int lowGradeQuestionQuantity, int courseId, String courseName) {
		
		this.id = id;
		this.name = name;
		this.time = time;
		this.highGradeQuestionQuantity = highGradeQuestionQuantity;
		this.middleGradeQuestionQuantity = middleGradeQuestionQuantity;
		this.lowGradeQuestionQuantity = lowGradeQuestionQuantity;
		this.courseId = courseId;
		this.courseName = courseName;
	}
	public TestDTO(int id, String name, int courseId, String courseName) {
		
		this.id = id;
		this.name = name;
		this.courseId = courseId;
		this.courseName = courseName;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int getTime() {
		return time;
	}


	public void setTime(int time) {
		this.time = time;
	}


	public int getHighGradeQuestionQuantity() {
		return highGradeQuestionQuantity;
	}


	public void setHighGradeQuestionQuantity(int highGradeQuestionQuantity) {
		this.highGradeQuestionQuantity = highGradeQuestionQuantity;
	}


	public int getMiddleGradeQuestionQuantity() {
		return middleGradeQuestionQuantity;
	}


	public void setMiddleGradeQuestionQuantity(int middleGradeQuestionQuantity) {
		this.middleGradeQuestionQuantity = middleGradeQuestionQuantity;
	}


	public int getLowGradeQuestionQuantity() {
		return lowGradeQuestionQuantity;
	}


	public void setLowGradeQuestionQuantity(int lowGradeQuestionQuantity) {
		this.lowGradeQuestionQuantity = lowGradeQuestionQuantity;
	}


	public int getCourseId() {
		return courseId;
	}


	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}


	public String getCourseName() {
		return courseName;
	}


	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	
}
