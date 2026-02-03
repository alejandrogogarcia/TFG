package es.udc.tfg.app.service.emailService;

import es.udc.tfg.app.model.CompanyInfo.CompanyInfo;
import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.service.companyInfoService.CompanyInfoService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CompanyInfoService companyInfoService;

    @Value("${app.mail.from}")
    private String from;

    @Value("${app.frontend.url}")
    private String frontendBaseUrl;

    @Async
    public void sendPasswordResetEmail(User user) throws MessagingException {

        String resetPath = "/resetPass?token=";

        String lang = user.getLanguage().toString();
        Locale locale = "GAL".equalsIgnoreCase(lang) ? new Locale("gl") :
                "ESP".equalsIgnoreCase(lang) ? new Locale("es") : Locale.ENGLISH;
        CompanyInfo company = companyInfoService.getCompanyInfo();

        String subject = messageSource.getMessage("project.mail.passwordReset.subject", null, locale);
        String hello = messageSource.getMessage("project.mail.passwordReset.hello", new Object[]{user.getFirstName()}, locale);
        String bodyText = messageSource.getMessage("project.mail.passwordReset.body", null, locale);
        String buttonText = messageSource.getMessage("project.mail.passwordReset.button", null, locale);
        String footerText = messageSource.getMessage("project.mail.passwordReset.footer", null, locale);

        String resetUrl = frontendBaseUrl + resetPath + user.getToken();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(from);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);

        String htmlContent =
                "<div style='background-color: #f4f4f4; padding: 30px; font-family: -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, Helvetica, Arial, sans-serif;'>" +
                        "<table align='center' width='600' style='background: #ffffff; border-radius: 12px; box-shadow: 0 4px 6px rgba(0,0,0,0.05); overflow: hidden;'>" +
                        "<tr>" +
                        "<td style='background-color: #0F766E; padding: 30px; text-align: center; color: #ffffff;'>" +
                        "<h1 style='margin: 0; font-size: 28px; letter-spacing: 1px; text-transform: uppercase;'>" + company.getName() + "</h1>" +
                        "</td>" +
                        "</tr>" +
                        "<tr>" +
                        "<td style='padding: 40px; color: #374151;'>" +
                        "<h3 style='font-size: 20px; margin-top: 0;'>" + hello + "</h3>" +
                        "<p style='font-size: 16px; line-height: 1.6; color: #4B5563;'>" + bodyText + "</p>" +
                        "<div style='text-align: center; margin: 40px 0;'>" +
                        "<a href='" + resetUrl + "' style='background-color: #0F766E; color: #ffffff; padding: 14px 30px; text-decoration: none; border-radius: 6px; font-weight: 600; display: inline-block; font-size: 16px;'>" +
                        buttonText +
                        "</a>" +
                        "</div>" +
                        "<tr>" +
                        "<td style='background: #F9FAFB; padding: 25px; text-align: center; font-size: 13px; color: #6B7280; border-top: 1px solid #E5E7EB;'>" +
                        "<strong style='color: #374151;'>" + company.getName() + "</strong><br>" +
                        company.getAddress() + " | " + company.getNif() + "<br><br>" +
                        "<span>" + footerText + "</span>" +
                        "</td>" +
                        "</tr>" +
                        "</table>" +
                        "</div>";

        helper.setText(htmlContent, true);
        mailSender.send(message);
    }
}