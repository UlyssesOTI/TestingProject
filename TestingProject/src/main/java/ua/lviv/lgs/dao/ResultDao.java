package ua.lviv.lgs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.lviv.lgs.entity.Results;

@Repository
public interface ResultDao extends JpaRepository<Results, Integer>{

	@Query("SELECT r FROM Results r WHERE r.user = :userId")
	List<Results> findAllByUserId(@Param("userId") int userId);
	
	@Query("SELECT r.id FROM Results r WHERE r.user = :userId")
	List<Integer> findAllIdsByUserId(@Param("userId") int userId);
	
	@Query("SELECT r "+
				"FROM Results r "+
			"WHERE "+
				"r.user = :studentId "+
			"AND "+
				"r.test in " + 
					"(SELECT t.id FROM Tests t JOIN t.course c JOIN c.groups g	WHERE g.teacherId = :teacherId) "+
			"AND "+
				"r.user in "+
					"(SELECT u.id FROM Users u JOIN u.groups g WHERE g.teacherId = :teacherId) ")
	List<Results> findAllByUserIdAndTeacherId(@Param("studentId") int studentId,@Param("teacherId") int teacherId);
	
	@Query("SELECT r.id "+
			   "FROM Results r "+
		  "WHERE "+
			  "r.test in " + 
			       "(SELECT t.id FROM Tests t JOIN t.course c JOIN c.groups g	WHERE g.teacherId = :teacherId) "+
		  "AND "+
			  "r.user in "+
				   "(SELECT u.id FROM Users u JOIN u.groups g WHERE g.teacherId = :teacherId) ")
	List<Integer> findAllIdByTeacherId(@Param("teacherId") int teacherId);
}
