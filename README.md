# LessonStudy

## はじめに

はじめはJava17でやろうとがんばっていましたが、[これ](https://github.com/oracle/graal/issues/4790)を見て諦めてJava22に乗り換えました。
私同様、はじめてJavaFX+jpackageでアプリを作ろうとするのであれば、Java17はやめておいた方がいいんじゃないかなあ…。
あと、All-in-One の Eclipse は、JavaFXのプラグインが動かなかったので、ピュアEclipse使うのがいいと思います。JavaFXが使えるのを確認してから、他のプラグインを考えるのがよいかと思います。


## 開発環境を構築する。

1. 必要なものをダウンロードする。
	- [OpenJDK](https://openjdk.org)
		- JDK 22.0.2 の Win 64 zipをダウンロードする。
	- [OpenJFX](https://openjfx.io)
		- JavaFX 22.0.2 のSDK、jmods をダウンロードする。まじめにやるなら javadocもダウンロードすべき。
		- Scene Builder をダウンロードする。
			- DonloadページからトップメニューのProductsで選べる。バージョンが選べないので、ここで選んだバージョンにあわせてJavaFXの方もダウンロードするのがよいと思います。
	- [ECLIPSE](https://www.eclipse.org/downloads/)
		- Download Packages から、`Eclipse IDE for Java Developers`をダウンロードする。バージョンは`2024‑06 R`。
	- [WiX Toolset](https://github.com/wixtoolset/wix3/releases)
		- `wix314-binaries.zip`をダウンロードする。
1. 作業フォルダをセットアップする。
	適当な作業フォルダを作って、そこにそれぞれを解凍します。今回は`Scene Builder`以外は全部zipでダウンロードしています。
	- 作業フォルダ
		- eclipse
			- eclipse-java-2024-06-R-win32-x86_64
			- eclipse-workspace  ※Ecplpseの最初の起動時に設定する。
		- Java
			- openjdk-22.0.2
		- JavaFX
			- javafx-jmods-22.0.2
			- javafx-sdk-22.0.2
		- WixToolSet
			- wix314-binaries
1. 作業中

## ビルド手順

なんやかんやで実行可能jarを作成する。

### インストーラを作成する。
コマンドプロンプトでの作業
1. 環境変数を設定する。
	```console
	~LessonStudy> set JAVA_HOME=..\..\..\Java\openjdk-22.0.2
	~LessonStudy> set JAVA_FX_HOME=..\..\..\JavaFX\javafx-sdk-22.0.2
	~LessonStudy> set JAVA_FX_MODS=..\..\..\JavaFX\javafx-jmods-22.0.2
	~LessonStudy> set WIX_HOME=..\..\..\WixToolSet\wix314-binaries
	~LessonStudy> set Path=%Path%;%WIX_HOME%;%JAVA_HOME%\bin;%JAVA_FX_HOME%\lib
	```
1. 作成したjarファイルが動くか確認する。
	```console
	~LessonStudy> java --module-path %JAVA_FX_HOME%\lib --add-modules=javafx.controls,javafx.fxml,javafx.media,javafx.swing -jar .\dest\lessonstudy.jar
	```
1. jarファイルが使用するモジュールを確認する。
	<br/>最後zipでまとめるにしろインストーラにするにしろ、ここが一番大事。
	```console
	~LessonStudy> jdeps --module-path %JAVA_FX_HOME%\lib -s .\dest\lessonstudy.jar
	LessonStudy -> java.base
	LessonStudy -> javafx.base
	LessonStudy -> javafx.controls
	LessonStudy -> javafx.fxml
	LessonStudy -> javafx.graphics
	```
1. 最小単位のjreを作成する。
	<br/>最小単位のjreとはいうが、JavaFXのライブラリも内包してくれるので非常に重要。
	```console
	~LessonStudy> jlink --module-path %JAVA_FX_MODS% --add-modules java.base,javafx.base,javafx.controls,javafx.fxml,javafx.graphics --output .\jre-min
	```
1. 最小単位のjreで動かせるか確認する。
	```console
	.\jre-min\bin\java -jar .\dest\lessonstudy.jar
	```
1. インストーラを作成する。
	<br/>モジュールまわりは最小単位のjreを作る時と同じオプションを使用するのが大事。
	<br/>Program Filesにインストールすると、アプリケーションフォルダ内のプロパティファイルが更新できないので、ユーザー単位のインストールを設定しています。
	```console
	jpackage --win-per-user-install --type msi --win-menu --win-menu-group LessonStudy --win-dir-chooser --win-shortcut --win-shortcut-prompt --module-path %JAVA_FX_MODS% --add-modules java.base,javafx.base,javafx.controls,javafx.fxml,javafx.graphics --input dest --name LessonStudy --main-class application.Main --main-jar lessonstudy.jar
	```


