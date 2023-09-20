<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Arrays" %>
<%@ page pageEncoding="UTF-8" trimDirectiveWhitespaces="false" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="core" uri="/WEB-INF/core.tld" %>

<h5>null</h5>
<% pageContext.setAttribute("items", null); %>
<core:forEach var="item" items="${items }">
    ${item }<br>
</core:forEach>
<h5>Iterable</h5>
<% pageContext.setAttribute("items", Arrays.asList(new String[]{"1", "2", "3"})); %>
<core:forEach var="item" items="${items }">
    ${item }<br>
</core:forEach>
<h5>Map</h5>
<%
    Map<String, Integer> map = new LinkedHashMap<>();
    map.put("1", 1);
    map.put("2", 2);
    map.put("3", 3);
    pageContext.setAttribute("items", map); %>
<core:forEach var="item" items="${items }">
    ${item }<br>
</core:forEach>
<h5>Iterator</h5>
<% pageContext.setAttribute("items", Arrays.asList(new String[]{"1", "2", "3"}).iterator()); %>
<core:forEach var="item" items="${items }">
    ${item }<br>
</core:forEach>
<h5>Enumeration</h5>
<% pageContext.setAttribute("items", Collections.enumeration(Arrays.asList(new String[]{"1", "2", "3"}))); %>
<core:forEach var="item" items="${items }">
    ${item }<br>
</core:forEach>
<h5>String</h5>
<% pageContext.setAttribute("items", "1,2,3"); %>
<core:forEach var="item" items="${items }">
    ${item }<br>
</core:forEach>
<h5>int[]</h5>
<% pageContext.setAttribute("items", new int[]{1, 2, 3}); %>
<core:forEach var="item" items="${items }">
    ${item }<br>
</core:forEach>
<h5>Integer[]</h5>
<% pageContext.setAttribute("items", new Integer[]{1, 2, 3}); %>
<core:forEach var="item" items="${items }">
    ${item }<br>
</core:forEach>
<h5>Object[]</h5>
<% pageContext.setAttribute("items", new Object[]{1, "2", true}); %>
<core:forEach var="item" items="${items }">
    ${item }<br>
</core:forEach>
<h5>String[]</h5>
<% pageContext.setAttribute("items", new String[]{"1", "2", "3"}); %>
<core:forEach var="item" items="${items }">
    ${item }<br>
</core:forEach>
<h5>double[]</h5>
<% pageContext.setAttribute("items", new double[]{1., 2., 3.}); %>
<core:forEach var="item" items="${items }">
    ${item }<br>
</core:forEach>
<h5>Double[]</h5>
<% pageContext.setAttribute("items", new Double[]{1., 2., 3.}); %>
<core:forEach var="item" items="${items }">
    ${item }<br>
</core:forEach>