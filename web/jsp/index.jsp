<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>XML parser</title>
</head>
<body>
<form action="upload" enctype="multipart/form-data" method="POST">
    <h3>Upload file:</h3>
    <input name="content" type="file" required>
    <output>${result}</output>
    <h3>Parser type:</h3>
    <p><input name="parser" type="radio" value="DOM" required>DOM</p>
    <p><input name="parser" type="radio" value="SAX" required>SAX</p>
    <p><input name="parser" type="radio" value="STAX" required>STAX</p>
    <input type="submit" value="Upload File">
</form>
<table bordercolor="black" border="1">
    <tr>
        <th>id</th>
        <th>name</th>
        <th>origin</th>
        <th>price</th>
        <th>isPeripheral</th>
        <th>powerUsage</th>
        <th>withCooler</th>
        <th>componentGroup</th>
        <th>portType</th>
        <th>critical</th>
    </tr>
    <c:forEach var="device" items="${devices}">
        <tr>
            <th><c:out value="${device.id}"/></th>
            <th><c:out value="${device.name}"/></th>
            <th><c:out value="${device.origin}"/></th>
            <th><c:out value="${device.price}"/></th>
            <th><c:out value="${device.type.isPeripheral}"/></th>
            <th><c:out value="${device.type.powerUsage}"/></th>
            <th><c:out value="${device.type.withCooler}"/></th>
            <th><c:out value="${device.type.componentGroup}"/></th>
            <th><c:out value="${device.type.portType}"/></th>
            <th><c:out value="${device.critical}"/></th>
        </tr>
    </c:forEach>
</table>
</body>
</html>
