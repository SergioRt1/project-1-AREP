package edu.escuelaing.arem.project.web_components;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class URLMapper {
    private final Map<String, URLHandler> URLMap;

    public URLMapper() {
        this.URLMap = new HashMap<>();
    }

    public void loadWebMethods() {
        List<Method> methods = getWebMethods();
        for (Method method : methods) {
            String URL = "/apps/" + method.getAnnotation(Web.class).value();
            Parameter[] parameters = method.getParameters();
//            URLHandler handler;
//            if (parameters.length != 0 && String.class.isAssignableFrom(parameters[0].getType())) {
//                handler = () -> method.invoke("");
//            } else {
//                handler = () -> method.invoke(null);
//            }
            URLMap.put(URL, () -> method.invoke(null));
            System.out.printf("Mapped[%s]\n", URL);
        }
    }

    public URLHandler getHandler(String url) {
        return URLMap.get(url);
    }

    public Map<String, URLHandler> getURLMap() {
        return URLMap;
    }

    private List<Method> getWebMethods() {
        List<Method> methods = new ArrayList<>();
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("edu.escuelaing.arem.project"))
                .setScanners(new SubTypesScanner(false))
        );
        Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);

        for (Class loadedClass : allClasses) {
            for (Method method : loadedClass.getMethods()) {
                if (method.isAnnotationPresent(Web.class)) {
                    methods.add(method);
                }
            }
        }

        return methods;
    }
}
