package Dialogue.ClientsRequest;

import java.util.regex.Pattern;

public class RequestSearch {
    public static ClientRequest.Request getRequest(String pattern) {
        if(Pattern.compile("\\bcd\\b").matcher(pattern).find()) return ClientRequest.Request.CD;
        if(Pattern.compile("\\bback\\b").matcher(pattern).find()) return ClientRequest.Request.BACK;
        if(Pattern.compile("\\bll\\b").matcher(pattern).find()) return ClientRequest.Request.LL;
        if(Pattern.compile("\\bdwn\\b").matcher(pattern).find()) return ClientRequest.Request.DWN;
        if(Pattern.compile("\\bupload\\b").matcher(pattern).find()) return ClientRequest.Request.UPLOAD;
        if(Pattern.compile("\\btree\\b").matcher(pattern).find()) return ClientRequest.Request.TREE;
        if(Pattern.compile("\\bdell\\b").matcher(pattern).find()) return ClientRequest.Request.DEL;
        if(Pattern.compile("\\bstop\\b").matcher(pattern).find()) return ClientRequest.Request.STOP;
        if(Pattern.compile("\\bfinish\\b").matcher(pattern).find()) return ClientRequest.Request.FINISH;
        return ClientRequest.Request.WRONG;
    }
}
