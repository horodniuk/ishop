package com.jshop.jsp_tag;

public class DefaultTag extends AbstractSwithChildTag {
    @Override
    protected boolean shouldBeProcessed(SwitchTag sw) {
        return true;
    }
}
