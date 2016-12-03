package com.apt;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.lang.model.element.TypeElement;

/**
 * Created by lizhaoxuan on 16/12/3.
 * 负责存储用于代码生成的信息
 */
public class ProxyInfo {
    private LinkedHashMap<String, String> domainMap;
    private TypeElement typeElement;

    public static String getProxyClassFullName() {
        return "com.cake.router.DomainMap$$PROXY";
    }

    TypeElement getTypeElement() {
        return typeElement;
    }

    void setTypeElement(TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    void putDomainMap(String domain, String classFullName) {
        if (domainMap == null) {
            domainMap = new LinkedHashMap<>();
        }
        domainMap.put(domain, classFullName);
    }

    String generateJavaCode() throws CakeRouterException {

        StringBuilder builder = new StringBuilder();
        builder.append("// Generated code from CakeRouter. Do not modify!\n");
        builder.append("package ").append("com.cake.router").append(";\n\n");

        builder.append("import com.cake.router.IDomainMap;\n");
        builder.append("import java.util.LinkedHashMap;\n");
        builder.append('\n');
        builder.append("public class DomainMap$$PROXY implements IDomainMap {\n");

        builder.append("private LinkedHashMap<String, String> domainMap = new LinkedHashMap<String, String>() { \n");

        builder.append("{ \n");
        if (domainMap != null) {
            for (Map.Entry<String, String> entry : domainMap.entrySet()) {
                builder.append("put(\"");
                builder.append(entry.getKey());
                builder.append("\", \"");
                builder.append(entry.getValue());
                builder.append("\");\n");
            }
        }
        builder.append("}\n");
        builder.append("};\n");

        builder.append("  @Override \n");
        builder.append("public String getFullClassName(String domain) { \n");
        builder.append("return domainMap.get(domain);\n");
        builder.append("}\n");

        builder.append("}\n");

        return builder.toString();
    }

}
