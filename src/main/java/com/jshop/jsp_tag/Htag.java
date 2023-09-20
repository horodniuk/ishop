package com.jshop.jsp_tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.util.Arrays;

public class Htag extends TagSupport {
    private String type;

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().println("<" + type + ">");
            return EVAL_BODY_INCLUDE;
        }catch (IOException e){
            throw new JspException(e);
        }
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().println("</" + type + ">");
            setDefaults();
            return EVAL_PAGE;
        } catch (IOException e) {
            throw new JspException(e);
        }
    }


    public Htag() {
        setDefaults();
    }

    public void setType(String type) {
        if(type != null){
            if(Arrays.asList(new String[]{"h1","h2","h3","h4","h5","h6"}).contains(type.toLowerCase())){
                this.type = type;
            }
        }
    }

    private void setDefaults() {
        type = "h6";
    }
}
