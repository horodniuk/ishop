package com.jshop.jsp_tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StaticTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
       try{
           JspWriter out = pageContext.getOut();
           out.println("Current date: " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
           return SKIP_BODY;
       } catch (IOException e){
           throw new JspException(e);
       }
    }
}
