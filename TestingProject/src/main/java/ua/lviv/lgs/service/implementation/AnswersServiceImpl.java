package ua.lviv.lgs.service.implementation;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ua.lviv.lgs.dao.AnswersDao;
import ua.lviv.lgs.dao.QuestionsDao;
import ua.lviv.lgs.entity.Answers;
import ua.lviv.lgs.service.AnswersService;

@Service
public class AnswersServiceImpl implements AnswersService {

	@Autowired
	AnswersDao answerDAO;

	@Autowired
	QuestionsDao questionsDao;

	@Transactional
	public Answers add(String text, MultipartFile file, boolean status, int questionId) {
		Answers answer = new Answers();
		answer.setText(text);
		answer.setStatus(status);
		answer.setQuestion(questionsDao.findOne(questionId));
		if (file != null) {
			try {
				answer.setImage(file.getBytes());
			} catch (IOException e) {
				answer.setImage(new byte[1]);
			}
		}
		return answerDAO.save(answer);
	}

	public Answers edit(int id, String text, MultipartFile file, boolean status, int questionId) {
		Answers answer = findById(id);
		answer.setText(text);
		answer.setStatus(status);
		answer.setQuestion(questionsDao.findOne(questionId));
		if (file != null) {
			try {
				answer.setImage(file.getBytes());
			} catch (IOException e) {
				answer.setImage(new byte[1]);
			}
		}
		return answerDAO.save(answer);	
	}

	public void delete(int id) {
		answerDAO.delete(id);
	}

//	public List<Answers> findAll() {
//		return answerDAO.findAll();
//	}

	private Answers findById(int id) {
		return answerDAO.findOne(id);
	}

}
