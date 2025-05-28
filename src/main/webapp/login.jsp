<!-- src/main/webapp/login.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Connexion</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body {
            background: linear-gradient(120deg, #89f7fe 0%, #66a6ff 100%);
            font-family: 'Segoe UI', Arial, sans-serif;
            margin: 0;
            padding: 0;
            min-height: 100vh;
        }
        .login-container {
            max-width: 350px;
            margin: 80px auto;
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 6px 24px rgba(0,0,0,0.10);
            padding: 40px 30px 30px 30px;
            text-align: center;
        }
        h2 {
            color: #2d8cf0;
            margin-bottom: 28px;
            font-weight: 600;
        }
        label {
            display: block;
            text-align: left;
            margin-bottom: 8px;
            color: #333;
            font-size: 1em;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 12px 10px;
            margin-bottom: 18px;
            border: 1px solid #e0e0e0;
            border-radius: 6px;
            font-size: 1em;
            background: #f7fafd;
            transition: border 0.2s;
        }
        input[type="text"]:focus, input[type="password"]:focus {
            border: 1.5px solid #2d8cf0;
            outline: none;
        }
        .login-btn {
            width: 100%;
            padding: 12px 0;
            background: #2d8cf0;
            color: #fff;
            border: none;
            border-radius: 6px;
            font-size: 1.1em;
            font-weight: 500;
            cursor: pointer;
            transition: background 0.2s;
            margin-top: 10px;
        }
        .login-btn:hover {
            background: #1766a3;
        }
        .error-message {
            color: #e74c3c;
            background: #fff0f0;
            border: 1px solid #f5c6cb;
            border-radius: 5px;
            padding: 8px 0;
            margin-bottom: 15px;
            font-size: 0.98em;
        }
        @media (max-width: 500px) {
            .login-container {
                margin: 30px 10px;
                padding: 25px 10px 20px 10px;
            }
        }
    </style>
</head>
<body>
<div class="login-container">
    <h2>Connexion</h2>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <label for="username">Nom d'utilisateur</label>
        <input type="text" id="username" name="username" autocomplete="username" required />

        <label for="password">Mot de passe</label>
        <input type="password" id="password" name="password" autocomplete="current-password" required />

        <button class="login-btn" type="submit">Se connecter</button>
    </form>
    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>
</div>
</body>
</html>