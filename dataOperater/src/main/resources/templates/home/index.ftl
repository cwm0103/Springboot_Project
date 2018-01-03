<html>
<head>
    <title>测试页面</title>
    <meta charset="utf-8"/>

</head>
<body>
<div style="width:100%;height: 200px; background-color: chartreuse">

    <form id="form1" name="form1" action="/home/export" method="post" enctype="multipart/form-data">
        <input type="file" id="fileload" name="fileload" >
        <input type="submit" value="提交">
    </form>
</div>

<div style="width: 100%;margin-top: 10px">
<table style="width: 200px;height: 200px;background-color: aliceblue">
    <tr><th>id</th><th>name</th></tr>
     <#--<tr><td>${list[0].user_id}</td><td>${list[0].user_name} </td></tr>-->
    <#--<tr><td>${list}</td></tr>-->
</table>
</div>

</body>
</html>