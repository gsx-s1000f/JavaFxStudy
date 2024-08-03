set JAVA_HOME=..\..\..\Java\openjdk-22.0.2
set JAVA_FX_HOME=..\..\..\JavaFX\javafx-sdk-22.0.2
set JAVA_FX_MODS=..\..\..\JavaFX\javafx-jmods-22.0.2
set WIX_HOME=..\..\..\WixToolSet\wix314-binaries

set Path=%Path%;%WIX_HOME%;%JAVA_HOME%\bin;%JAVA_FX_HOME%\lib


rem java --module-path %JAVA_FX_HOME%\lib --add-modules=javafx.controls,javafx.fxml,javafx.media,javafx.swing -jar .\dest\lessonstudy.jar

rem jdeps --module-path %JAVA_FX_HOME%\lib -s .\dest\lessonstudy.jar

rem jlink --module-path %JAVA_FX_MODS% --add-modules java.base,javafx.base,javafx.controls,javafx.fxml,javafx.graphics --output .\jre-min

rem .\jre-min\bin\java -jar .\dest\lessonstudy.jar





rem jpackage --win-console --type msi --win-menu --win-dir-chooser --win-shortcut --win-shortcut-prompt --module-path %JAVA_FX_MODS% --add-modules java.base,javafx.base,javafx.controls,javafx.fxml,javafx.graphics --input dest --name LessonStudy --main-class application.Main --main-jar lessonstudy.jar
jpackage --win-per-user-install --type msi --win-menu --win-menu-group LessonStudy --win-dir-chooser --win-shortcut --win-shortcut-prompt --module-path %JAVA_FX_MODS% --add-modules java.base,javafx.base,javafx.controls,javafx.fxml,javafx.graphics --input dest --name LessonStudy --main-class application.Main --main-jar lessonstudy.jar
