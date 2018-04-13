package cn.yuki.controller;

import cn.yuki.entity.Plan;
import cn.yuki.service.HouseService;
import cn.yuki.service.ManagerService;
import cn.yuki.service.PlanService;
import cn.yuki.service.UserService;
import cn.yuki.service.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ManagerController {

    @Autowired
    private UserService userService;
    @Autowired
    private HouseService houseService;
    @Autowired
    private PlanService planService;
    @Autowired
    private ManagerService managerService;
//    @RequestMapping(value = "/manage", method = RequestMethod.GET)
//    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("manager/manage");
//        return mav;
//    }

    @RequestMapping("/deleteAccount")
    @ResponseBody
    public Result deleteAccount(HttpServletRequest request, HttpServletResponse response){
        String email = (String)request.getSession().getAttribute("email");
        if(email!=null&&userService.deleteAccount(email)== Result.Success){
            return Result.Success;
        }else{
            return Result.NotLogin;
        }
    }

    @RequestMapping(value = "/houseinfo", method = RequestMethod.GET)
    public ModelAndView houseinfo(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        String state = (String)request.getAttribute("state");
        mav.addObject("houseList",houseService.getAllHouse(state));
        mav.setViewName("manager/houseinfo");
        return mav;
    }

    @RequestMapping(value="/check", method = RequestMethod.GET)
    public ModelAndView check(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        mav.addObject("planList",planService.getFinishedPlanList());
        mav.setViewName("manager/check");
        return mav;
    }

    @RequestMapping(value="/fiinfo", method = RequestMethod.GET)
    public ModelAndView financeInfo(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        mav.addObject("statistic",managerService.getStatistic());
        mav.setViewName("manager/fiinfo");
        return mav;
    }

    @RequestMapping(value="/websiteincome", method = RequestMethod.POST)
    @ResponseBody
    public void insertWebsiteIncome(HttpServletRequest request){
        double money = Double.parseDouble(request.getParameter("money"));
        double checkmoney = Double.parseDouble(request.getParameter("checkmoney"));
        int houseId = Integer.parseInt(request.getParameter("houseId"));
        int planId = Integer.parseInt(request.getParameter("planId"));
        System.out.println(money+" "+houseId+" "+planId);
        managerService.insertWebsiteIncome(money,planId, houseId);
        Plan plan = planService.getPlanDetail(String.valueOf(planId));
        plan.setCheckMoney(checkmoney);
        planService.updatePlan(plan);
    }

    @RequestMapping(value="/passhouse", method = RequestMethod.POST)
    public void passHouse(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        int state = Integer.valueOf(request.getParameter("state"));
        houseService.passHouse(id,state);
    }

    @RequestMapping(value="/rejecthouse", method = RequestMethod.POST)
    public void rejectHouse(HttpServletRequest request, HttpServletResponse response){
        String id = request.getParameter("id");
        String reason = request.getParameter("reason");
        houseService.rejectHouse(id, reason);
    }
}
