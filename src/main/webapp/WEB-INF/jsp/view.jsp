<%@ page import="local.ldwx.model.TextSection" %>
<%@ page import="local.ldwx.model.ListSection" %>
<%@ page import="local.ldwx.model.OrganizationSection" %>
<%@ page import="local.ldwx.util.HtmlUtil" %>
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
    <jsp:useBean id="employee" type="local.ldwx.model.Employee" scope="request"/>
    <title>${employee.fullName}</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div class="wrapper">
    <div class="header">
        <div class="logo"><a href="">Our<span class="black">Company</span><span class="gray">.ru</span></a>
            <p>ГК Компания</p></div>
        <ul class="nav">
            <li><a href="#" class="active">Главная</a></li>
            <li><a href="#">О нас</a></li>
            <li><a href="#">Новости</a></li>
            <li><a href="#">Контакты</a></li>
        </ul>
    </div>
    <section>
        <h1>${employee.fullName}<a href="employees?uuid=${employee.uuid}&action=edit"><img src="../../img/pencil.png" alt="Редактировать" title="Редактировать данные сотрудника"></a></h1>
        <p>
            <c:forEach var="contactEntry" items="${employee.contacts}">
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<local.ldwx.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
            </c:forEach>
        </p>
        <hr>
        <table cellpadding="2">
            <c:forEach var="sectionEntry" items="${employee.sections}">
                <jsp:useBean id="sectionEntry"
                             type="java.util.Map.Entry<local.ldwx.model.SectionType, local.ldwx.model.Section>"/>
                <c:set var="type" value="${sectionEntry.key}"/>
                <c:set var="section" value="${sectionEntry.value}"/>
                <jsp:useBean id="section" type="local.ldwx.model.Section"/>
                <tr>
                    <td colspan="2"><h2><a name="type.name">${type.title}</a></h2></td>
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
                                        <li>${item}</li>
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
                                            <h3>${org.homePage.name}</h3>
                                        </c:when>
                                        <c:otherwise>
                                            <h3><a href="${org.homePage.url}">${org.homePage.name}</a></h3>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <c:forEach var="position" items="${org.positions}">
                                <jsp:useBean id="position" type="local.ldwx.model.Organization.Position"/>
                                <tr>
                                    <td width="15%" style="vertical-align: top"><%=HtmlUtil.formatDates(position)%>
                                    </td>
                                    <td><b>${position.title}</b><br>${position.description}</td>
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
    <div class="footer">
        <p>&copy; Создано для внутреннего использования <a href="#">Главная</a></p>
    </div>
</div>
</body>
</html>
