# JavaFxStudy

## はじめに

JavaFXとjpackageでWindowsアプリケーションを作ってみました。

はじめはJava17でやろうとがんばっていましたが、[これ](https://github.com/oracle/graal/issues/4790)を見て諦めてJava22に乗り換えました。
私同様、はじめてJavaFX+jpackageでアプリを作ろうとするのであれば、Java17はやめておいた方がいいんじゃないかなあ…。
あと、All-in-One の Eclipse は、JavaFXのプラグインが動かなかったので、ピュアEclipse使うのがいいと思います。JavaFXが使えるのを確認してから、他のプラグインを考えるのがよいかと思います。


## 開発環境を構築する。

1. 必要なものをダウンロードする。
	- [OpenJDK](https://openjdk.org)
		- JDK 22.0.2 の Win 64 zipをダウンロードする。
	- [OpenJFX](https://openjfx.io)
		- JavaFX
			22.0.2、Windows、x64の、SDK、jmodsをダウンロードする。javadocもダウンロードしておく。
			- openjfx-22.0.2_windows-x64_bin-sdk.zip
			- openjfx-22.0.2_windows-x64_bin-jmods.zip
			- openjfx-22.0.2-javadoc.zip
		- Scene Builder をダウンロードする。
			- DonloadページからトップメニューのProductsで選べる。バージョンが選べないので、ここで選んだバージョンにあわせてJavaFXの方もダウンロードするのがよいと思います。
			- SceneBuilder-22.0.0.msi
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
1. SceneBuilderをインストールしておく。
	- `SceneBuilder-22.0.0.msi` をダブルクリックするだけ。
1. Eclipseセッティング
	1. 起動する。初回起動時に、上記のワークスペースを作成する。
	1. Javaの設定
		1. Window > Preferences を選択する。
		1. Java > Installed JREs から、openjdk-22.0.2を登録し、default に設定する。
	1. JavaFXの準備
		1. help > Eclipse Marketplace... を選択する。
		1. `Eclipse Market`ダイアログから Find に `fx` と入力して `Go` ボタンを押下する。
		1. `e(fx)clipse 3.8.0` が表示される(はず)なので `installeo` ボタンを押下する。
		1. インストールには結構時間がかかる。終わった様に見えても下の方にインストール中の表示がある。終わると再起動をうながされるので再起動する。
		1. Window > Preferences を選択する。
		1. JavaFX を選択する。
		1. `SceneBuilder executable` にインストールしたexeを設定する。
		1. `JavaFX 11+ SDK` に上に解凍した `javafx-sdk-22.0.2\lib` を設定する。
	1. JavaFXライブラリをユーザライブラリに登録する。
		1. Window > Preferences を選択する。
		1. Java > Build Path > User Libraries を選択する。
		1. `New`ボタンから `javafx-sdk-22.0.2`を作成する。
		1. `javafx-sdk-22.0.2`を選択した状態で`Add External JARs...`ボタンを押下し、上に解凍した `javafx-sdk-22.0.2\lib`のjarファイルをすべて登録する。
	1. JavaFXプロジェクトを作成する。
		1. File > New > Other... から、JavaFX > JavaFX Project を選べるようになっているので、ここから新規JavaFXプロジェクトを作成する。
			- JRE
				- Use a project specific JRE:openjdk-22.0.2
			- Project layout
				- Create separate folders for source and class files
			- Working sets
				- なし
			- Module
				- Create module-info.java file
				- Generate comments
			- Application type : Desktop
			- Declarative UI
				- Langage : FXML
			- その他はアプリケーションにあわせて。
		1. ビルドパスにJavaFXライブラリを追加する。
			1. 作成したプロジェクトを選択して右クリックし、`Properties`を選択する。
			1. `Java Build Path`を選択し、`Libraries`タブを選択する。
			1. `Modulepath`を選択し、`Add Library...`ボタンを押下する。
			1. `User Library`を選択し、`javafx-sdk-22.0.2`にチェックを入れて `Finish`を押下する。
	1. `module-info.java`
		```java
		module JavaFxStudy {
			requires javafx.controls;
			requires javafx.fxml;
			requires javafx.base;
			requires javafx.graphics;
		
			opens application to javafx.graphics, javafx.fxml;
		}
		```
## ビルド手順

### jarを作成する。

今回は、`Runnable JAR file` で、 `Copy required libraries into sub-folder next to generated JAR` を選択する。
出力場所は、`JavaFxStudy\dest\javafxstudy.jar`

### インストーラを作成する。
コマンドプロンプトでの作業
1. 環境変数を設定する。
	```console
	~JavaFxStudy> set JAVA_HOME=..\..\..\Java\openjdk-22.0.2
	~JavaFxStudy> set JAVA_FX_HOME=..\..\..\JavaFX\javafx-sdk-22.0.2
	~JavaFxStudy> set JAVA_FX_MODS=..\..\..\JavaFX\javafx-jmods-22.0.2
	~JavaFxStudy> set WIX_HOME=..\..\..\WixToolSet\wix314-binaries
	~JavaFxStudy> set Path=%Path%;%WIX_HOME%;%JAVA_HOME%\bin;%JAVA_FX_HOME%\lib
	```
1. 作成したjarファイルが動くか確認する。
	```console
	~JavaFxStudy> java --module-path %JAVA_FX_HOME%\lib --add-modules=javafx.controls,javafx.fxml,javafx.media,javafx.swing -jar .\dest\javafxstudy.jar
	```
1. jarファイルが使用するモジュールを確認する。
	<br/>最後zipでまとめるにしろインストーラにするにしろ、ここが一番大事。
	```console
	~JavaFxStudy> jdeps --module-path %JAVA_FX_HOME%\lib -s .\dest\javafxstudy.jar
	~JavaFxStudy -> java.base
	~JavaFxStudy -> javafx.base
	~JavaFxStudy -> javafx.controls
	~JavaFxStudy -> javafx.fxml
	~JavaFxStudy -> javafx.graphics
	```
1. 最小単位のjreを作成する。
	<br/>最小単位のjreとはいうが、JavaFXのライブラリも内包してくれるので非常に重要。
	```console
	~JavaFxStudy> jlink --module-path %JAVA_FX_MODS% --add-modules java.base,javafx.base,javafx.controls,javafx.fxml,javafx.graphics --output .\jre-min
	```
1. 最小単位のjreで動かせるか確認する。
	```console
	.\jre-min\bin\java -jar .\dest\javafxstudy.jar
	```
1. インストーラを作成する。
	<br/>モジュールまわりは最小単位のjreを作る時と同じオプションを使用するのが大事。
	<br/>Program Filesにインストールすると、アプリケーションフォルダ内のプロパティファイルが更新できないので、ユーザー単位のインストールを設定しています。
	```console
	jpackage --win-per-user-install --type msi --win-menu --win-menu-group JavaFxStudy --win-dir-chooser --win-shortcut --win-shortcut-prompt --module-path %JAVA_FX_MODS% --add-modules java.base,javafx.base,javafx.controls,javafx.fxml,javafx.graphics --input dest --name JavaFxStudy --main-class application.Main --main-jar javafxstudy.jar
	```

# 参考文献

- [jpackageを使ってJavaアプリケーションの配布用パッケージを作成する](https://rheb.hatenablog.com/entry/2023/10/25/110538)
- [jpackageの踏み入った使い方](https://rheb.hatenablog.com/entry/2023/11/01/155002)
