package edu.escuelaing.arem.project.web_components;

@FunctionalInterface
public interface URLHandler {
    Object process(Object var1, Object[] params) throws ReflectiveOperationException;
}
