package ua.lviv.lgs.service;

import java.util.List;

import ua.lviv.lgs.dto.ThemeDTO;
import ua.lviv.lgs.entity.Questions;
import ua.lviv.lgs.entity.Themes;

public interface ThemesService {
	
	void add(String name, int courseId);

	void edit(int id, String name, String courseId);

	void delete(int id);

	ThemeDTO findById(int id);
	
//	Themes findOneById(int id);

//	List<ThemeDTO> findAll();
	
//	List<Themes> findAllThemes();
	
//	List<Themes> findAllThemesByTestId(int id);
	
//	List<Questions> findAllQuestionsFromThemeByGrade(int id, int grade);
	
	List<Themes> findAllByCourse(int courseId);
	
	List<ThemeDTO> findAllDtoByCourse(int courseId);
	
	List<?> findAllUnDuplicated(List<?> checkdList, List<?> fullList);
	
}
