for /f "delims=[] tokens=2" %%a in ('ping -4 -n 1 %ComputerName% ^| findstr [') do set NetworkIP=%%a
echo start "" http://%NetworkIP%:8080/ > program.bat

java -jar mentenance.jar --server.address=%NetworkIP%

