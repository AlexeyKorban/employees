<%@ page import="ru.ldwx.model.ListSection" %>
<%@ page import="ru.ldwx.model.OrganizationSection" %>
<%@ page import="ru.ldwx.model.TextSection" %>
<%@ page import="ru.ldwx.util.HtmlUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Alexey.Korban
  Date: 07.11.2018
  Time: 13:53
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:useBean id="employee" type="ru.ldwx.model.Employee" scope="request"/>
    <title><c:out value="${employee.fullName}"/></title>
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
        <h1><c:out value="${employee.fullName}"/><a href="employees?uuid=${employee.uuid}&action=edit"><img src="../../img/pencil.png" alt="Редактировать" title="Редактировать данные сотрудника"></a></h1>
        <p>
            <c:forEach var="contactEntry" items="${employee.contacts}">
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<ru.ldwx.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
            </c:forEach>
        </p>
        <hr>
        <table cellpadding="2">
            <c:forEach var="sectionEntry" items="${employee.sections}">
                <jsp:useBean id="sectionEntry"
                             type="java.util.Map.Entry<ru.ldwx.model.SectionType, ru.ldwx.model.Section>"/>
                <c:set var="type" value="${sectionEntry.key}"/>
                <c:set var="section" value="${sectionEntry.value}"/>
                <jsp:useBean id="section" type="ru.ldwx.model.Section"/>
                <tr>
                    <td colspan="2"><h2><a name="type.name"><c:out value="${type.title}"/></a></h2></td>
                </tr>
                <c:choose>
                    <c:when test="${type=='OBJECTIVE'}">
                        <tr>
                            <td colspan="2">
                                <h3><%=((TextSection) section).getContent()%>
                                </h3>
                            </td>
                        </tr>
                    </c:when>
                    <c:when test="${type=='PERSONAL'}">
                        <tr>
                            <td colspan="2">
                                <%=((TextSection) section).getContent()%>
                            </td>
                        </tr>
                    </c:when>
                    <c:when test="${type=='QUALIFICATIONS'}">
                        <tr>
                            <td colspan="2">
                                <ul>
                                    <c:forEach var="item" items="<%=((ListSection) section).getItems()%>">
                                        <li><c:out value="${item}"/></li>
                                    </c:forEach>
                                </ul>
                            </td>
                        </tr>
                    </c:when>
                    <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                        <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganizations()%>">
                            <tr>
                                <td colspan="2">
                                    <c:choose>
                                        <c:when test="${empty org.homePage.url}">
                                            <h3><c:out value="${org.homePage.name}"/></h3>
                                        </c:when>
                                        <c:otherwise>
                                            <h3><a href="${org.homePage.url}"><c:out value="${org.homePage.name}"/></a></h3>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <c:forEach var="position" items="${org.positions}">
                                <jsp:useBean id="position" type="ru.ldwx.model.Organization.Position"/>
                                <tr>
                                    <td width="15%" style="vertical-align: top"><%=HtmlUtil.formatDates(position)%>
                                    </td>
                                    <td><b><c:out value="${position.title}"/></b><br><c:out value="${position.description}"/></td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:forEach>
        </table>
        <br/>
        <button onclick="window.history.back()">ОК</button>
    </section>
    </div>
    <div class="footer">
        <p>&copy; Создано для внутреннего использования <a href="/employees">Главная</a></p>
    </div>
</div>
</body>
</html>
