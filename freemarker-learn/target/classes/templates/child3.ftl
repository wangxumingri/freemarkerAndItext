<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Title</title>
    <style type="text/css">
        div {
            margin: 0 auto;
            width: 80%;
            height: 80%;
        }
        td {
            vertical-align: middle;
            text-align: center;
            font-size: 12px;
            border: 1px solid black;
        }
    </style>
</head>
<body>
    <div>
        <div style="vertical-align: middle;text-align:center;font-size:22px ">
            国家免疫规划疫苗儿童免疫程序表
        </div>
        <br/>
        <table width="70%" cellpadding="0"  cellspacing="0"  align="center" style="border: 1px solid black">
            <tr>
                <td colspan="2">
                    疫苗种类
                </td>
                <td colspan="15">
                    接种年(月)龄
                </td>
            </tr>
            <tr>
                <td>名称</td>
                <td>缩写</td>
                <td>出生时</td>
                <td>1月</td>
                <td>2月</td>
                <td>3月</td>
                <td>4月</td>
                <td>5月</td>
                <td>6月</td>
                <td>8月</td>
                <td>9月</td>
                <td>18月</td>
                <td>2岁</td>
                <td>3岁</td>
                <td>4岁</td>
                <td>5岁</td>
                <td>6岁</td>
            </tr>
            <#list myList as item>
                <tr>
                    <td>${item.vaccineName}</td>
                    <td>${item.abbreviation}</td>

                    <#list item.vaccinationTime as subItem>
                            <td>
                               ${subItem ! ""}
                            </td>
                    </#list>
                </tr>
            </#list>
        </table>
    </div>
    <div style="vertical-align: middle;text-align:center;font-size:22px ">
        hello □ □ world
        <label>
            <input type="checkbox" checked> 1
        </label>
        <label>
            <input type="checkbox">2
        </label>
        <label>
            <input type="checkbox">3
        </label>
    </div>
</body>
</html>