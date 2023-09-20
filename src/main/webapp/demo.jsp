<%@ page pageEncoding="UTF-8" contentType="text/html;
charset=UTF-8" %>
<%! private int count1 = 1; %>
<jsp:declaration>
     private int count2=2;
</jsp:declaration>
count1: <%=count1%> <br/>
count2:<jsp:expression>count2</jsp:expression> <br/>

<% System.out.println("Hello1"); %>

<jsp:scriptlet>
System.out.println("Hello2");
</jsp:scriptlet>
<%-- JSP Comment --%>
<!-- HTML Comment -->
