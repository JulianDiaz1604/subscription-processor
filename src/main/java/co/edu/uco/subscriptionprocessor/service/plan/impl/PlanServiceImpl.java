package co.edu.uco.subscriptionprocessor.service.plan.impl;

import co.edu.uco.subscriptionprocessor.domain.plan.Plan;
import co.edu.uco.subscriptionprocessor.domain.plan.PlanListMessage;
import co.edu.uco.subscriptionprocessor.service.plan.PlanService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanServiceImpl implements PlanService {

    @Override
    public PlanListMessage getDiscountByPeriod(PlanListMessage planListMessage) {

        double discount = planListMessage.getDiscount();
        List<Plan> planList = planListMessage.getPlanList();

        planList.forEach(plan -> {
            double actualPrice = plan.getPrice();
            double discountedPrice = actualPrice - (actualPrice * (discount / 100));
            plan.setPrice(discountedPrice);
        });

        planListMessage.setPlanList(planList);

        return planListMessage;

    }

}
