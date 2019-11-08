package com.xunlei.lib;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.xunlei.applyprocessor.Person;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import static com.xunlei.lib.utils.Consts.ANNOTATION_TYPE_PERSON;
import static com.xunlei.lib.utils.Consts.PACKAGE_OF_GENERATE_FILE;
import static com.xunlei.lib.utils.Consts.WARNING_TIPS;
import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * 引用当前lib的项目，编译后会自动生成代码。
 * 使用命令行编译，带上--stacktrace，可以看到log。
 * 参考：https://race604.com/annotation-processing/# 和 https://github.com/alibaba/ARouter。
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({ANNOTATION_TYPE_PERSON})
public class LearnProcessor extends BaseProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Person.class);
        logger.info("get element");
        for (Element element : elements) {
            if (element.getKind() == ElementKind.CLASS) {
                logger.info("element=" + element.getSimpleName());
                Person person = element.getAnnotation(Person.class);

                ParameterSpec.Builder nameParameterSpec = ParameterSpec.builder(TypeName.OBJECT, "name");
                ParameterSpec.Builder ageParameterSpec = ParameterSpec.builder(TypeName.INT, "age");

                MethodSpec.Builder printMethod = MethodSpec.methodBuilder("print")
                        .addParameter(nameParameterSpec.build())
                        .addParameter(ageParameterSpec.build())
                        .addModifiers(Modifier.PUBLIC);

                logger.info("start build javaFile");
                try {
                    JavaFile.builder(PACKAGE_OF_GENERATE_FILE,
                            TypeSpec.classBuilder("Jayden$$gen$$" + person.name())
                                    .addJavadoc(WARNING_TIPS)
                                    .addModifiers(PUBLIC)
                                    .addMethod(printMethod.build())
                                    .build()
                    ).build().writeTo(mFiler);
                } catch (IOException e) {
                    logger.info("exception=" + e.getLocalizedMessage());
                }
            }
        }
        return true;
    }
}
