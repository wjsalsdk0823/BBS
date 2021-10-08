package com.human.app;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Resource(name="uploadPath")
    String uploadPath;
	
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
		
	@RequestMapping(value = "/list/{pageno}",  method = RequestMethod.GET) 
	public String selectBBS(@PathVariable ("pageno") int pageno,
		HttpServletRequest hsr, Model model) { 	
		iBBS bbs=sqlSession.getMapper(iBBS.class);
		int start=20*(pageno-1)+1;
		int end=20*pageno;
		ArrayList<BBSrec> bbs_id=bbs.getlist(start,end);
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
		String nDirection ="";
		if(pageno==1) {
			nDirection="<a href='/app/list/"+(pageno+1)+"'>다음페이지</a>";
		} else {
			nDirection="<a href='/app/list/"+(pageno-1)+"'>이전페이지</a>"+
					"<a href='/app/list/"+(pageno+1)+"'>다음페이지</a>";
		}
		model.addAttribute("direct",nDirection);
		return "list";	
	}
	//아래 댓글 등록하면 댓글리스트 가져오기 뷰 jsp 54/57
	@RequestMapping(value = "/view/{bbs_id}",method = RequestMethod.GET) 
	public String selectOneBBS(@PathVariable("bbs_id") int bbs_id,
		HttpServletRequest hsr,Model model) {  
		//System.out.println("bbs_id ["+bbs_id+"]");
		//게시물 내용가져오기
		iBBS bbs=sqlSession.getMapper(iBBS.class);
		BBSrec post=bbs.getpost(bbs_id);
		model.addAttribute("post",post);
		//현재 사용자 아이디 가져오기
		HttpSession session=hsr.getSession();
		model.addAttribute("userid",session.getAttribute("userid"));
		//로그인 여부 확인
		String userid=(String)session.getAttribute("userid");
		//System.out.println("userid ["+userid+"]");
		if (userid==null || userid.equals("")){//로그인 안함	
			model.addAttribute("logined","0");
		}else {//로그인 함	
			model.addAttribute("logined","1");
			model.addAttribute("userid",userid);
		}
		//현재 게시물에 속한 댓글 리스트 가져오기
		iReply r=sqlSession.getMapper(iReply.class);
		ArrayList<Reply> replyList=r.getReplylist(bbs_id);
		model.addAttribute("reply_list",replyList);
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
		return "redirect:/list/1"; 
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String insertBBS(HttpServletRequest hsr,MultipartFile ufile) {
	    String fileName = ufile.getOriginalFilename();
        File target = new File(uploadPath, fileName);     
        //경로 생성
        if ( ! new File(uploadPath).exists()) {
            new File(uploadPath).mkdirs();
        }
        //파일 복사
        try {
            FileCopyUtils.copy(ufile.getBytes(), target);
           // mv.addObject("file", ufile);
        } catch(Exception e) {
            e.printStackTrace();
         //   mv.addObject("file", "error");
        }
		String pTitle = hsr.getParameter("title");//제목
		String pContent = hsr.getParameter("content");//내용
		String pWriter = hsr.getParameter("writer");//작성자
		
		//아래 디버깅 하는법
		System.out.println("title ["+pTitle+"] content ["+pContent+"] write ["+pWriter+"]" );
		//insert into DB table
		iBBS bbs=sqlSession.getMapper(iBBS.class);
		bbs.writebbs(pTitle, pContent, pWriter,fileName);//ibbs.xml이랑ibbs.java와 같은 이름으로 쓴다
		return "redirect:/list/1";//리스트 이름을 가진 방향으로 돌린다?--해당하는 RequestMapping으로 이동 위레 list로 이동 method실행
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
		  return "redirect:/list/1";//리스트 이름을 가진 방향으로 돌린다?위에 리스트가 뜬다 
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
				return "redirect:/list/1";
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
			return "redirect:/list/1";//jsp화일로 이동한
		}
		 //아래 댓글에 글 등록하면 등록하게하고 아래 내용나오게 만드는법 DB랑 연결 뷰.jsp 81/96
		 //댓글 삭제 수정
		 @RequestMapping(value = "/ReplyControl",method = RequestMethod.POST)
		 @ResponseBody
		 public String doReplyControl(HttpServletRequest hsr) {
			 System.out.println("doReplyControl");
			 String result="";
			 try {
				 String optype=hsr.getParameter("optype");	
				 if(optype.equals("add")) {//댓글등록(추가)	 
				 String reply_content=hsr.getParameter("content");
				 int bbs_id =Integer.parseInt(hsr.getParameter("bbs_id"));
				 HttpSession s=hsr.getSession();
				 String userid=(String) s.getAttribute("userid");
				 System.out.println("userid ["+userid+"]");
				 //mybitis호출
				 iReply reply=sqlSession.getMapper(iReply.class);
				 reply.add(bbs_id,reply_content,userid);
				 
			 }else if(optype.equals("delete")) {//댓글 쓴거 삭제
				 int reply_id=Integer.parseInt(hsr.getParameter("reply_id"));
				 iReply re=sqlSession.getMapper(iReply.class);
				 re.delete(reply_id);
				 
			 }else if(optype.equals("update")) {//댓글수정 
				 String content=hsr.getParameter("content");
				 int reply_id =Integer.parseInt(hsr.getParameter("reply_id"));
				 System.out.println("content ["+content+"]");
				 System.out.println("reply_id ["+reply_id+"]");
				 iReply re=sqlSession.getMapper(iReply.class);
				 re.update(content,reply_id);
			 }
			 result="ok";
		 }catch (Exception e) {
			 result="fail";
		 }  finally {
			 return "result";
		 }
			 
	 }

		
		 //아래는 로그인
		 public boolean checkLogin(HttpServletRequest hsr) {
		  HttpSession s=hsr.getSession();
		  String userid=(String) s.getAttribute("userid");
		  if(userid == null || userid.equals(null))return false;
		  return true;
		} 
}
