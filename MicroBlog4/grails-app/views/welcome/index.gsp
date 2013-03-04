<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page import="microblog4.User"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home Page</title>
  </head>
  <body>
    <h1>Welcome to the worlds most advaced MicroBloger</h1>
    <p># of users = ${User.count()}</p>
  <g:link controller="Welcome" action="about">About the Creator</g:link>
   <g:link controller="User" action="list">Users</g:link>
    <g:link controller="MicroPost" action="list">Dont touch this</g:link>
  </body>
</html>
