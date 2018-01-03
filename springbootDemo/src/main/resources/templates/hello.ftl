<!DOCTYPE html>
<html lang="en">
<head>
    <title>SpringBoot + Freemarker</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
Hello ${name}${age}${sex}
<p>${birth?string("yyyy-MM-dd HH:mm:ss.sss")}</p>
</body>
</html>