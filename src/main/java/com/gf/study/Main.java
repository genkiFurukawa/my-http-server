package com.gf.study;

import com.gf.study.server.MyHttpServer;

public class Main {
    public static void main(String[] args) {
        MyHttpServer myHttpServer = MyHttpServer.getInstance();
        myHttpServer.run();
    }
}
