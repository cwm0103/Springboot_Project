@echo off


start javaw -jar web-api-0.0.1-SNAPSHOT.jar > logs\batchLog%date:~0,4%%date:~5,2%%date:~8,2%.log  


exit