package com.human.app;

import java.util.ArrayList;

public interface iBBS {
	void writebbs(String sTitle, String sContent, String sWriter, String sPasscode);
	ArrayList<BBSrec>getlist();
	BBSrec getpost(int bbs_id);//하나만 가져올때는 Array안씀
	void updatebbs(String sbbs_id, String sTitle, String sContent);
	void deletebbs(int bbs_id);
	
	 
}
