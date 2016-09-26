package ua.lviv.lgs.service;

public interface MailSenderService {
	
	public void sendMail(String messageHeader, String email, String message);

}
