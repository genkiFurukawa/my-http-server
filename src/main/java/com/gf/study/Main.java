package com.gf.study;

import com.gf.study.server.MyHttpServer;

public class Main {
    public static void main(String[] args) {
        MyHttpServer myHttpServer = new MyHttpServer();
        myHttpServer.run();
    }
}
