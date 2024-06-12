package co.edu.uco.subscriptionprocessor.service.billing.impl;

import co.edu.uco.subscriptionprocessor.courier.MessageSenderBroker;
import co.edu.uco.subscriptionprocessor.domain.billing.Billing;
import co.edu.uco.subscriptionprocessor.domain.billing.BillingProcess;
import co.edu.uco.subscriptionprocessor.domain.period.Period;
import co.edu.uco.subscriptionprocessor.domain.person.Person;
import co.edu.uco.subscriptionprocessor.domain.plan.Plan;
import co.edu.uco.subscriptionprocessor.domain.subscription.Subscription;
import co.edu.uco.subscriptionprocessor.service.billing.BillingService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.Objects;
import java.util.Properties;

@Service
public class BillingServiceImpl implements BillingService {

    @Autowired
    private MessageSenderBroker messageSenderBroker;

    @Value("${billing.resources.paths.img.logo}")
    private String logoPath;

    @Value("${billing.resources.paths.img.qr}")
    private String qrPath;

    @Value("${billing.resources.paths.pdf.template}")
    private String reportTemplatePath;

    @Value("${billing.resources.paths.pdf.save-folder}")
    private String pdfSaveFolder;

    @Value("${mail.properties.username}")
    private String emailUser;

    @Value("${mail.properties.password}")
    private String emailPassword;

    @Value("${mail.properties.smtp.auth}")
    private String smtpAuth;

    @Value("${mail.properties.smtp.start-tls}")
    private String smtpStartTls;

    @Value("${mail.properties.smtp.host}")
    private String smtpHost;

    @Value("${mail.properties.smtp.port}")
    private String smtpPort;

    @Value("${mail.message.content.file.pdf}")
    private String attachedBillName;

    @Override
    public String createPdfBilling(BillingProcess billingProcess) {

        Subscription subscription = billingProcess.getSubscription();
        Period period = billingProcess.getPeriod();
        Person person = billingProcess.getPerson();
        Plan plan = billingProcess.getPlan();
        Double amount = billingProcess.getAmount();
        String filePath = "";

        try {

            URL logoUrl = getClass().getResource(logoPath);
            URL qrUrl = getClass().getResource(qrPath);
            URL reportTemplateUrl = getClass().getResource(reportTemplatePath);

            InputStream logo = getClass().getResourceAsStream(logoPath);
            InputStream qr = getClass().getResourceAsStream(qrPath);
            InputStream reportTemplate = getClass().getResourceAsStream(reportTemplatePath);

            if (logo != null && qr != null && reportTemplate != null) {

                JasperReport report = (JasperReport) JRLoader.loadObject(Objects.requireNonNull(getClass().getResource(reportTemplatePath)));

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("fullLogoBilling", logo);
                parameters.put("qr", qr);
                parameters.put("name", person.getName() + " " + person.getLastName());
                parameters.put("mobileNumber", person.getMobileNumber());
                parameters.put("documentType", person.getDocumentType());
                parameters.put("documentNumber", person.getIdentityDocument());
                parameters.put("email", person.getEmail());
                parameters.put("plan", plan.getName());
                parameters.put("price", plan.getPrice());
                parameters.put("period", period.getName());
                parameters.put("startDate", subscription.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                parameters.put("endDate", subscription.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                parameters.put("amount", amount);
                parameters.put("dueDate", subscription.getStartDate().plusDays(10).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters);
                filePath = pdfSaveFolder + person.getId() + ".pdf";
                JasperExportManager.exportReportToPdfFile(jasperPrint, filePath);

                logo.close();
                qr.close();
                reportTemplate.close();

                Billing billing = new Billing(amount, subscription.getStartDate(), subscription.getStartDate().plusDays(10), subscription.getId());
                messageSenderBroker.sendBillingResponseMessage(billing);

            } else {

                if (logoUrl == null) {
                    System.out.println("Logo not found at " + logoPath);
                }
                if (qrUrl == null) {
                    System.out.println("QR code not found at " + qrPath);
                }
                if (reportTemplateUrl == null) {
                    System.out.println("Report template not found at " + reportTemplatePath);
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return filePath;

    }

    @Override
    public void sendEmailWithAttachment(Person mailRecipient, String filePath) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.starttls.enable", smtpStartTls);
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailUser, emailPassword);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailUser));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailRecipient.getEmail()));
            message.setSubject(createMailSubject(mailRecipient));

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(createMailBody(mailRecipient));

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName(attachedBillName);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            message.setContent(multipart);

            Transport.send(message);

        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }

    }

    private String createMailSubject(Person person) {
        return person.getName() + " we have generated your invoice.";
    }

    private String createMailBody(Person person) {
        return person.getName() + " " + person.getLastName() + " you have received an electronic invoice.\n" +
                "\nAttached to this email you will find the document.";
    }

}
