package com.human.app;

import java.util.ArrayList;

public interface iReply {

	void add( int bbs_id,String content,  String userid);
	ArrayList<Reply> getReplylist(int bbs_id);
	void delete(int reply_id);
	void update(String content, int reply_id);
}
