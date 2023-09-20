<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="tag-files" tagdir="/WEB-INF/tags" %>
<tag-files:modal id="test1" message="Test" title="TITLE"/>
<tag-files:modal>
    <jsp:attribute name="id">test</jsp:attribute>
    <jsp:attribute name="message"><span><strong>Hello world</strong></span></jsp:attribute>
</tag-files:modal>
