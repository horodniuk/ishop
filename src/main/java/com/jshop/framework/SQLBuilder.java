package com.jshop.framework;

public interface SQLBuilder {
    SearchQuery build(Object... builderParams);
}
