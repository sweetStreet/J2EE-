package cn.yuki.controller;

import cn.yuki.entity.House;
import cn.yuki.entity.Plan;
import cn.yuki.entity.User;
import cn.yuki.service.HouseService;
import cn.yuki.service.OrderService;
import cn.yuki.service.PlanService;
import cn.yuki.service.UserService;
import cn.yuki.service.util.Result;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class HouseController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private PlanService planService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/apply", method = RequestMethod.GET)
    public ModelAndView apply(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("house/apply");
        return mav;
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public ModelAndView submitApply(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        String address = request.getParameter("location");
        String rows = request.getParameter("rows");
        String lines = request.getParameter("lines");
        String email = request.getParameter("email");
        houseService.register(name, address, rows, lines, email);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("house/apply");
        return mav;
    }

    @RequestMapping(value = "/planmake", method = RequestMethod.GET)
    public ModelAndView planmake(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        String seatmap = request.getParameter("seatmap");
        mav.addObject("seatmap",seatmap);
        mav.setViewName("house/planmake");
        return mav;
    }

    @RequestMapping(value = "/planmake", method = RequestMethod.POST)
    public String fileUpload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request, HttpServletResponse response){
        long startUploadTime=System.currentTimeMillis();   //获取开始时间
        String fileLocation="";
        if(!file.isEmpty()){
            try {
                //定义输出流     file.getOriginalFilename()为获得文件的名字
                String filename = file.getOriginalFilename();
                String[] arrays = filename.split("\\.");
                System.out.println("getContentType() "+file.getContentType());
                filename = String.valueOf(System.currentTimeMillis())+"."+arrays[arrays.length-1];
                fileLocation = "/Users/liuyu/Downloads/Ticket/src/main/webapp/poster/"+filename;
                FileOutputStream os = new FileOutputStream(fileLocation);
                InputStream in = file.getInputStream();
                int b = 0;
                while((b=in.read())!=-1){ //读取文件
                    os.write(b);
                }
                os.flush(); //关闭流
                in.close();
                os.close();
                fileLocation="http://127.0.0.1:8080/poster/"+filename;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long endUploadTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("上传文件共使用时间："+(endUploadTime-startUploadTime));

        String name = request.getParameter("name");
        String endTime = request.getParameter("endTime");
        String startTime = request.getParameter("startTime");
        String type = request.getParameter("type");
        String description = request.getParameter("description");
        //生成价格String
        String price = "";
        String price1 = request.getParameter("price1");
        String price2 = request.getParameter("price2");
        String price3 = request.getParameter("price3");
        String price4 = request.getParameter("price4");
        if(price1!=null && !price1.equals("")) {
            price += price1+",";
        }
        if(price2!=null && !price2.equals("")) {
            price += price2+",";
        }
        if(price3!=null && !price3.equals("")) {
            price += price3+",";
        }
        if(price4!=null && !price4.equals("")) {
            price += price4+",";
        }
        price = price.substring(0,price.length()-1);


        //生成座位图
        String seatmap = "";
        String row1 = request.getParameter("row1");
        String row2 = request.getParameter("row2");
        String row3 = request.getParameter("row3");
        String row4 = request.getParameter("row4");
        House house = (House)request.getSession().getAttribute("house");
        if(house == null){
            System.out.println("缺少house信息");
        }
        int line = Integer.parseInt(house.getLine());

        String seatInRow = "";
        if(row1 != null && !row1.equals("")){
            for(int i=0;i<line;i++){
                seatInRow += "a";
            }

            int row = Integer.parseInt(row1);
            for(int i=0;i<row;i++){
                seatmap+=seatInRow+",";
            }

//            System.out.println(seatmap);
            seatInRow = "";
        }
        if(row2 != null &&!row2.equals("")){
            for(int i=0;i<line;i++){
                seatInRow += "b";
            }
            int row = Integer.parseInt(row2);
            for(int i=0;i<row;i++){
                seatmap+=seatInRow+",";
            }

//            System.out.println(seatmap);
            seatInRow = "";
        }
        if(row3 != null&& !row3.equals("")){
            for(int i=0;i<line;i++){
                seatInRow += "c";
            }
            int row = Integer.parseInt(row3);
            for(int i=0;i<row;i++){
                seatmap+=seatInRow+",";
            }

//            System.out.println(seatmap);
            seatInRow = "";
        }
        if(row4 != null && !row4.equals("")){
            for(int i=0;i<line;i++){
                seatInRow += "d";
            }
            int row = Integer.parseInt(row4);
            for(int i=0;i<row;i++){
                seatmap+=seatInRow+",";
            }
//            System.out.println(seatmap);
        }
        seatmap = seatmap.substring(0,seatmap.length()-1);
        planService.makePlan(name, startTime, endTime, type, description, price,fileLocation,String.valueOf(house.getId()),seatmap);
        return "house/houseindex";
    }

    @RequestMapping(value="/hlogin", method = RequestMethod.POST)
    public ModelAndView houseLogin(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        Result result = houseService.login(account, password);
        if(result == Result.Success){
            House house = houseService.getHouseInfo(account);
            request.getSession().setAttribute("house", house);
            mav.addObject("house",house);
            mav.setViewName("house/houseindex");
        }else{
            mav.setViewName("house/house");
        }
        return mav;
    }

    @RequestMapping(value = "/house", method = RequestMethod.GET)
    public ModelAndView house(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("house/house");
        return mav;
    }

    @RequestMapping(value = "/houseindex", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("house/houseindex");
        return mav;
    }

    //查看场馆订单信息
    @RequestMapping(value = "/houseorder", method = RequestMethod.GET)
    public ModelAndView order(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        String state = request.getParameter("state");
        House house = (House)request.getSession().getAttribute("house");
        if(state == null || state.equals("")) {
            List orders = orderService.houseGetOrderList(house.getId());
            mav.addObject("orderList", orders);
        }else{
            List orders = orderService.houseGetOrderListByState(house.getId(),state);
            mav.addObject("orderList", orders);
        }

        mav.setViewName("house/houseorder");
        return mav;
    }

    @RequestMapping(value = "/houseplanlist", method = RequestMethod.GET)
    public ModelAndView planList(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        House house = (House)request.getSession().getAttribute("house");
        mav.addObject("planList", planService.getPlanByHouse(house.getId()));
        mav.setViewName("house/houseplanlist");
        return mav;
    }


    @RequestMapping(value = "/houseplandetail", method = RequestMethod.GET)
    public ModelAndView planDetail(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        String planid = request.getParameter("id");
        mav.addObject("plan",planService.getPlanDetail(planid));
        mav.setViewName("house/houseplandetail");
        return mav;
    }

    @RequestMapping(value = "/orderonsite", method = RequestMethod.POST)
    @ResponseBody
    public Result orderOnSite(HttpServletRequest request, HttpServletResponse response){
        int planId = Integer.parseInt(request.getParameter("planId"));
        String planName = request.getParameter("planName");
        int userId = Integer.parseInt(request.getParameter("userId"));
        String username = request.getParameter("username");
        double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
        double payment = Double.parseDouble(request.getParameter("payment"));
        String activity = request.getParameter("activity");
        String seats = request.getParameter("seats");
        House house = (House)request.getSession().getAttribute("house");
        return orderService.orderOnSite(planId, planName, userId, username, totalPrice, payment, activity, seats, house.getId());
    }

    @RequestMapping(value = "/validateemail", method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ResponseBody
    public String validateEmail(HttpServletRequest request, HttpServletResponse response){
        String email = request.getParameter("email");
        User user = userService.getUserInfo(email);
        JSONObject result = new JSONObject();
        if(user == null){
            result.put("result","NotExist");
        }else{
            result.put("result","Success");
            result.put("id", user.getId());
            result.put("discount",user.getVIP().getDiscount());
            result.put("level",user.getVIP().getLevel());
            result.put("username",user.getUsername());
        }
        return result.toString();
    }

    @RequestMapping(value = "/housefinance", method = RequestMethod.GET)
    public ModelAndView houseFinance(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        House house = (House)request.getSession().getAttribute("house");
        mav.addObject("statistic",houseService.getStatistic(house.getId()));
        mav.setViewName("house/housefinance");
        return mav;
    }

    @RequestMapping(value = "/housecheckin", method = RequestMethod.GET)
    public ModelAndView houseCheckin(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("house/checkin");
        return mav;
    }

    @RequestMapping(value = "/checkin", method = RequestMethod.POST,produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String houseCheckinResult(HttpServletRequest request, HttpServletResponse response){
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Result result =  orderService.checkIn(orderId);
        String message = result.toString();
        if(result == Result.Success) {
            Plan plan = planService.getPlanDetail(String.valueOf(orderService.getOrderInfo(orderId).getPlanID()));
            message = "计划名称：" + plan.getName() + "\n" + "开始时间" + plan.getStartTime();
        }
        return message;

    }

    @RequestMapping(value = "/revisehouse", method = RequestMethod.POST)
    @ResponseBody
    public Result reviseHouse(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String row = request.getParameter("row");
        String line = request.getParameter("line");
        String email = request.getParameter("email");
        String houseId = request.getParameter("houseId");
        return houseService.reviseHouse(houseId, name,location,row,line,email);
    }


    @RequestMapping(value = "/revisehousepassword", method = RequestMethod.POST)
    @ResponseBody
    public Result reviseHousePassword(HttpServletRequest request, HttpServletResponse response){
        String password = request.getParameter("password");
        String houseId = request.getParameter("houseId");
        return houseService.reviseHousePassword(houseId, password);
    }
}
