package ua.lviv.lgs.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.lviv.lgs.entity.Users;

@Repository
public interface UsersDao extends JpaRepository<Users, Integer> {
	@Query("Select u from Users u where u.email like :login or u.phone like :login")
	Users findByLogin(@Param("login") String login);

//	@Query("Select u from Users u where u.name like CONCAT(:name, '%') OR u.secondname like CONCAT(:name, '%'))")
//	List<Users> findUsers(@Param("name") String name);

//	@Query("Select u from Users u where (u.name like CONCAT(:name, '%') AND u.secondname like CONCAT(:secondname, '%')) "
//			+ "OR (u.secondname like CONCAT(:name, '%') AND u.name like CONCAT(:secondname, '%')) ")
//	List<Users> findUsers(@Param("name") String name, @Param("secondname") String secondname);
	
//	@Query("SELECT u FROM Users u WHERE u.role.id = :roleId")
//	List<Users> findUsersByRole(@Param("roleId") int roleId);

	@Query("SELECT COUNT(u) FROM Users u WHERE (u.name like CONCAT(:name, '%') OR u.secondname like CONCAT(:name, '%')) AND u.role.name like CONCAT('%', :roleId))")
	Integer findUsersCountByRole(@Param("name") String name,  @Param("roleId") String roleId);
	
	@Query("SELECT COUNT(u) FROM Users u WHERE (u.name like CONCAT(:name, '%') AND u.secondname like CONCAT(:secondname, '%') AND u.role.name like CONCAT('%', :roleId))) "
			+ "OR (u.secondname like CONCAT(:name, '%') AND u.name like CONCAT(:secondname, '%') AND u.role.name like CONCAT('%', :roleId))) ")
	Integer findUsersCountByRole(@Param("name") String name, @Param("secondname") String secondname, @Param("roleId") String roleId);
	
//	@Query("Select u from Users u join u.role r where r.name like :role")
//	List<Users> findByRole(@Param("role") String role);

	@Query("select u from Users u join u.groups g where g.id = :id")
	List<Users> findUsersByGroupId(@Param("id") int id);
	
	@Query("SELECT g.teacherId FROM Groups g JOIN g.users u WHERE u.id in (SELECT r.user FROM Results r WHERE r.id = :resultId)")
	List<Integer> findTeacherIdByResultId(@Param("resultId") int resultId);
	
	@Query("select u.id from Users u join u.groups g where g.id = :id")
	List<Integer> findUsersIdByGroupId(@Param("id") int id);
	
	@Query("SELECT DISTINCT u FROM Users u JOIN u.courses c WHERE c.id in (:ids)")
	List<Users> findTeachersByCoursesIds(@Param("ids") List<Integer> ids);
	
	@Query("SELECT c.id FROM Courses c JOIN c.users u WHERE u.id = :id ")
	List<Integer> findUserCoursesId(@Param("id") int userId);
	
	@Query("SELECT u.id FROM Users u WHERE u.email like :email")
	Integer findUsersIdByEmail(@Param("email") String email);
	
	@Query("SELECT u.id FROM Users u WHERE u.hashSume = :hashSume")
	Integer findUsersIdByHashSum(@Param("hashSume") String hashSume);
	
	@Query("SELECT u.id FROM Users u WHERE u.phone like :phone")
	Integer findUsersIdByPhone(@Param("phone") String phone);
	
//	List<Users> findBySecondnameStartingWith(String secondname, Pageable pagable);
	
	@Query("SELECT u FROM Users u WHERE (u.name like CONCAT(:name, '%') OR u.secondname like CONCAT(:name, '%')) AND u.role.name like CONCAT('%', :roleId))")
	List<Users> findUsersByRole228(@Param("name") String name, @Param("roleId") String roleId, Pageable pagable);
	
	@Query("SELECT u FROM Users u WHERE ((u.name like CONCAT(:name, '%') AND u.secondname like CONCAT(:secondname, '%') AND u.role.name like CONCAT('%', :roleId)) "
			+ "OR (u.secondname like CONCAT(:name, '%') AND u.name like CONCAT(:secondname, '%') AND u.role.name like CONCAT('%', :roleId))) ")
	List<Users> findUsersByRole228(@Param("name") String name, @Param("secondname") String secondname, @Param("roleId") String roleId, Pageable pagable);
	
	@Query("SELECT concat(u.name, ' ', u.secondname) as fullname FROM Users u WHERE (u.name like CONCAT(:name, '%') OR u.secondname like CONCAT(:name, '%')) AND u.role.name like CONCAT('%', :roleId))")
	List<String> findUsersByRole(@Param("name") String name, @Param("roleId") String roleId);
	
	@Query("SELECT concat(u.name, ' ', u.secondname) as fullname FROM Users u WHERE (u.name like CONCAT(:name, '%') AND u.secondname like CONCAT(:secondname, '%') AND u.role.name like CONCAT('%', :roleId)) "
			+ "OR (u.secondname like CONCAT(:name, '%') AND u.name like CONCAT(:secondname, '%') AND u.role.name like CONCAT('%', :roleId)) ")
	List<String> findUsersByRole(@Param("name") String name, @Param("secondname") String secondname, @Param("roleId") String roleId);
	
//	@Query("SELECT concat(u.name, ' ', u.secondname) as fullname from Users u WHERE u.role.name like CONCAT('%', :roleId))")
//	List<String> findAllFulname(@Param("roleId") String roleId);
	
	Users findByEmail(String email);
}
