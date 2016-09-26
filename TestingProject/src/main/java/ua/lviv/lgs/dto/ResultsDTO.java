package ua.lviv.lgs.dto;

public class ResultsDTO {
	
	private String resultID;
	
	private String testName;
	
	private String testMark;

	public ResultsDTO(String resultID, String testName, String testMark) {
		super();
		this.resultID = resultID;
		this.testName = testName;
		this.testMark = testMark;
	}

	public String getResultID() {
		return resultID;
	}

	public void setResultID(String resultID) {
		this.resultID = resultID;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestMark() {
		return testMark;
	}

	public void setTestMark(String testMark) {
		this.testMark = testMark;
	}

	
	
}

