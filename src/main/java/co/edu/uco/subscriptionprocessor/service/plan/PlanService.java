package co.edu.uco.subscriptionprocessor.service.plan;

import co.edu.uco.subscriptionprocessor.domain.plan.PlanListMessage;

public interface PlanService {

    PlanListMessage getDiscountByPeriod(PlanListMessage planListMessage);

}
