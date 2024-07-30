set JAVA_HOME=D:\Atelier\Java\openjdk-17.0.2
set JAVA_FX_HOME=D:\Atelier\JavaFX\javafx-sdk-22.0.2
set JAVA_FX_MODE=D:\Atelier\JavaFX\javafx-jmods-22.0.2

set Path=%Path%;%WIX_HOME%;%JAVA_HOME%\bin;%JAVA_FX_HOME%\lib


rem java --module-path %JAVA_FX_HOME%\lib --add-modules=javafx.controls,javafx.fxml,javafx.media,javafx.swing -jar lessonstudy.jar

rem jdeps --module-path %JAVA_FX_HOME%\lib -s lessonstudy.jar

rem jlink --module-path %JAVA_FX_MOD% --add-modules java.base,javafx.base,javafx.controls,javafx.fxml,javafx.graphics --output ..\jre-min

rem ..\jre-min\bin\java -jar lessonstudy.jar


jpackage --type msi --win-menu --win-dir-chooser --win-shortcut --win-shortcut-prompt --module-path %JAVA_FX_MOD% --add-modules java.base,javafx.base,javafx.controls,javafx.fxml,javafx.graphics --input dest --name LessonStudy --main-class Main --main-jar lessonstudy.jar
