package co.edu.uco.subscriptionprocessor.service.plan;

import co.edu.uco.subscriptionprocessor.domain.plan.Plan;
import co.edu.uco.subscriptionprocessor.domain.plan.PlanListMessage;

import java.util.List;

public interface PlanService {

    PlanListMessage getDiscountByPeriod(PlanListMessage planListMessage);

}
