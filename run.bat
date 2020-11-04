@ECHO OFF

CALL compile.bat
@IF %ERRORLEVEL% NEQ 0 (
    EXIT /B 1
)

SET "JAVAEXE=C:\java-11-openjdk-11.0.8.10-1.windows.ojdkbuild.x86_64\bin\java.exe"

"%JAVAEXE%" -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5757 -jar target\pior-filme-1.0-SNAPSHOT.jar