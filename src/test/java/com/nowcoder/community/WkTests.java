package com.nowcoder.community;

import java.io.IOException;

public class WkTests {

    public static void main(String[] args) {
        String cmd = "d:/Java/wkhtmltopdf/bin/wkhtmltoimage --quality 75  https://www.nowcoder.com d:/Java/data/wk-images/3.png";
        try {
            Runtime.getRuntime().exec(cmd);
            //这一行命令由操作系统执行，命令发给操作系统之后程序就马上往下执行，和操作系统并发的，因此程序马上就执行完了。
            //因为图片生成需要一定时间，所以我们是先看到ok，再看到图片生成了【注意】
            System.out.println("ok.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
