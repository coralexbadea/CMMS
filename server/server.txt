echo Y | rmdir Data /s
mkdir Data
Xcopy /E "C:\ProgramData\MySQL\MySQL Server 5.7\Data" .\Data 

for /f "delims=[] tokens=2" %%a in ('ping -4 -n 1 %ComputerName% ^| findstr [') do set NetworkIP=%%a
echo start chrome http://%NetworkIP%:8080/ > program.bat

java -jar mentenance.jar --server.address=%NetworkIP%

