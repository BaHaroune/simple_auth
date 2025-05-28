<%@ page contentType="text/html;charset=UTF-8" language="java" %>
</html><%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Accueil</title>
    <style>
        body {
            background: #f5f7fa;
            font-family: 'Segoe UI', Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 400px;
            margin: 80px auto;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 16px rgba(0,0,0,0.08);
            padding: 40px 30px;
            text-align: center;
        }
        h2 {
            color: #2d8cf0;
            margin-bottom: 20px;
        }
        .success {
            color: #27ae60;
            font-size: 1.2em;
            margin-bottom: 10px;
        }
        .logout-btn {
            margin-top: 30px;
            padding: 10px 30px;
            background: #2d8cf0;
            color: #fff;
            border: none;
            border-radius: 5px;
            font-size: 1em;
            cursor: pointer;
            transition: background 0.2s;
        }
        .logout-btn:hover {
            background: #1766a3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Bienvenue&nbsp;!</h2>
        <div class="success">Vous êtes connecté(e) avec succès.</div>
        <form action="${pageContext.request.contextPath}/login" method="get">
            <button class="logout-btn" type="submit">Se déconnecter</button>
        </form>
    </div>
</body>
</html>