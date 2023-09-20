package com.jshop.jsp_tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

public class AttrTag extends TagSupport {
    private boolean condition;
    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            if(condition) {
                out.println("Condition is true");
            } else {
                out.println("Condition is false");
            }
            condition = false;
            return SKIP_BODY;
        } catch (IOException e) {
            throw new JspException(e);
        }
    }
    public void setCondition(boolean condition) {
        this.condition = condition;
    }
}
