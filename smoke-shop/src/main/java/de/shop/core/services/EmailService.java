package de.shop.core.services;

import de.shop.core.components.DateData;
import de.shop.core.components.LanguageResolver;
import de.shop.core.exceptions.EmailServiceException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Класс сервис, который занимается отправкой email
 */
@Service
public class EmailService {
    private JavaMailSender mailSender; // класс для работы с email
    private TemplateEngine templateEngine; // класс для работы с шаблонами
    private LanguageResolver lang; // Языковой класс
    private String template; // Имя шаблона
    private String name_app, adresse, contact_email, contact_tel, work_t, send_mode; // данные для письма
    private DateData date; // дата данных
    private ServerService server; // сервер сервис

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine, LanguageResolver lang,
                        @Value("${spring.application.name}") String name_app, // имя приложения
                        @Value("${spring.email_service.adresse}") String adresse, // адрес магазина, если есть
                        @Value("${spring.email_service.contact_email}") String contact_email, // email для связи
                        @Value("${spring.email_service.contact_tel}") String contact_tel, // телефон для связи
                        @Value("${spring.email_service.work_time}") String work_t, // время работы
                        @Value("${spring.email_service.send_mode}") String send_mode, // тип  отправки
                        DateData date, // дата и время
                        ServerService server // про сервер
    ) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.lang = lang;
        this.name_app = name_app;
        this.adresse = adresse;
        this.contact_email = contact_email;
        this.contact_tel = contact_tel;
        this.work_t = work_t;
        this.send_mode = send_mode;
        this.date = date;
        this.server = server;
    }

    /**
     * Метод, в котором можно указать шаблон для работы
     *
     * @param t адрес к шаблону
     */
    public void setTemplate(String t) {
        this.template = t;
    }

    /**
     * Метод занимается отправкой письма на реальные адреса или сохраняет в файл на локальной машине.
     *
     * @param to      Адрес, кому отправляем
     * @param title   Заголовок письма
     * @param message текст письма
     * @param html    содержимое html
     * @param mode    режим. true, сохраняем в файл. false, отправляем на адрес
     * @param p       языковой пакет
     * @return возвращает boolean
     * @throws EmailServiceException перехватчик сообщений
     */
    private boolean sendData(String to, String title, String message, String html, boolean mode, Properties p) throws EmailServiceException {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(title);
            mimeMessageHelper.setText(html, true);
            if (mode == true) {
                File dir = new File("email");
                if (!dir.exists()) {
                    if (!dir.mkdirs()) {
                    }
                }
                String datepath = date.format("dd.MM.yyyy-HH.mm.ss");
                try (OutputStream outputStream = new FileOutputStream("email/mail-" + datepath + ".eml")) {
                    mimeMessage.writeTo(outputStream);
                }
            } else {
                mailSender.send(mimeMessage);
            }
            return true;
        } catch (MessagingException | IOException e) {
            throw new EmailServiceException(((String) p.get("no.send")));
        }
    }

    /**
     * Метод принимает данные для отправки писем
     *
     * @param to      адрес
     * @param title   заголовок
     * @param message сообщение
     * @param vars    дополнительные параметры, которые будут использоваться в шаблонах
     * @throws EmailServiceException перехвад ошибок
     */
    public void send(String to, String title, String message, Map<String, String> vars) throws EmailServiceException {
        Properties p = lang.load("email_manager", "words");

        String work_time = ((String) p.get("days")) + " " + work_t;
        Context context = new Context();
        context.setVariable("title", title);
        context.setVariable("message", message);
        context.setVariable("name_app", name_app);
        context.setVariable("adresse", adresse);
        context.setVariable("contact_email", contact_email);
        context.setVariable("email", to); // email пользователя
        context.setVariable("work_time", work_time);
        context.setVariable("contact_tel", contact_tel);
        context.setVariable("domain", server.getSite());
        if (vars != null) {
            for (Map.Entry<String, String> entry : vars.entrySet()) {
                context.setVariable(entry.getKey(), entry.getValue());
            }
        }
        String html = templateEngine.process("/" + lang.getCurrentLang() + "/" + template, context);
        if (send_mode.equals("local")) {
            sendData(to, title, message, html, true, p);
        } else if (send_mode.equals("network")) {
            sendData(to, title, message, html, false, p);
        }


    }

}
