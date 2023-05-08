package com.gf.study.server;

import com.gf.study.server.exception.ClientDisconnectedException;
import com.gf.study.server.exception.IllegalRequestLineException;
import com.gf.study.server.request.HttpRequest;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MyHttpServer {
    public static final String CRLF = "\r\n";

    // NOTE: シングルトンにする
    private static MyHttpServer myHttpServer = new MyHttpServer();

    private MyHttpServer() {
    }

    public static MyHttpServer getInstance() {
        return myHttpServer;
    }

    // NOTE: サーバ側は4つのシステムコールが必要
    // NOTE: ↓ 呼ばれるシステムコール
    // NOTE: socket(2) => bind(2) => listen(2) => accept(2)
    public void run() {
        // リクエストを捌いたら、次のリクエストを受け付ける

        try (ServerSocket serverSocket = new ServerSocket()) {
            // ポート番号を指定してサーバー側のソケットを作成
            serverSocket.bind(new InetSocketAddress(80));
            System.out.println("Server listening on port 80...");

            while (true) {
                // MEMO: CHAT-GPTより
                // accept()メソッドは、新しいクライアントの接続があるまでプログラムをブロックするため、通常は別のスレッドで実行する必要がある。
                // また、複数のクライアントと接続する場合は、accept()メソッドをループさせる必要がある。
                try (Socket socket = serverSocket.accept()) {
                    System.out.println(">>");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    HttpRequest httpRequest;
                    try {
                        httpRequest = readRequest(reader);
                    } catch (IllegalRequestLineException e) {
                        writer.write("HTTP/1.1 500 Internal Server Error" + CRLF);
                        writer.write("Content-Length: 0" + CRLF);
                        writer.write("Content-Type: text/html" + CRLF);
                        writer.write(CRLF);
                        writer.flush();
                        System.out.println("<<");
                        continue;
                    }

                    System.out.println("method: " + httpRequest.getMethod());
                    System.out.println("path: " + httpRequest.getPath());
                    System.out.println("HTTP/1." + httpRequest.getProtocolMinorVersion());
                    System.out.println("body: " + httpRequest.getBody());

                    // HTTP レスポンス
                    writer.write("HTTP/1.1 200 OK" + CRLF);
                    writer.write("Content-Length: 0" + CRLF); //TODO: Content-Lengthを設定する
                    writer.write("Content-Type: text/html" + CRLF);
                    writer.write(CRLF);
                    writer.flush();
                    System.out.println("<<");
                }
            }
        } catch (IOException | ClientDisconnectedException e) {
            e.printStackTrace();
        }
    }

    /**
     * HTTPリクエストを読み込んでhttpRequestのインスタンスを生成する
     *
     * @param reader reader
     * @return httpRequest
     * @throws IllegalRequestLineException
     * @throws IOException
     * @throws ClientDisconnectedException
     */
    private HttpRequest readRequest(BufferedReader reader) throws IllegalRequestLineException, IOException, ClientDisconnectedException {
        HttpRequest httpRequest = new HttpRequest();

        readRequestLine(reader, httpRequest);
        readHeaderField(reader, httpRequest);
        readRequestBody(reader, httpRequest);

        return httpRequest;
    }

    /**
     * リクエストラインの内容を解析して、httpRequestのフィールドにセットする
     * このような文字列が入る想定 GET /index.html HTTP/1.1
     *
     * @param reader      reader
     * @param httpRequest httpRequest
     * @throws IOException
     * @throws ClientDisconnectedException
     * @throws IllegalRequestLineException
     */
    private void readRequestLine(BufferedReader reader, HttpRequest httpRequest) throws IOException, ClientDisconnectedException, IllegalRequestLineException {
        String line = reader.readLine();
        if (line == null) {
            throw new ClientDisconnectedException();
        }
        httpRequest.setRequestLine(line); // NOTE: 引数にはGET /index.html HTTP/1.1のような文字列が入る想定
    }

    /**
     * HTTPヘッダを読み込んでhttpRequestにセットする
     *
     * @param reader      reader
     * @param httpRequest httpRequest
     * @throws IOException
     */
    private void readHeaderField(BufferedReader reader, HttpRequest httpRequest) throws IOException {
        String line = reader.readLine();
        while (line != null && !line.isEmpty()) {
            if (line.startsWith("Content-Length: ")) {
                int contentLength = Integer.valueOf(line.replace("Content-Length: ", ""));
                httpRequest.setLength(contentLength);
            }
            line = reader.readLine();
        }
    }

    /**
     * エンティティボディをhttpRequestにセットする
     *
     * @param reader      reader
     * @param httpRequest httpRequest
     * @throws IOException
     */
    private void readRequestBody(BufferedReader reader, HttpRequest httpRequest) throws IOException {
        if (httpRequest.getLength() > 0) {
            char[] c = new char[(int) httpRequest.getLength()];
            reader.read(c);
            httpRequest.setBody(new String(c));
        }
    }
}
