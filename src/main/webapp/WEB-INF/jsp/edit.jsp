<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.ldwx.model.SectionType" %>
<%@ page import="ru.ldwx.model.ContactType" %>
<%@ page import="ru.ldwx.model.ListSection" %>
<%@ page import="ru.ldwx.model.OrganizationSection" %>
<%@ page import="ru.ldwx.util.DateUtil" %><%--
  Created by IntelliJ IDEA.
  User: Loky
  Date: 08.11.2018
  Time: 18:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:useBean id="employee" type="ru.ldwx.model.Employee" scope="request"/>
    <title>${employee.fullName}</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div class="wrapper">
    <div class="header">
        <div class="logo"><a href="">Our<span class="black">Company</span><span class="gray">.ru</span></a>
            <p>ГК Компания</p></div>
        <ul class="nav">
            <li><a href="/employees" class="active">Главная</a></li>
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
        <section>
            <form method="post" action="employees" enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="uuid" value="${employee.uuid}">
                <h1>Имя:</h1>
                <dl>
                    <input type="text" name="fullName" size=55 value="${employee.fullName}">
                </dl>
                <h2>Контакты:</h2>
                <c:forEach var="type" items="<%=ContactType.values()%>">
                    <dl>
                        <dt>${type.title}</dt>
                        <dd><input type="text" name="${type.name()}" size=30 value="${employee.getContact(type)}"></dd>
                    </dl>
                </c:forEach>
                <hr>
                <c:forEach var="type" items="<%=SectionType.values()%>">
                    <c:set var="section" value="${employee.getSection(type)}"/>
                    <jsp:useBean id="section" type="ru.ldwx.model.Section"/>
                    <h2><a>${type.title}</a></h2>
                    <c:choose>
                        <c:when test="${type=='OBJECTIVE'}">
                            <input type='text' name='${type}' size=75 value='<%=section%>'>
                        </c:when>
                        <c:when test="${type=='PERSONAL'}">
                            <textarea name='${type}' cols=75 rows=5><%=section%></textarea>
                        </c:when>
                        <c:when test="${type=='QUALIFICATIONS'}">
                    <textarea name='${type}' cols=75
                              rows=5><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
                        </c:when>
                        <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                            <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganizations()%>"
                                       varStatus="counter">
                                <dl>
                                    <dt>Название учереждения:</dt>
                                    <dd><input type="text" name='${type}' size=100 value="${org.homePage.name}"></dd>
                                </dl>
                                <dl>
                                    <dt>Сайт учереждения:</dt>
                                    <dd><input type="text" name='${type}url' size=100 value="${org.homePage.url}"></dd>
                                    </dd>
                                </dl>
                                <br>
                                <div style="margin-left: 30px">
                                    <c:forEach var="pos" items="${org.positions}">
                                        <jsp:useBean id="pos" type="ru.ldwx.model.Organization.Position"/>
                                        <dl>
                                            <dt>Начальная дата:</dt>
                                            <dd>
                                                <input type="text" name="${type}${counter.index}startDate" size=10
                                                       value="<%=DateUtil.format(pos.getStartDate())%>"
                                                       placeholder="MM/yyyy">
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt>Конечная дата:</dt>
                                            <dd>
                                                <input type="text" name="${type}${counter.index}endDate" size=10
                                                       value="<%=DateUtil.format(pos.getEndDate())%>"
                                                       placeholder="MM/yyyy">
                                        </dl>
                                        <dl>
                                            <dt>Должность:</dt>
                                            <dd><input type="text" name='${type}${counter.index}title' size=75
                                                       value="${pos.title}">
                                        </dl>
                                        <dl>
                                            <dt>Описание:</dt>
                                            <dd><textarea name="${type}${counter.index}description" rows=5
                                                          cols=75>${pos.description}</textarea></dd>
                                        </dl>
                                    </c:forEach>
                                </div>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                </c:forEach>
                <button type="submit">Сохранить</button>
                <button onclick="window.history.back()">Отменить</button>
            </form>
        </section>
    </div>
    <div class="footer">
        <p>&copy; Создано для внутреннего использования <a href="/employees">Главная</a></p>
    </div>
</div>
</body>
</html>
