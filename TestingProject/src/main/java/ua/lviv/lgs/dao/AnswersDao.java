package ua.lviv.lgs.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.lviv.lgs.entity.Answers;

@Repository
public interface AnswersDao extends JpaRepository<Answers, Integer> {

}
