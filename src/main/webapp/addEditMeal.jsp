<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <c:set var="action" scope="page" value="${param.action eq 'add' ? 'Add new meal' : 'Edit meal'}"/>
    <title>${action}</title>
</head>
<body>
<form action="meals" method="post">
    <table border="1">
        <caption>${action}</caption>
        <tr>
            <td>Date and time of meal:</td>
            <td><input type="datetime-local" name="dateTime" value="${param.dt}"></td>
        </tr>
        <tr>
            <td>Meal description:</td>
            <td><input type="text" name="description" value="${param.desc}"></td>
        </tr>
        <tr>
            <td>Calories:</td>
            <td><input type="number" name="calories" value="${param.calories}"></td>
        </tr>
    </table>
    <input type="hidden" name="action" value="${param.action}">
    <input type="hidden" name="id" value="${param.id}">
    <p>
        <input type="submit" value="${param.action} meal">
        <button><a href="meals">cancel</a> </button>
    </p>
</form>
</body>
</html>
