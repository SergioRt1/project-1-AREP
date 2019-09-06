package edu.escuelaing.arem.project.web_components.entities;

import edu.escuelaing.arem.project.web_components.URLHandler;

import java.util.Map;

public class HandlerModel {
    private URLHandler handler;
    private Map<String, Class<?>> parameterTypes;

    public HandlerModel(URLHandler handler, Map<String, Class<?>> parameterTypes) {
        this.handler = handler;
        this.parameterTypes = parameterTypes;
    }

    public URLHandler getHandler() {
        return handler;
    }

    public void setHandler(URLHandler handler) {
        this.handler = handler;
    }

    public Map<String, Class<?>> getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Map<String, Class<?>> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}
