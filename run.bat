@echo off
setlocal
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot"
set "PATH=%JAVA_HOME%\bin;%PATH%"

javac -d bin src/main/java/com/example/snake/*.java
java -cp bin com.example.snake.App
