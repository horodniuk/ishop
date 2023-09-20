<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Collections"%>
<%@ page import="java.util.Arrays"%>
<%@ page pageEncoding="UTF-8" trimDirectiveWhitespaces="false" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="core" uri="/WEB-INF/core.tld"%>

<h5>null case</h5>
<core:switch value="${null }">
    <core:case value="${null }">null</core:case>
    <core:case value="s1">s1</core:case>
    <core:case value="s2">s2</core:case>
    <core:default>default</core:default>
</core:switch>

<h5>case</h5>
<core:switch value="s1">
    <core:case value="s0">s0</core:case>
    <core:case value="s1">s1</core:case>
    <core:case value="s2">s2</core:case>
    <core:default>default</core:default>
</core:switch>

<h5>case with duplicates</h5>
<core:switch value="s1">
    <core:case value="s0">s0</core:case>
    <core:case value="s1">s1</core:case>
    <core:case value="s1">S1</core:case>
    <core:default>default</core:default>
</core:switch>

<h5>defaults</h5>
<core:switch value="s3">
    <core:case value="s0">s0</core:case>
    <core:case value="s1">s1</core:case>
    <core:case value="s2">s2</core:case>
    <core:default>default</core:default>
</core:switch>

<h5>nothing</h5>
<core:switch value="s3">
    <core:case value="s0">s0</core:case>
    <core:case value="s1">s1</core:case>
    <core:case value="s2">s2</core:case>
</core:switch>

<h5>Just text</h5>
<core:switch value="s3">
    Just text
</core:switch>
