<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>접근 거부됨 - 403</title>
    <style>
        body {
            background-color: #f8f8f8;
            font-family: 'Arial', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .container {
            text-align: center;
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            color: #ff0000;
            margin-bottom: 20px;
        }

        p {
            color: #333;
            margin-bottom: 10px;
        }

        a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
        }

        a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>403 - 접근이 거부되었습니다</h1>
        <p>보안상의 이유로 이 페이지에 대한 접근이 차단되었습니다.</p>
        <p>문제가 계속될 경우, 시스템 관리자에게 문의하시기 바랍니다.</p>
        <a href="/ex01/">홈으로 돌아가기</a>
    </div>
</body>
</html>
