package ua.lviv.lgs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.lviv.lgs.entity.Courses;
import ua.lviv.lgs.entity.Themes;

@Repository
public interface CoursesDao extends JpaRepository<Courses, Integer>{
	
//	@Query("SELECT c FROM Courses c where c.name like :name")
//	Courses findCourseByName(@Param("name") String name);

	@Query("SELECT c FROM Courses c join c.users u  where u.id like :id")
	List<Courses> findByUserId(@Param("id")int id);
	
	@Query("SELECT th FROM Themes th JOIN th.course c WHERE c.id like :id")
	List<Themes> findAllThemesByCourseId(@Param("id")int courseId);
	
	@Query("SELECT c.id FROM Courses c WHERE c.name like :name")
	Integer findCourseIdByName(@Param("name") String name);
	
}
