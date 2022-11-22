<%@ tag description="Page template" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="end" fragment="true"%>
<%@ attribute name="head" fragment="true"%>
<%@ attribute name="headTitle" fragment="true"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
<jsp:invoke fragment="headTitle" />
	
	<script type="text/javascript">const CONTEXT_PATH = "<%= request.getContextPath() %>";</script>
	
	   <!-- Favicons -->
	<link rel="icon" href="<%=request.getContextPath()%>/public/images/logo.png">
	
	<title>Minhas Tarefas</title>
	
	<!-- Styles -->
	<link href="<%= request.getContextPath() %>/public/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/public/css/fontawesome/css/all.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/public/plugins/sweetalert2/sweetalert2.min.css">

<jsp:invoke fragment="head" />

</head>
<body>

	<jsp:doBody />

	<!-- Scripts -->
	<script src="<%=request.getContextPath()%>/public/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="<%=request.getContextPath()%>/public/plugins/jquery/jquery-3.6.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/public/plugins/sweetalert2/sweetalert2.all.min.js"></script>
	<script src="<%=request.getContextPath()%>/public/js/utils.js"></script>
    <script src="<%=request.getContextPath()%>/public/js/alerts.js"></script>

	<jsp:invoke fragment="end" />
</body>
</html>