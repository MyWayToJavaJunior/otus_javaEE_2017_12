<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="static servlets.ServletConsts.*"%>
<!DOCTYPE html>
<html>
    <head>
        <title>HomeWork02</title>
    	<link rel="icon" href ="favicon.png" type= "image/x-icon" >
	    <link rel="shortcut icon" href ="favicon.png" typ e="image/x-icon" >
        <link rel="stylesheet" type="text/css" href="css/styles.css">
    </head>
<body>
    <div class="main-container centrator">
		<div class = "header">OTUS - HomeWork #02</div>
            <div class = "actions-header">Action result</div>
            <div class = "action-result-container">
                <div class = <%= request.getAttribute(ATTR_RESULT_MESSAGE_CLASS) %>><%= request.getAttribute(ATTR_RESULT_MESSAGE) %></div>
            </div>
            <br>
            <center><a href = "/" class = "back-href">Back</a></center>
	</div>


</body>
</html>