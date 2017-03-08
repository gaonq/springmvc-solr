package com.corpautohome.controller;

import com.corpautohome.service.ICityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gaonq on 2017/3/3.
 */
@Controller
public class HomeController {
    @Resource
    ICityService cityService;
    //添加一个日志器
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @RequestMapping("/index")
    public ModelAndView Index(HttpServletRequest request, HttpServletResponse response,RedirectAttributes attr) throws Exception {
        logger.info("进入了index");
        logger.info(request.getParameter("username"));
        logger.info(request.getParameter("password"));
        logger.info("ceset");

        Date date = new Date();
        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
        logger.info("当前时间："+dateString);
        Integer count;
        count = cityService.getList();
        logger.info("总数量："+count);

        attr.addFlashAttribute("username", request.getParameter("username"));
        attr.addFlashAttribute("username", request.getParameter("password"));

        attr.addFlashAttribute("password","gaonengquan");
        return new ModelAndView("index");
    }



    @RequestMapping(value="/login")
    public String LoginView(HttpServletRequest request, Model model)
    {
        logger.info("进入了login");
        model.addAttribute("username","gaonengquan");
        return "login";
    }
    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, RedirectAttributes attr)
    {
        attr.addFlashAttribute("username",request.getParameter("username"));
        attr.addFlashAttribute("password",request.getParameter("password"));
        return "redirect:index";
    }
}
