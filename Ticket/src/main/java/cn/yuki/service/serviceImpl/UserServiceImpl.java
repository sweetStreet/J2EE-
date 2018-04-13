package cn.yuki.service.serviceImpl;

import cn.yuki.dao.PurseDAO;
import cn.yuki.dao.UserDAO;
import cn.yuki.dao.UserFinanceDAO;
import cn.yuki.entity.Purse;
import cn.yuki.entity.User;
import cn.yuki.entity.UserFinance;
import cn.yuki.service.UserService;
import cn.yuki.service.util.MD5;
import cn.yuki.service.util.MailUtil;
import cn.yuki.service.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;
    @Autowired
    PurseDAO purseDAO;
    @Autowired
    UserFinanceDAO userFinanceDAO;

    @Transactional
    public Result login(String email, String password) {

        User user = userDAO.find(email);

        if(user == null){
            //用户尚未注册
            return Result.NotRegistered;
        }else if(user.getIsDeleted()==1){
            return Result.HaveDeleted;
        }else if(user.getActivate()==0){
            //没有激活
            return Result.NotActivated;
        }else if(user.getPassword().equals(MD5.MD5(password))){
            return Result.Success;
        }else {
            return Result.WrongPassword;
        }
    }

    @Transactional
    public Result processRegister(String email, String password) {
        User user = userDAO.find(email);
        if(user!=null){
            //用户已注册
            return Result.HaveRegistered;
        }

        user = new User();
        user.setEmail(email);
        user.setRegisterTime(new Date());
        user.setPassword(MD5.MD5(password));
        user.setUsername("");
        String validateCode = MD5.MD5(email);
        user.setValidateCode(validateCode);
        int id = userDAO.save(user);

        String[] array = new String[]{user.getEmail()};

        String subject = "请激活你的Tickets账号";

        String link = "http://127.0.0.1:8080/register?action=activate&email="+email+"&validateCode="+validateCode;
        String html = "<html><head>" +
                "</head><body>" +
                "<div>Tickets的新成员你好，欢迎来到Tickets</div>" +
                "<div>请点击此链接以确认您的邮件地址</div>"+
                "<a href="+link+">点击验证</a>"+
                "<div>48小时后过期</div>"+
                "</body></html>";

        MailUtil.htmlMail(array,subject,html);
        return Result.Success;
    }

    @Transactional
    public Result processActivate(String email, String validateCode){
        User user = userDAO.find(email);
        if(user!=null){
           if(user.getActivate()==0){
               Date currentTime = new Date();
               //验证链接是否过期
               currentTime.before(user.getRegisterTime());
               if(currentTime.before(user.getLastActivateTime())){
                   //验证码是否正确
                   if(validateCode.equals(user.getValidateCode())){
                       //激活成功
                       user.setActivate(1);
                       userDAO.update(user);
                       return Result.Success;
                   }else{
                       //激活码不正确
                       return Result.WrongValidateCode;
                   }
               }else{
                   //激活码过期
                   return Result.OutDatedValidateCode;
               }
           }else{
               //邮箱已激活，请登录
               return Result.HaveValidated;
           }
        }else{
            //邮箱未注册
            return Result.NotRegistered;
        }
    }


    public User getUserInfo(String email){
        return userDAO.find(email);
    }

    @Transactional
    public Result reviseProfile(String email, String rePassword, String username){
        if(email==null){
            System.out.println(email);
            return Result.NotRegistered;
        }
        User user = userDAO.find(email);
        if(username!=null&&!username.equals(user.getUsername())){
            user.setUsername(username);
        }
        if(rePassword!=null && !rePassword.equals("")){
            user.setPassword(MD5.MD5(rePassword));
        }
        userDAO.update(user);
        return Result.Success;
    }

    @Transactional
    public Result deleteAccount(String email){
        User user = userDAO.find(email);
        user.setIsDeleted(1);
        userDAO.update(user);
        return Result.Success;
    }

    @Override
    public Result checkPay(int userId, String password, double total) {
        Purse purse = purseDAO.findByUserId(userId);
        if(purse!=null){
            //这里暂时没有做加密处理
            if(purse.getPassword().equals(password)){
                if(purse.getRemain()>=total) {
                    return Result.Success;
                }else{
                    return Result.NoEnoughRemains;
                }
            }else{
                return Result.WrongPassword;
            }
        }
        return Result.WrongAccount;
    }

    @Override
    public Purse getPurseInfo(int userId) {
        return purseDAO.findByUserId(userId);
    }

    @Override
    public List getUserFinanceDetail(int userId, int state) {
        return userFinanceDAO.userFind(userId,state);
    }

    @Override
    public double getUserFinance(int userId, int state) {
        return userFinanceDAO.userFindTotal(userId, state);
    }


}
