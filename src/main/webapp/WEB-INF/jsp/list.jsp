<%@ page import="local.ldwx.model.ContactType" %><%--
  Created by IntelliJ IDEA.
  User: Loky
  Date: 01.11.2018
  Time: 21:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Список всех работников</title>
</head>
<body>
<section>
    <a href="employees?action=add">Добавить сотрудника</a>
    <br>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Имя</th>
            <th>Должность</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${employees}" var="employee">
            <jsp:useBean id="employee" type="local.ldwx.model.Employee"/>
            <tr>
                <td><a href="employees?uuid=${employee.uuid}&action=view">${employee.fullName}</a></td>
                <td></td>
                <td><%=ContactType.MAIL.toHtml(employee.getContact(ContactType.MAIL))%></td>
                <td><a href="employees?uuid=${employee.uuid}&action=delete">Удалить</a></td>
                <td><a href="employees?uuid=${employee.uuid}&action=edit">Редактировать</a></td>
            </tr>
        </c:forEach>
    </table>
</section>

</body>
</html>
