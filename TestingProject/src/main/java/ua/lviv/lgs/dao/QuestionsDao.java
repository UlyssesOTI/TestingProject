package ua.lviv.lgs.dao;

import java.util.List;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.lviv.lgs.entity.Questions;

@Repository
public interface QuestionsDao extends JpaRepository<Questions, Integer> {
	
//	@Query("select q from Questions q where q.text like :text")
//	public Questions findQuestionByText(@Param("text")String text);
	
	@Query("select q.grade from Questions q Join q.theme t where t.id like :id")
	public List<Integer> findQuestionsComplexityByThemeId(@Param("id")int id);
	
	@Query("SELECT q FROM Questions q JOIN q.theme t WHERE t.id = :themeId ")
	public List<Questions> findAllByCoursePaginated(@Param("themeId") int themeId, Pageable pageble);
	
	@Query("SELECT COUNT(q) FROM Questions q JOIN q.theme t WHERE t.id = :themeId ")
	public Integer findQuestionCoutnByTheme(@Param("themeId") int themeId);

}
