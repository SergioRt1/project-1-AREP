package edu.escuelaing.arem.project.web_components;

import edu.escuelaing.arem.project.web_components.entities.HandlerModel;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
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
    private final Map<String, HandlerModel> URLMap;

    public URLMapper() {
        this.URLMap = new HashMap<>();
    }

    public void loadWebMethods() {
        List<Method> methods = getWebMethods();
        for (Method method : methods) {
            String URL = "/apps/" + method.getAnnotation(Web.class).value();

            Map<String, Class<?>> parameterTypes = new HashMap<>();
            for (Parameter parameter : method.getParameters()) {
                if (parameter.getAnnotation(Param.class) != null) {
                    String id = parameter.getAnnotation(Param.class).value();
                    parameterTypes.put(id, parameter.getType());
                }
            }
            HandlerModel handler = new HandlerModel(method::invoke, parameterTypes);

            URLMap.put(URL, handler);
            System.out.printf("Mapped[%s]\n", URL);
        }
    }

    public HandlerModel getHandler(String url) {
        return URLMap.get(url);
    }

    public Map<String, HandlerModel> getURLMap() {
        return URLMap;
    }

    private List<Method> getWebMethods() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("edu.escuelaing.arem.project"))
                .setScanners(new MethodAnnotationsScanner())
        );
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Web.class);

        return new ArrayList<>(methods);
    }
}
