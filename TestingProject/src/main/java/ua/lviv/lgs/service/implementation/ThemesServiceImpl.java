package ua.lviv.lgs.service.implementation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.lviv.lgs.dao.CoursesDao;
import ua.lviv.lgs.dao.ThemesDao;
import ua.lviv.lgs.dto.ThemeDTO;
import ua.lviv.lgs.entity.Themes;
import ua.lviv.lgs.service.ThemesService;

@Service
public class ThemesServiceImpl implements ThemesService {

	@Autowired
	private ThemesDao themesDao;
	@Autowired
	private CoursesDao coursesDao;

	private static Logger log = Logger.getLogger(ThemesServiceImpl.class);
	
	@Transactional
	public void add(String name, int courseId) {
		Themes theme = new Themes(name);
		theme.setCourse(coursesDao.findOne(courseId));
		themesDao.save(theme);
	}

	@Transactional
	public void edit(int themeId, String name, String courseId) {
		Themes theme = themesDao.findOne(themeId);
		if (name != null) {
			theme.setName(name);
		}
		if (courseId != null) {
			theme.setCourse(coursesDao.findOne(Integer.parseInt(courseId)));
		}
		themesDao.save(theme);
	}

	@Transactional
	public void delete(int themeId) {
		Themes theme = themesDao.findOne(themeId);
		themesDao.delete(theme);
		log.info("[INFO] deleted theme with id="+themeId);
	}

	@Transactional
	public ThemeDTO findById(int themeId) {
		try {
			Themes theme = themesDao.findOne(themeId);
			ThemeDTO themeDTO = new ThemeDTO(themeId, theme.getName());
			themeDTO.setCourse(theme.getCourse().getName());
			themeDTO.setCourseId(theme.getCourse().getId());
			return themeDTO;
		} catch (Exception e) {
			return null;
		}
	}

//	@Transactional
//	public List<ThemeDTO> findAll() {
//		List<Themes> themes = themesDao.findAll();
//		List<ThemeDTO> themesDTO = new ArrayList<ThemeDTO>();
//		for (Themes theme : themes) {
//			ThemeDTO themeDTO = new ThemeDTO(theme.getId(), theme.getName());
//			themesDTO.add(themeDTO);
//		}
//		return themesDTO;
//	}

//	@Transactional
//	public List<Themes> findAllThemesByTestId(int themeId) {
//		return themesDao.findAllThemesByTestsId(themeId);
//	}

//	@Transactional
//	public List<Questions> findAllQuestionsFromThemeByGrade(int themeId, int grade) {
//		return themesDao.findAllQuestionsFromThemeByGrade(themeId, grade);
//	}

	@Transactional
	public List<Themes> findAllByCourse(int courseId) {
		return coursesDao.findAllThemesByCourseId(courseId);

	}

//	@Transactional
//	public Themes findOneById(int id) {
//		return themesDao.findOne(id);
//	}
	
//	@Transactional
//	public List<Themes> findAllThemes() {
//		return themesDao.findAll();
//	}
	
	@Transactional
	public List<ThemeDTO> findAllDtoByCourse(int courseId) {
		List<ThemeDTO> themesDTO = new ArrayList<ThemeDTO>();
		List<Themes> themes = coursesDao.findAllThemesByCourseId(courseId);
		for (Themes theme : themes) {
			ThemeDTO themeDTO = new ThemeDTO(theme.getId(), theme.getName());
			themeDTO.setCourse(coursesDao.findOne(courseId).getName());
			themeDTO.setCourseId(courseId);
			themesDTO.add(themeDTO);
		}
		return themesDTO;
	}

	@Transactional
	public List<?> findAllUnDuplicated(List<?> checkdList, List<?> fullList) {
		if(fullList == null || checkdList == null){
			return null;
		}else{
			Iterator<?> iter = fullList.iterator();
			while (iter.hasNext()) {
				Object iterObj = iter.next();
				for (Object forechObj : checkdList) {
					if (forechObj.equals(iterObj)) {
						iter.remove();
					}
				}
			}
			return fullList;
		}
		
	}

}
