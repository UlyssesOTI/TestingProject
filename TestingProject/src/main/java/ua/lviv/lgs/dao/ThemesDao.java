package ua.lviv.lgs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.lviv.lgs.entity.Questions;
import ua.lviv.lgs.entity.Themes;

public interface ThemesDao extends JpaRepository<Themes, Integer> {

	@Query("select th from Themes th join th.tests t where t.id like :id")
	public List<Themes> findAllThemesByTestsId(@Param("id") int id);

	@Query("select q from Questions q join q.theme th where q.grade like :grade and th.id like :id")
	public List<Questions> findAllQuestionsFromThemeByGrade(@Param("id") int themeId, @Param("grade")int grade);
}
