package com.jshop.jsp_tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;

public class SwitchTag extends SimpleTagSupport {
    private Object value;
    private boolean processed;
    @Override
    public void doTag() throws JspException, IOException {
        processed = false;
        getJspBody().invoke(null);
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    public boolean isProcessed() {
        return processed;
    }
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
