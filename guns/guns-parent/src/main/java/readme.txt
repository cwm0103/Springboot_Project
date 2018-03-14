说明: 如果不创建这个文件夹,开启devtools启动会报错

此项目的运行方式有2种：

1.启动guns：
    1.1 运行Java文件【springboot】
        运行 guns\guns-admin\src\main\java\com\stylefeng\guns\GunsApplication.java

    1.2 直接运行jar文件
        1.2.1 对guns进行打包部署
          1.2.1.1 找到guns-parent
          1.2.1.2 运行命令
                mvn clean package  -Dmaven.test.skip=true
          1.2.1.3 拷贝guns-admin-1.0.0-SNAPSHOT.jar到运行目录
             运行命令  java -jar guns-admin-1.0.0-SNAPSHOT.jar
    1.3 tomcat 运行
           打包过程是一样的



