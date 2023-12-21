package com.example.zion23182.Configuration;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmailSenderServiceConfig {
      @Autowired
    private  JavaMailSender mailsender;

    public void sendEmail(String toemail, String subject, String body) throws MessagingException {

        MimeMessage message = mailsender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setSubject(subject);
        helper.setTo(toemail);
        helper.setText(body, true);
        mailsender.send(message);
    }

    public void sendPoliceEmail(String toemail, String subject, String username, String division)
            throws MessagingException {
        String message = "<body>\r\n" + //
                "  <h1>\r\n" + //
                "   Police System \r\n" + //
                "  </h1>\r\n" + //
                "  <div>\r\n" + //
                "    Hi Dear,<b>" + username + "</b>\r\n" + //
                "  </div>\r\n" + //
                "  <div>\r\n" + //
                "    You have been registered sucessfully \r\n" + //
                "  </div>\r\n" + //

                "  <div>\r\n" + //
                "  With category  :<b>" + division + " </b>\r\n" + //
                "  </div>\r\n" + //
                "  <div>\r\n" + //
                " <br> Thank you. </b>\r\n" + //
                "  </div>\r\n" + //
                "</body>";
        this.sendEmail(toemail, subject, message);
    }



    public void sendSignUpEmail(String toemail,String subject,String username,String code) throws MessagingException
    {
        String message="<body>\r\n" + //
                "  <h1>\r\n" + //
                "    Inyange Industry \r\n" + //
                "  </h1>\r\n" + //
                "  <div>\r\n" + //
                "    Hi Dear,<b>"+username+"</b>\r\n" + //
                "  </div>\r\n" + //
                "  <div>\r\n" + //
                "    your account has been created sucessfully \r\n" + //
                "  </div>\r\n" + //
                "  <div>\r\n" + //
                "  Password :"+code+" \r\n" + //
                "    email use : <b>"+toemail+"</b>\r\n" + //
                "  </div>\r\n" + //
                "</body>";
        this.sendEmail(toemail,subject,message);
    }
    public void eventForApprovalNotification
            (String toemail,String subject,String Tousername,
             String eventTitle,String invitedChoir,String choir,
             String invitedChoirLocation,String date
            ) throws MessagingException
    { SimpleDateFormat sFormat=new SimpleDateFormat("MMM-dd HH:mm)");
        String today=sFormat.format(new Date());
        String message="<body>\r\n" + //
                "  <h1>\r\n" + //
                "    Seventh-Day-Adventist\r\n" + //
                "  </h1>\r\n" + //
                "  <div>\r\n" + //
                "    Hi Dear,<b>"+Tousername+"</b>\r\n" + //
                " Choir "+invitedChoir+" from "+invitedChoirLocation+" needs for your approval </div>\r\n" + //
                "  <div>\r\n" + //
                " \r\n" + //
                "  </div>\r\n" + //
                "  <div>\r\n" + //
                "   to participate "+eventTitle+" that has been prepared by "+choir+" that will take place on "+date+"\r\n" + //
                "  </div>\r\n" + //
                "  <div>\r\n" + //
                "    email use : <b>"+toemail+"</b>\r\n" + //
                " <nav>   Done at : <b>"+today+"</b> </nav>\r\n" + //
                "  </div>\r\n" + //
                "</body>";
        this.sendEmail(toemail,subject,message);
    }

}
