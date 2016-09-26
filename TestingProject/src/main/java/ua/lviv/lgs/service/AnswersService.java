package ua.lviv.lgs.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ua.lviv.lgs.entity.Answers;

public interface AnswersService {

	Answers add(String text, MultipartFile file, boolean status, int questionId);

	Answers edit(int id, String text, MultipartFile file,boolean status, int questionId);

	void delete(int id);

//	List<Answers> findAll();

//	Answers findById(int id);
}
