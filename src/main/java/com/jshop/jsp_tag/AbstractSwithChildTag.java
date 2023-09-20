package com.jshop.jsp_tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.JspTag;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;

public class AbstractSwithChildTag extends SimpleTagSupport {



    @Override
    public final void doTag() throws JspException, IOException {
        SwitchTag sw = getSwitchTag();
        if (!sw.isProcessed() && shouldBeProcessed(sw)) {
            sw.setProcessed(true);
            getJspBody().invoke(null);
        }
    }

    protected boolean shouldBeProcessed(SwitchTag sw) {
        return false;
    }

    protected final SwitchTag getSwitchTag() throws JspException {
        JspTag tag = getParent();
        if (tag instanceof SwitchTag) {
            return ((SwitchTag) tag);
        } else {
            throw new JspException("case tag should be inside switch tag! Current parent is: " + (tag != null ? tag.getClass() : null));
        }
    }
}
