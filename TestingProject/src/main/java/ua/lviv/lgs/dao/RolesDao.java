package ua.lviv.lgs.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.lviv.lgs.entity.Roles;

@Repository
public interface RolesDao extends JpaRepository<Roles, Integer> {
	
//	@Query("Select r.id from Users u join u.role r where u.id like :id")
//	public Integer findRoleByUserId(@Param("id") int id);
}
