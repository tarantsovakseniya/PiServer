package Dialogue.ClientsRequest;

import java.util.regex.Pattern;

public interface ClientRequest {

    enum Request{
        //templates
        LL(Pattern.compile("\\bll \\b")),
        TREE(Pattern.compile("\\tree \\b")),
        CD(Pattern.compile("\\bcd \\b")),
        BACK(Pattern.compile("\\bback \\b")),
        DWN(Pattern.compile("\\bdwn \\b")),
        UPLOAD(Pattern.compile("\\bupload \\b")),
        DEL(Pattern.compile("\\bdell \\b")),
        STOP(Pattern.compile("\\bstop \\b")),
        FINISH(Pattern.compile("\\bfinish \\b"));

        private Pattern p;

        Request(Pattern p) {
            this.p = p;
        }

        public Pattern getP() {
            return p;
        }
    }
}
