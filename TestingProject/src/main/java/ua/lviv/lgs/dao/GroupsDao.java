package ua.lviv.lgs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.lviv.lgs.entity.Groups;

@Repository
public interface GroupsDao extends JpaRepository<Groups, Integer>{
	
//	@Query("SELECT g FROM Groups g where g.name like CONCAT(:name, '%')")
//	List<Groups> findGroupsByName(@Param("name") String name);
	
	
//	@Query("SELECT g FROM Groups g where g.name like :name")
//	Groups findGroupByName(@Param("name") String name);
	
	@Query("SELECT g.id FROM Groups g WHERE g.name like :name")
	Integer findGroupIdByName(@Param("name") String name);
}
