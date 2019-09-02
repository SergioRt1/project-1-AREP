package edu.escuelaing.arem.project.web_components;

@FunctionalInterface
public interface URLHandler {
    Object process() throws ReflectiveOperationException;
}
