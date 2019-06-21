<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <table border="2px">
        <tr>
            <td>name</td>
            <td>price</td>
        </tr>
        <tr>

            <td>${man.name}</td>
            <td>${man.name}</td>
            <#--Map数据：两种方式都行-->
            <td>${man["name"]}</td>
            <td>${man["price"]}</td>
        </tr>
    </table>
</body>
</html>