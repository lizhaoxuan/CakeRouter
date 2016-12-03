package com.apt;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RouterProcessor extends AbstractProcessor {

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //获取ProxyInfo
        ProxyInfo proxyInfo = getProxyMap(roundEnv);
        if (proxyInfo.getTypeElement() != null) {
            writeCode(proxyInfo);
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(CakeRouterUrl.class.getCanonicalName());
        return types;
    }

    private ProxyInfo getProxyMap(RoundEnvironment roundEnv) {
        ProxyInfo proxyInfo = new ProxyInfo();
        for (Element element : roundEnv.getElementsAnnotatedWith(CakeRouterUrl.class)) {
            //target相同只能强转。不同使用getEnclosingElement
            TypeElement classElement = (TypeElement) element;

            //全类名
            String fullClassName = classElement.getQualifiedName().toString();

            String domain = classElement.getAnnotation(CakeRouterUrl.class).value();

            print("fullClassName: " + fullClassName +
                    ",  domain: " + domain);
            proxyInfo.setTypeElement(classElement);
            proxyInfo.putDomainMap(domain, fullClassName);
        }
        return proxyInfo;
    }

    private void writeCode(ProxyInfo proxyInfo) {
        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile(
                    ProxyInfo.getProxyClassFullName(),
                    proxyInfo.getTypeElement());
            Writer writer = jfo.openWriter();
            writer.write(proxyInfo.generateJavaCode());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            error(proxyInfo.getTypeElement(),
                    "Unable to write injector for type %s: %s",
                    proxyInfo.getTypeElement(), e.getMessage());
        } catch (CakeRouterException e) {
            error(proxyInfo.getTypeElement(),
                    "The use of irregular %s: %s",
                    proxyInfo.getTypeElement(), e.getMessage());
        }
    }

    private void print(String message) {
        messager.printMessage(Diagnostic.Kind.NOTE, message);
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }

}
