package com.human.app;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private SqlSession sqlSession;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	/*
	 * @RequestMapping(value = "/", method = RequestMethod.GET) public String
	 * home(Locale locale, Model model) {
	 * logger.info("Welcome home! The client locale is {}.", locale);
	 * 
	 * Date date = new Date(); DateFormat dateFormat =
	 * DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
	 * String formattedDate = dateFormat.format(date);
	 * model.addAttribute("serverTime", formattedDate );//데이터는 모델에 실어서 보낸다? return
	 * "home";//JSP화일명 } */
	
	/*
	 * @RequestMapping(value = "/list", method = RequestMethod.GET) public String
	 * selectBBS() { // return "list"; }
	 * 
	 * @RequestMapping(value = "/view", method = RequestMethod.GET) public String
	 * selectOneBBS() { // return "view"; }
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String brandNew() {
		return "new";
	}

	/*
	 * @RequestMapping(value = "/update_view", method = RequestMethod.GET) public
	 * String updateview() { return "update"; }
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String insertBBS(HttpServletRequest hsr) {
		String pTitle = hsr.getParameter("title");//new.jsp에서 설정한 이름과 같아야한다 앞에는 다르게 설정
		String pContent = hsr.getParameter("content");//new.jsp에서 설정한 이름과 같아야한다 앞에는 다르게 설정
		String pWriter = hsr.getParameter("writer");//new.jsp에서 설정한 이름과 같아야한다 앞에는 다르게 설정
		String pPasscode = hsr.getParameter("passcode");//new.jsp에서 설정한 이름과 같아야한다 앞에는 다르게 설정
		//아래 디버깅 하는법
		System.out.println("title ["+pTitle+"] content ["+pContent+"] write ["+pWriter+"] passcode ["+pPasscode+"]" );
		//insert into DB table
		iBBS bbs=sqlSession.getMapper(iBBS.class);
		bbs.writebbs(pTitle, pContent, pWriter, pPasscode);//ibbs.xml이랑ibbs.java와 같은 이름으로 쓴다
		return "redirect:/list";//리스트 이름을 가진 방향으로 돌린다?위에 리스트가 뜬다 
	}
	
	/*
	 * @RequestMapping(value = "/update", method = RequestMethod.POST) public String
	 * updateBBS() { // return "redirect:/list";//리스트 이름을 가진 방향으로 돌린다?위에 리스트가 뜬다 }
	 * 
	 * @RequestMapping(value = "/delete", method = RequestMethod.GET) public String
	 * deleteBBS() { // return "redirect:/list";//리스트 이름을 가진 방향으로 돌린다?위에 리스트가 뜬다 }
	 */
}
