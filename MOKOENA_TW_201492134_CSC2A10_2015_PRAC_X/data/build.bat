set path=%path%;C:\Program Files (x86)\Java\jdk1.7.0_75\bin
cd..
set PRAC_BIN= ./bin
set PRAC_DOCS= ./docs
set PRAC_SRC= ./src

javac -sourcepath %PRAC_SRC% -cp %PRAC_BIN% -d %PRAC_BIN% %PRAC_SRC%/Main.java
javadoc -d %PRAC_DOCS%/JavaDocs -subpackages . -sourcepath %PRAC_SRC% -classpath %PRAC_BIN%
java -cp %PRAC_BIN% Main

PAUSE