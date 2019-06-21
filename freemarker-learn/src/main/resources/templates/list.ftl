<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>list测试</title>
</head>
<body>
<table border = "1">
    <tr>
        <td>name</td>
        <td>price</td>
    </tr>
    <#list myList as item>
        <tr>
            <td>${item.name}</td>
            <td>${item.price}</td>
            <td>
                <a href = "">修改</a>
                <a href = "">删除</a>
            </td>
        </tr>
    </#list>
</table>

<a href = "#">新增</a>

</body>
</html>