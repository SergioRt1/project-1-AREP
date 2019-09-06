package edu.escuelaing.arem.project.web_components.entities;

import java.util.Map;

public class ProcessedURL {
    private String path;
    private Map<String, String> parameters;

    public ProcessedURL(String path, Map<String, String> parameters) {
        this.path = path;
        this.parameters = parameters;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}
