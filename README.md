# my-http-server
## 概要
「ふつうの Linux プログラミング 第 2 版」（ISBN: 978-4-7973-8647-9）の「第16章 HTTPサーバを作る」と「第17章 HTTPサーバを本格化する」をJavaに移植して、理解を深める。

## 実行方法
- サーバー側は以下の方法で起動する（または、InteliJ IDEAで run ジョブを実行する）。

```sh
$ ./gradlew run
```

## 実行結果
`curl http://127.0.0.1 -i`を実行すると、サーバー側で下記のように出力される。

```sh
>>
method: GET
path: /
HTTP/1.1
body: null
<<
```

`para=30; seq $para | xargs -I{} -P$para curl http://127.0.0.1 -i`を実行すると、並列でHTTPリクエストを同時に実行できる。

## Javaで書いた時のメモ
### メリット
- メモリの解放などを気にしなくて良い

### デメリット
- try-catch文が多くて複雑
- ファイル、プロセス、ストリームの3つの概念で構成されていることが分かりにくい
  - C言語だとプロセスがファイルディスクリプタを指定して入力のストリームと出力のストリームの読み書きを行っていることが分かりやすい + メソッドの抽象度が高いことが理解しやすい。