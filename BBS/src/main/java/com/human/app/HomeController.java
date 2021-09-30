package com.human.app;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	
	@RequestMapping(value = "/list",  method = RequestMethod.GET) 
	public String selectBBS(HttpServletRequest hsr, Model model) { 	
		//String listbbs_id = hsr.getParameter("bbs_id");
		//String listTitle = hsr.getParameter("title");
		//String listContent = hsr.getParameter("Content");
		//String listWriter = hsr.getParameter("Writer");
		//String listcreated = hsr.getParameter("created");
		//String listupdated = hsr.getParameter("updated");
		iBBS bbs=sqlSession.getMapper(iBBS.class);
		ArrayList<BBSrec> bbs_id=bbs.getlist();
		model.addAttribute("list",bbs_id);
		return "list";
	}
	
//
//	@RequestMapping(value = "/list", method = RequestMethod.GET) 
//	public String selectBBS() { //  
//		  return "list";
//	}
//	   
	@RequestMapping(value = "/view/{bbs_id}",method = RequestMethod.GET) 
	public String selectOneBBS(@PathVariable("bbs_id") int bbs_id, Model model) {  
		System.out.println("bbs_id ["+bbs_id+"]");
		iBBS bbs=sqlSession.getMapper(iBBS.class);
		BBSrec post=bbs.getpost(bbs_id);
		model.addAttribute("post",post);
		  return "view";//뷰한테 넘겨줄때는 모델 써야한다
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String brandNew() {
		return "new";//jsp화일로 이동한다
	}
	
	@RequestMapping(value = "/update_view", method = RequestMethod.GET) 
	public String updateview() { 
		return "update"; 
	}
	 
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
		return "redirect:/list";//리스트 이름을 가진 방향으로 돌린다?--해당하는 RequestMapping으로 이동 위레 list로 이동 method실행
	}
	
	  @RequestMapping(value = "/updatebbs", method = RequestMethod.POST) 
	  public String updateBBS(HttpServletRequest hsr) { 
		  String sbbs_id = hsr.getParameter("bbs_id");
		  String sTitle = hsr.getParameter("title");
		  String sContent = hsr.getParameter("content");
		  iBBS bbs=sqlSession.getMapper(iBBS.class);
		  bbs.updatebbs(sbbs_id,sTitle,sContent);
		  return "redirect:/list";//리스트 이름을 가진 방향으로 돌린다?위에 리스트가 뜬다	  
	  }
	  
	  @RequestMapping(value = "/deletebbs", method = RequestMethod.GET) 
	  public String deleteBBS(HttpServletRequest hsr) { 
		  int dbbs_id = Integer.parseInt(hsr.getParameter("bbs_id"));
		  iBBS bbs=sqlSession.getMapper(iBBS.class);
		  bbs.deletebbs(dbbs_id);
		  return "redirect:/list";//리스트 이름을 가진 방향으로 돌린다?위에 리스트가 뜬다 
	  }
	 
}
