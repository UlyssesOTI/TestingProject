package ua.lviv.lgs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.lviv.lgs.entity.Courses;
import ua.lviv.lgs.entity.Tests;
import ua.lviv.lgs.entity.Users;

@Repository
public interface TestsDao extends JpaRepository<Tests, Integer> {
	
	@Query("SELECT c FROM Courses c JOIN c.users u WHERE u.id = :teacherId")
	List<Courses> findCoursesFromUsers(@Param("teacherId") int teacherId);
	
	@Query("SELECT u FROM Users u WHERE u.id in (SELECT g.teacherId FROM Groups g WHERE g.id = :groupId)")
	Users findTeacherByGroupId(@Param("groupId") Integer groupId);

	@Query("SELECT t FROM Tests t WHERE t.course.id = :courseId")
	List<Tests> findAllTestsByCourse(@Param("courseId") int courseId);
	
	@Query("SELECT t.id FROM Tests t WHERE t.name = :name")
	int findTestIdByName(@Param("name") String name);
	
	
	
}
