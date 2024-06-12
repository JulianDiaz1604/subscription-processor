package co.edu.uco.subscriptionprocessor.service.billing;

import co.edu.uco.subscriptionprocessor.domain.billing.BillingProcess;
import co.edu.uco.subscriptionprocessor.domain.person.Person;

public interface BillingService {

    String createPdfBilling(BillingProcess billingProcess);

    void sendEmailWithAttachment(Person mailRecipient, String filePath);

}
