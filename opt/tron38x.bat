@echo off
@java -classpath

start .\java\jre1.8.0\bin\java -jar -Xms1024m -Xmx1024m .\fsproject.jar

cd .\hsqldb\bin
start  runServer.bat

exit