package com.human.app;

import java.util.ArrayList;

public interface iBBS {
	void writebbs(String sTitle, String sContent, String sWriter, String sPasscode);
	ArrayList<BBSrec>getlist();
	BBSrec getpost(int bbs_id);//하나만 가져올때는 Array안씀
	void deletebbs(int dbbs_id);
	void updatebbs(int bbs_id, String title, String content);
	 
}
