<%@ page import="ru.ldwx.model.ContactType" %><%--
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
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>Список всех работников</title>
</head>
<body>
<div class="wrapper">
    <div class="header">
        <div class="logo"><a href="">Our<span class="black">Company</span><span class="gray">.ru</span></a><p>ГК Компания</p></div>
        <ul class="nav">
            <li><a href="#" class="active">Главная</a></li>
            <li><a href="#">О нас</a></li>
            <li><a href="#">Новости</a></li>
            <li><a href="#">Контакты</a></li>
        </ul>
    </div>
    <div class="content">
        <div class="rightCol">
            <ul class="rightNav">
                <li><a href="#">Департамент продаж</a></li>
                <li><a href="#">Депортамент системной интеграции</a></li>
                <li><a href="#">Департамент юридической деятельности</a></li>
                <li><a href="#">Департамент сервиса</a></li>
                <li><a href="#">Департамент логистики и склада</a></li>
            </ul>
            <div class="block">
                <h3>Корпоративное кафе</h3>
                <p><i>Уважаемые коллеги! С 11 ноября корпоративное кафе будет закрыто на реконструкцию.</i></p>
                <p><a href="#" class="more">Читать далее »</a></p>
            </div>
        </div>
        <div class="main">
            <h1>Описание</h1>
            <p>В данном списке сотрудников можно посмотреть всех сотрудников нашей компании. При нажатии на имя сотрудника можно увидеть делатльную информацию с контактами и квалификацией.</p>
            <!--Таблица-->
            <h2>Сотрудники</h2>
            <br>
            <a href="employees?action=add">Добавить сотрудника</a>
            <table class="bordered">
                <thead>
                <tr>
                    <th>Имя</th>
                    <th>Должность</th>
                    <th>Email</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${employees}" var="employee">
                    <jsp:useBean id="employee" type="ru.ldwx.model.Employee"/>
                    <tr>
                        <td><a href="employees?uuid=${employee.uuid}&action=view">${employee.fullName}</a></td>
                        <td></td>
                        <td><%=ContactType.MAIL.toHtml(employee.getContact(ContactType.MAIL))%></td>
                        <td><a href="employees?uuid=${employee.uuid}&action=delete">Удалить</a></td>
                        <td><a href="employees?uuid=${employee.uuid}&action=edit">Редактировать</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="footer">
        <p>&copy; Создано для внутреннего использования <a href="#">Главная</a></p>
    </div>
</div>
</body>
</html>
