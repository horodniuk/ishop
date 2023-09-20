<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="java-classes" uri="/WEB-INF/tags.tld"%>
<java-classes:static />
<br>

<java-classes:attr condition="true" /><br>
<java-classes:attr condition="false" /><br>
<java-classes:attr condition="${2 > 1 }" /><br>
<java-classes:attr condition="<%=2 < 1 %>" /><br>
<br>
<java-classes:h type="h5">h5</java-classes:h>
<java-classes:h type="h4">h4</java-classes:h>
<java-classes:h type="h3">h3</java-classes:h>
<java-classes:h type="h2">h2</java-classes:h>
<java-classes:h type="h1">h1</java-classes:h>
<java-classes:h type="h0">h6</java-classes:h>
<br>
<java-classes:loop count="-2">NULL</java-classes:loop><br>
<java-classes:loop count="5">5 </java-classes:loop><br>
<java-classes:loop count="${ 1 + 4 }">${ 1 + 4 } </java-classes:loop><br>
<java-classes:loop count="<%=1+4 %>">
    <java-classes:attr condition="${2 > 1 }" />
</java-classes:loop>
<br>

<java-classes:uppercase>This is test!</java-classes:uppercase>


