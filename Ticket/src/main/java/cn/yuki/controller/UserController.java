package cn.yuki.controller;

import cn.yuki.entity.Order;
import cn.yuki.entity.Purse;
import cn.yuki.entity.User;
import cn.yuki.service.OrderService;
import cn.yuki.service.PlanService;
import cn.yuki.service.UserService;
import cn.yuki.service.util.Result;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PlanService planService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Result result = userService.login(email, password);
        if(result == Result.Success){
            //登录成功
            request.getSession().setAttribute("email", email);
            request.getSession().setAttribute("user",userService.getUserInfo(email));
            mav.setViewName("user/index");
        }else if(result == Result.HaveDeleted){
            //没有验证
            mav.addObject("text","账号已注销");
            mav.setViewName("user/login");
        } else if(result == Result.NotActivated){
            //没有验证
            mav.addObject("text","账号尚未验证");
            mav.setViewName("user/login");
        }else if(result == Result.WrongPassword || result == Result.NotRegistered){
            //账号或密码错误
            mav.addObject("text", "账号或密码错误");
            mav.setViewName("user/login");
        }
        return mav;
    }

    @RequestMapping("/register")
    public ModelAndView register(HttpServletRequest request, HttpServletResponse response){
        String action = request.getParameter("action");
        ModelAndView mav = new ModelAndView();
        if("register".equals(action)){
            //注册
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            Result result = userService.processRegister(email, password);
            if(result == Result.HaveRegistered){
                mav.addObject("text", "账号已注册");
                mav.setViewName("user/register");
            }else if(result == Result.Success) {
                mav.addObject("text", "注册成功，请前往邮箱验证");
                mav.setViewName("user/login");
            }
        }else if("activate".equals(action)){
            //激活
            String email = request.getParameter("email");
            String validateCode = request.getParameter("validateCode");
            userService.processActivate(email, validateCode);
            mav.setViewName("user/index");
        }else{
            mav.setViewName("user/register");
        }
        return mav;
    }

    @RequestMapping(value="/profile", method =RequestMethod.POST)
    public ModelAndView profile(HttpServletRequest request, HttpServletResponse response){
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String rePassword = request.getParameter("rePassword");
        System.out.println("usernmae: " + username);
        userService.reviseProfile(email,rePassword,username);
        //更新user信息
        request.getSession().setAttribute("user",userService.getUserInfo(email));
        ModelAndView mav = new ModelAndView();
        mav.setViewName("user/profile");
        return mav;
    }

    @RequestMapping(value = "/profile",method = RequestMethod.GET)
    public ModelAndView getProfile(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null) {
            mav.addObject("user", userService.getUserInfo(user.getEmail()));
        }
        mav.setViewName("user/profile");
        return mav;
    }

    @RequestMapping(value = "/planlist", method = RequestMethod.GET)
    public ModelAndView planlist(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        String type = request.getParameter("type");
        mav.addObject("planList", planService.getPlan(type));
        mav.setViewName("user/planlist");
        return mav;
    }

    @RequestMapping(value = "/plandetail",method = RequestMethod.GET)
    public ModelAndView plandetail(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        String id = request.getParameter("id");
        if(id == null){
            mav.setViewName("user/planlist");
            return mav;
        }else{
            mav.setViewName("user/plandetail");
            mav.addObject("plan", planService.getPlanDetail(id));
            return mav;
        }
    }


    @RequestMapping(value = "/seat", method = RequestMethod.GET)
    public ModelAndView seat(HttpServletRequest request, HttpServletResponse response){
        String planId = request.getParameter("planId");
        User user = (User)request.getSession().getAttribute("user");
        ModelAndView mav = new ModelAndView();
        if(planId!=null){
            mav.addObject("plan", planService.getPlanDetail(planId));
        }
        if(user!=null){
            mav.addObject("user", userService.getUserInfo(user.getEmail()));
        }
        mav.setViewName("user/seat");
        return mav;
    }

    @RequestMapping(value = "/makeorder", method = RequestMethod.POST)
    @ResponseBody
    public int makeOrder(HttpServletRequest request, HttpServletResponse response){
        Double total = Double.parseDouble(request.getParameter("total"));
        Double actualPay = Double.parseDouble(request.getParameter("actualPay"));
        String seatID = request.getParameter("seatID");
        int planId = Integer.parseInt(request.getParameter("planId"));
        String planName = request.getParameter("planName");
        User user = (User)request.getSession().getAttribute("user");
        int userId = user.getId();
        String username=  user.getUsername();
        String activity = "会员"+user.getVIP().getLevel()+"级 "+"享受"+user.getVIP().getDiscount()*100+"折优惠\n";
        double integration = Double.parseDouble(request.getParameter("integration"));
        int counter = Integer.parseInt(request.getParameter("counter"));
        int orderid = orderService.makeOrder(planId, planName, userId, username, total, actualPay, activity,seatID, integration, counter);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId", orderid);
        jsonObject.put("message","成功");
        return orderid;
    }

    @RequestMapping(value = "/validatepay", method = RequestMethod.POST)
    @ResponseBody
    public Result validate(HttpServletRequest request, HttpServletResponse response){
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String password = request.getParameter("password");
        //这里actualPay表示实际支付
        Double actualPay = Double.parseDouble(request.getParameter("actualPay"));
        int userId = ((User)request.getSession().getAttribute("user")).getId();
        Result checkPayResult = userService.checkPay(userId,password,actualPay);
        if(checkPayResult == Result.Success){
            Order order = orderService.getOrderInfo(orderId);
            Purse purse = userService.getPurseInfo(userId);
            if(order!=null && purse!=null) {
                return orderService.payOrder(order, purse.getId());
            }else{
                return Result.NoOrderFound;
            }

        }else{
            return checkPayResult;
        }
    }

    @RequestMapping(value = "/unavailable", method = RequestMethod.GET)
    @ResponseBody
    public String getUnavailableSeats(HttpServletRequest request, HttpServletResponse response){
        int planId = Integer.parseInt(request.getParameter("planId"));
        return orderService.getUnavailableSeats(planId);
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public ModelAndView getOrderList(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        User user = (User)request.getSession().getAttribute("user");
        String state = request.getParameter("state");
        if(state == null || state.equals("")){
            mav.addObject("orderList",orderService.getOrderList(user.getId()));
        }else{
            mav.addObject("orderList", orderService.getOrderListByState(user.getId(),state));
        }
        mav.setViewName("user/order");
        return mav;
    }

    @RequestMapping(value = "/purse", method = RequestMethod.GET)
    public ModelAndView getPurse(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        User user = (User)request.getSession().getAttribute("user");
        mav.addObject("purse",userService.getPurseInfo(user.getId()));
        mav.addObject("consumeDetail",userService.getUserFinanceDetail(user.getId(),0));
        mav.addObject("reserveDetail",userService.getUserFinanceDetail(user.getId(),1));
        mav.addObject("backDetail",userService.getUserFinanceDetail(user.getId(),2));
        mav.addObject("consume",-userService.getUserFinance(user.getId(),0));
        mav.addObject("reserve",-userService.getUserFinance(user.getId(),1));
        mav.addObject("back",userService.getUserFinance(user.getId(),2));
        mav.setViewName("user/purse");
        return mav;
    }

    @RequestMapping(value = "/cancleOrder", method = RequestMethod.POST)
    @ResponseBody
    public Result cancleOrder(HttpServletRequest request, HttpServletResponse response){
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        return orderService.cancleOrder(orderId);
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    @ResponseBody
    public Result book(HttpServletRequest request){
        int  planId = Integer.valueOf(request.getParameter("planId"));
        String planName = request.getParameter("planName");
        User user = ((User)request.getSession().getAttribute("user"));
        Purse purse = userService.getPurseInfo(user.getId());
        String password = request.getParameter("password");
        Double payment = Double.parseDouble(request.getParameter("payment"));
        String planTime = request.getParameter("planTime");
        int num = Integer.parseInt(request.getParameter("num"));
        String type = request.getParameter("type");
        Result result = userService.checkPay(user.getId(), password, payment);
        if(result == Result.Success) {
            return orderService.reserve(planId, planName, user.getId(), user.getUsername(), purse.getId(), payment, planTime, num, type);
        }else{
            return result;
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response){
        request.getSession().removeAttribute("user");
        return "user/login";
    }


}
