package as1124.com.helloworld.ch3.list;

public class WechatMessage {

    public static final int TYPE_SENT = 1;

    public static final int TYPE_RECEIVED = 2;

    private String content;

    private int type;

    public WechatMessage(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
