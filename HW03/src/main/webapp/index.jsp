<%@page import="static ru.otus.homework03.servlets.ServletConsts.*"%>
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
		<div class = "header">OTUS - HomeWork #03</div>
		<center>
    	    <div class = "actions-header">Available actions</div>
    	    <ul>
                <li><a href = "<%= REQUEST_CREATE_XML_FILE_FOR_EMPLOYEES_LIST %>" class = "options-item">Stage #1 - Create XML file for employees list</a></li>
                <li><a href = "<%= REQUEST_CREATE_XML_FILE_FOR_RICH_EMPLOYEES %>" class = "options-item">Stage #2 - Load data for employees who have salary higher than average from XML file</a></li>
                <li><a href = "<%= REQUEST_CONVERT_XML_FILE_TO_JSON_FILE %>" class = "options-item">Stage #3 - Convert XML file to JSON file</a></li>
                <li><a href = "<%= REQUEST_GET_LIST_OF_ODD_EMPLOYEES_FROM_JSON_FILE %>" class = "options-item">Stage #3 - Get list of odd employees from JSON file</a></li>
            </ul>
	    </center>
	</div>


</body>
</html>
