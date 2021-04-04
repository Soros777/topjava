<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/topjava.common.js" defer></script>
<script type="text/javascript" src="resources/js/topjava.meals.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3><spring:message code="meal.title"/></h3>

        <form method="get" id="mealsFilter">
            <div class="form-group">
                <label for="startDate" class="col-form-label"><spring:message code="meal.startDate"/>:</label>
                <input type="date" name="startDate" id="startDate" value="${param.startDate}">
            </div>
            <div class="form-group">
                <label for="endDate" class="col-form-label"><spring:message code="meal.endDate"/>:</label>
                <input type="date" name="endDate" id="endDate" value="${param.endDate}">
            </div>
            <div>
                <label for="startTime" class="col-form-label"><spring:message code="meal.startTime"/>:</label>
                <input type="time" name="startTime" id="startTime" value="${param.startTime}">
            </div>
            <div class="form-group">
                <label for="endTime" class="col-form-label"><spring:message code="meal.endTime"/>:</label>
                <input type="time" name="endTime" id="endTime" value="${param.endTime}">
            </div>
            <button type="button" class="btn btn-primary" onclick="filter()">
                <span class="fa fa-filter"></span>
                <spring:message code="meal.filter"/>
            </button>
        </form>
        <button class="btn btn-primary btn-lg" onclick="create()">
            <span class="fa fa-plus"><spring:message code="meal.add"/></span>
        </button>
        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="meal.dateTime"/></th>
                <th><spring:message code="meal.description"/></th>
                <th><spring:message code="meal.calories"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealTo"/>
                <tr data-mealExcess="${meal.excess}" id="${meal.id}">
                    <td>
                            <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                            <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                            <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                            ${fn:formatDateTime(meal.dateTime)}
                    </td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a><span class="fa fa-pencil" onclick="update('${meal.id}', '${meal.dateTime}',
                            '${meal.description}', '${meal.calories}', '<spring:message code="meal.edit"/>')">
                            </span>
                        </a>
                    </td>
                    <td><a class="delete"><span class="fa fa-remove"></span></a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="title"><spring:message code="meal.add"/></h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form method="post" action="meals" id="detailsForm">
                    <input type="hidden" name="id" id="id">

                    <div class="form-group">
                        <label for="dateTime" class="col-form-label"><spring:message code="meal.dateTime"/>:</label>
                        <input type="datetime-local" name="dateTime" id="dateTime" required>
                    </div>
                    <div class="form-group">
                        <label for="description" class="col-form-label"><spring:message code="meal.description"/>:</label>
                        <input type="text" size=40 name="description" id="description" required>
                    </div>
                    <div class="form-group">
                        <label for="calories" class="col-form-label"><spring:message code="meal.calories"/>:</label>
                        <input type="number" name="calories" id="calories" required>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="saveMeal()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>