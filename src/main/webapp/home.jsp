<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    if (request.getAttribute("users") == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<html>
<head>
    <title>Liste des utilisateurs</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f8fb;
        }

        table {
            border-collapse: collapse;
            width: 80%;
            margin: 40px auto;
            background: #fff;
        }

        th, td {
            border: 1px solid #e0e0e0;
            padding: 12px 18px;
            text-align: left;
        }

        th {
            background: #2d8cf0;
            color: #fff;
        }

        tr:nth-child(even) {
            background: #f7fafd;
        }

        h2 {
            text-align: center;
            color: #2d8cf0;
            margin-top: 30px;
        }
    </style>
</head>
<body>
<h2>Liste des utilisateurs</h2>
<a href="${pageContext.request.contextPath}/login?action=ajout"
   style="display: block; width: 200px; margin: 30px auto 0 auto; text-align: center; background: #2d8cf0; color: #fff; padding: 12px 0; border-radius: 5px; text-decoration: none; font-weight: bold;">
    Ajouter un utilisateur
</a>
<table>
    <tr>
        <th>ID</th>
        <th>Nom d'utilisateur</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>
                <a href="${pageContext.request.contextPath}/login?action=edit&id=${user.id}">Modifier</a>
                <a style="color: #e74c3c" href="${pageContext.request.contextPath}/login?action=delete&id=${user.id}"
                   onclick="return confirm('Supprimer cet utilisateur ?');">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>