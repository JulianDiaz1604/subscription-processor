package co.edu.uco.subscriptionprocessor.service.billing.impl;

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
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class BillingServiceImpl implements BillingService {

    @Autowired
    private ServletContext servletContext;

    @Override
    public String createPdfBilling(BillingProcess billingProcess) {

        Subscription subscription = billingProcess.getSubscription();
        Period period = billingProcess.getPeriod();
        Person person = billingProcess.getPerson();
        Plan plan = billingProcess.getPlan();
        Double amount = billingProcess.getAmount();
        String filePath = "";

        try {

            String logoPath = "/billing/templates/img/full_logo_billing.png";
            String qrPath = "/billing/templates/img/qr.png";
            String reportTemplatePath = "/billing/templates/BillingServiceImpl.jasper";

            URL logoUrl = getClass().getResource(logoPath);
            URL qrUrl = getClass().getResource(qrPath);
            URL reportTemplateUrl = getClass().getResource(reportTemplatePath);

            if (logoUrl != null && qrUrl != null && reportTemplateUrl != null) {
                InputStream logo = getClass().getResourceAsStream(logoPath);
                InputStream qr = getClass().getResourceAsStream(qrPath);
                InputStream reportTemplate = getClass().getResourceAsStream(reportTemplatePath);

                JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResource(reportTemplatePath));

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
                parameters.put("dueDate", subscription.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters);
                JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/Users/diazj/OneDrive/Documentos/UCO/Software 3/facturas/" + person.getName() + ".pdf");

                logo.close();
                qr.close();
                reportTemplate.close();

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
            e.printStackTrace();
        }

        return filePath;

    }

}
