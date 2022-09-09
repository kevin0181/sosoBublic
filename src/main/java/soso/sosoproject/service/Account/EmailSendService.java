package soso.sosoproject.service.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class EmailSendService {

    @Autowired
    private JavaMailSender javaMailSender;

    public EmailSendService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public EmailSendService() {

    }

    //이메일 인증
    public String sendEmailCheck(String email, String certificationKey) throws MessagingException {
//        sendMail(email, "soso 인증메일 입니다.", "인증메일입니다.");
        StringBuffer emailcontent = new StringBuffer();
        emailcontent.append("<!DOCTYPE html>");
        emailcontent.append("<html>");
        emailcontent.append("<head>");
        emailcontent.append("</head>");
        emailcontent.append("<body>");
        emailcontent.append(
                " <div" +
                        "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">" +
                        "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">" +
                        "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">SOSO</span><br />" +
                        "		<span style=\"color: #02b875\">메일인증</span> 안내입니다." +
                        "	</h1>\n" +
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                        email +
                        "		님 안녕하세요.<br />" +
                        "		soso에 가입해 주셔서 진심으로 감사드립니다.<br />" +
                        "	</p>" + "<p>" + certificationKey + "</p>" +
                        " </div>"
        );
        emailcontent.append("</body>");
        emailcontent.append("</html>");

        sendMail(email, "soso 인증메일 입니다.", emailcontent.toString());

        return certificationKey;
    }


    //비밀번호 찾기 이메일
    public void sendPasswordCheck(String memberEmail, String certificationKey) throws MessagingException {

        StringBuffer emailcontent = new StringBuffer();
        emailcontent.append("<!DOCTYPE html>");
        emailcontent.append("<html>");
        emailcontent.append("<head>");
        emailcontent.append("</head>");
        emailcontent.append("<body>");
        emailcontent.append(
                " <div" +
                        "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">" +
                        "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">" +
                        "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">SOSO</span><br />" +
                        "		<span style=\"color: #02b875\">비밀번호 초기화</span> 안내입니다." +
                        "	</h1>\n" +
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                        memberEmail +
                        "		님 안녕하세요.<br />" +
                        "		soso 비밀번호 초기화 입니다.<br/> 해당페이지에 접속하여 비밀번호를 수정하여 주시길 바랍니다.<br />" +
                        "	</p>" + "<p>" + "<a href=\"" + "https://soso-kitchen.com/user/account/change/password?email=" + memberEmail + "&certi=" + certificationKey + "\">"
                        + "비밀번호 초기화 하러가기" + "</a>" +
                        " </div>"
        );
        emailcontent.append("</body>");
        emailcontent.append("</html>");

        sendMail(memberEmail, "soso 비밀번호 초기화 입니다.", emailcontent.toString());

    }


    public void sendMail(String toEmail, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setFrom("soso"); //보내는사람
        helper.setTo(toEmail); //받는사람
        helper.setSubject(subject); //메일제목
        helper.setText(message, true); //ture넣을경우 html

        javaMailSender.send(mimeMessage);
    }


    public String certified_key() {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        int num = 0;

        do {
            num = random.nextInt(75) + 48;
            if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                sb.append((char) num);
            } else {
                continue;
            }

        } while (sb.length() < 10);
        return sb.toString();
    }


}
