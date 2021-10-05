package com.human.app;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
		iBBS bbs=sqlSession.getMapper(iBBS.class);
		ArrayList<BBSrec> bbs_id=bbs.getlist();
		HttpSession session=hsr.getSession();
		String userid=(String)session.getAttribute("userid");
		System.out.println("ID1 ["+userid+"]");
		if (userid==null || userid.equals("")){//로그인 안함	
			model.addAttribute("logined","0");
		}else {//로그인 함	
			model.addAttribute("logined","1");
			model.addAttribute("userid",userid);
		}
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
	//수정내용 DB에 보내기(<<<form태그 안에 있는 submit버튼의 동작을 구현)
	@RequestMapping(value = "/updatue_view", method = RequestMethod.POST) 
	public String updateview(HttpServletRequest hsr) { 
		int bbs_id =Integer.parseInt(hsr.getParameter("bbs_id"));
		String title = hsr.getParameter("title");
		String content = hsr.getParameter("content");
		iBBS bbs=sqlSession.getMapper(iBBS.class);
		bbs.updatebbs(bbs_id, title, content);
		return "redirect:/list"; 
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
	//수정할 화면을 보여준다 (update.jsp)
	  @RequestMapping(value = "/update/{bbs_id}", method = RequestMethod.GET) 
	  public String updateBBS(@PathVariable("bbs_id") int bbs_id, Model model) { 		  
		  iBBS bbs=sqlSession.getMapper(iBBS.class);
		  BBSrec rec=bbs.getpost(bbs_id);
		  model.addAttribute("post",rec);
		  return "update";//리스트 이름을 가진 방향으로 돌린다?위에 리스트가 뜬다	  
	  }
	  
	  @RequestMapping(value = "/delete/{bbs_id}", method = RequestMethod.GET) 
	  public String deleteBBS(@PathVariable("bbs_id") int bbs_id, Model model) {
		  System.out.println("bbs_id ["+bbs_id+"]" );
		  iBBS bbs=sqlSession.getMapper(iBBS.class);
		  bbs.deletebbs(bbs_id);
		  return "redirect:/list";//리스트 이름을 가진 방향으로 돌린다?위에 리스트가 뜬다 
	  }
	  @RequestMapping(value = "/login")
		public String showlist() {
		  
			return "login";//jsp화일로 이동한
		}
	  //login.jsp submit버튼 기능 구현
	  @RequestMapping(value = "/logincheck",method = RequestMethod.POST)
		public String logincheck(HttpServletRequest hsr) {
		  String loginid=hsr.getParameter("loginid");
		  String passcode=hsr.getParameter("passcode");
			System.out.println("name["+passcode+"]");
			iMember mem=sqlSession.getMapper(iMember.class);
			int n=mem.checkUser(loginid,passcode);
			if(n==1) {
				HttpSession session=hsr.getSession();
				session.setAttribute("userid", loginid);
				return "redirect:/list";
			}else {//0
				return "redirect:/login";
			}
		}
	  @RequestMapping(value = "/sign")
		public String show() {
		  
			return "sign";//jsp화일로 이동한
		}
	  
		@RequestMapping(value = "/signin",method = RequestMethod.POST,
				produces = "application/text; charset=utf8")
		public String newbie(HttpServletRequest hsr)	{
			
			String name=hsr.getParameter("realname");
			System.out.println("name["+name+"]");
			String loginid=hsr.getParameter("loginid");
			String passcode=hsr.getParameter("password1");
			//mybatis xml 실행
			iBBS bbs=sqlSession.getMapper(iBBS.class);
			bbs.signin(name,loginid,passcode);
			
			return "redirect:/login";//jsp화일로 이동한다
		}	 
		 @RequestMapping("/logout")
			public String logout(HttpServletRequest hsr) {
			 HttpSession session=hsr.getSession();
			 session.invalidate();
			return "redirect:/list";//jsp화일로 이동한
		}
}
