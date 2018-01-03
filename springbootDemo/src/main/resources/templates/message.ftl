<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Spring Boot 集成Mybatis 测试例子</title>
</head>
<body>
<h1>Spring Boot 集成Mybatis 测试例子</h1>
<em>${.now}</em>

<#if messages?exists>
<table style="border: 1px solid red;">
    <#assign index = 1>
    <#list messages as message>
        <tr <#if index%2 == 0>style="color: red;"</#if>>
            <td>${message.id}</td>
            <td>${message.ip}</td>
            <td>${message.nickName}</td>
            <td>${message.insertDate}</td>
        </tr>
        <#assign index = index + 1>
    </#list>
</table>
</#if>
</body>
</html>