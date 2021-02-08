<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://ru.topjava" %>

<html lang="ru">
<head>
    <title>Meals</title>
    <style>
        .green {
            color: green;
        }
        .red {
            color: red;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a> </h3>
<h3><a href="users.jsp">Users</a> </h3>
<hr>
<h2>Meals</h2>
<br>
<button><a href="addEditMeal.jsp?action=add">Add</a></button>
<table border="1">
    <caption>List of meals</caption>
    <tr>
        <th>Date and Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <tr class="${meal.excess ? 'red' : 'green'}">
            <td>${f:formatLocalDateTime(meal.dateTime, "dd.MM.yyyy HH:mm")}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>
                <button>
                    <a href="addEditMeal.jsp?action=edit&id=${meal.id}&dt=${meal.dateTime}&desc=${meal.description}&calories=${meal.calories}">Update</a>
                </button>
                <button><a href="meals?action=delete&id=${meal.id}" me>Delete</a></button>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
