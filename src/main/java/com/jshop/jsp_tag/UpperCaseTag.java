package com.jshop.jsp_tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.BodyContent;
import jakarta.servlet.jsp.tagext.BodyTagSupport;

import java.io.IOException;

public class UpperCaseTag extends BodyTagSupport {
    @Override
    public int doEndTag() throws JspException {
        BodyContent body = getBodyContent();
        try {
            pageContext.getOut().print(body.getString().toUpperCase());
        } catch (IOException e) {
            throw new JspException(e);
        }
        return EVAL_PAGE;
    }

}
