package vn.kltn.KLTN.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.kltn.KLTN.repository.OrderRepository;

@Controller
@RequestMapping("/admin/statistics")
public class StatisticsController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public String showStatistics(Model model) {
        List<Object[]> revenueData = orderRepository.getRevenueByDay();
        List<Object[]> orderCountData = orderRepository.getOrderCountByDay();

        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.now();

        Integer todayRevenue = orderRepository.sumRevenueByDate(Date.valueOf(today));
        Integer todayOrders = orderRepository.countOrdersByDate(Date.valueOf(today));
        Integer monthRevenue = orderRepository.sumRevenueByMonth(currentMonth.getMonthValue());
        Integer monthOrders = orderRepository.countOrdersByMonth(currentMonth.getMonthValue());
        Integer allRevenue = orderRepository.sumAllRevenue();

        // Gán giá trị mặc định nếu null
        model.addAttribute("todayRevenue", todayRevenue != null ? todayRevenue : 0);
        model.addAttribute("todayOrders", todayOrders != null ? todayOrders : 0);
        model.addAttribute("monthRevenue", monthRevenue != null ? monthRevenue : 0);
        model.addAttribute("monthOrders", monthOrders != null ? monthOrders : 0);
        model.addAttribute("allRevenue", allRevenue != null ? allRevenue : 0);

        model.addAttribute("revenueData", revenueData);
        model.addAttribute("orderCountData", orderCountData);

        return "admin/statistics";
    }
}