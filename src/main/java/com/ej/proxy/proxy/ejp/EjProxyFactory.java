package com.ej.proxy.proxy.ejp;

import javassist.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EjProxyFactory {

    private static final Map<String, Class<?>> CLASS_NAMES = new ConcurrentHashMap<>();

    private static final String EXTENDS_CLASS_NAME_SUFFIX = "EjProxy";

    private static final String IMPLEMENTS_CLASS_NAME_SUFFIX = "EjProxyImpl";

    private static final String JOIN_STR = ",";

    private static final String EMPTY_STR = "";

    private static final String ONE_SPACE_STR = " ";

    private static final String FIELD_NAME_PREFIX = "_";

    private static final String MIDDLE_LINE_STR = "-";

    private static final String EMPTY_METHOD_BODY = "{}";

    private static final String ABSTRACT_METHOD_KEY = " abstract";

    private static final String CLASS_SUFFIX = ".class";

    private static final String STATIC_FIELD_TEMPLATE = "private static final java.lang.reflect.Method %s = %s.class.getDeclaredMethod(\"%s\",new Class[]{%s});";

    private static final String STATIC_FIELD_NONP_TEMPLATE = "private static final java.lang.reflect.Method %s = %s.class.getDeclaredMethod(\"%s\",new Class[0]);";

    private static final String TARGET_FIELD_TEMPLATE = "private %s target;";

    private static final String HANDLER_FIELD_TEMPLATE = "private %s handler;";

    private static final String METHOD_HEAD_TEMPLATE = "%s %s %s(%s)";

    private static final String METHOD_HEAD_THROWS_TEMPLATE = "%s %s %s(%s)throws %s";

    private static final String EXTENDS_METHOD_BODY_VOID_TEMPLATE = "{Object [] %s = new Object[%s];%s this.handler.before(this.target,%s,%s);try{this.target.%s(%s);}catch(Throwable e){this.handler.throwable(this.target,%s,%s,e);throw e;}finally{this.handler.after(this.target,%s,%s);}}";

    private static final String EXTENDS_METHOD_BODY_RETURN_TEMPLATE = "{Object [] %s = new Object[%s];%s this.handler.before(this.target,%s,%s);try{return this.target.%s(%s);}catch(Throwable e){this.handler.throwable(this.target,%s,%s,e);throw e;}finally{this.handler.after(this.target,%s,%s);}}";

    private static final String IMPLEMENTS_METHOD_BODY_VOID_TEMPLATE = "{Object [] %s = new Object[%s];%s this.handler.before(this,%s,%s);try{this.handler.current(this,%s,%s);}catch(Throwable e){this.handler.throwable(this,%s,%s,e);throw e;}finally{this.handler.after(this,%s,%s);}}";

    private static final String IMPLEMENTS_METHOD_BODY_RETURN_TEMPLATE = "{Object [] %s = new Object[%s];%s this.handler.before(this,%s,%s);try{return (%s)this.handler.current(this,%s,%s);}catch(Throwable e){this.handler.throwable(this,%s,%s,e);throw e;}finally{this.handler.after(this,%s,%s);}}";

    private static final String ARGS_INIT_TEMPLATE = "%s[%s] = %s;";


    public static <T> T newInterfaceEjProxy(Class<T> clazz, InterfaceEjProxyHandler interfaceEjProxyHandler) throws Exception {
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException("Only the proxy interface is supported");
        }
        String className = clazz.getName() + IMPLEMENTS_CLASS_NAME_SUFFIX;
        Class<?> proxyClass = CLASS_NAMES.get(className);
        if (proxyClass == null) {
            proxyClass = buildImplementsClass(clazz, className);
            CLASS_NAMES.put(className, proxyClass);
        }
        Constructor<?> constructor = proxyClass.getConstructor(InterfaceEjProxyHandler.class);
        return (T) constructor.newInstance(interfaceEjProxyHandler);
    }

    public static <T> T newEjProxy(T target, ClassEjProxyHandler classEjProxyHandler) throws Exception {
        Class<T> clazz = (Class<T>) target.getClass();
//        if (clazz != target.getClass()) {
//            throw new IllegalArgumentException("The objects of the proxy do not belong to the class");
//        }
        String className = clazz.getName() + EXTENDS_CLASS_NAME_SUFFIX;
        Class<?> proxyClass = CLASS_NAMES.get(className);
        if (proxyClass == null) {
            proxyClass = buildExtendsClass(clazz, className);
            CLASS_NAMES.put(className, proxyClass);
        }
        Constructor<?> constructor = proxyClass.getConstructor(clazz, ClassEjProxyHandler.class);
        return (T) constructor.newInstance(target, classEjProxyHandler);
    }

    private static final String IMPLEMENTS_CONSTRUCTOR_BODY = "{this.handler = $1;}";
    private static final String EXTENDS_CONSTRUCTOR_BODY = "{this.target = $1;this.handler = $2;}";

    private static Class<?> buildImplementsClass(Class<?> interfaceClass, String className) throws NotFoundException, CannotCompileException {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.makeClass(className);
        cc.setInterfaces(new CtClass[]{cp.get(interfaceClass.getName())});
        cc.addField(CtField.make(String.format(HANDLER_FIELD_TEMPLATE, InterfaceEjProxyHandler.class.getName()), cc));
        CtClass[] cctTypes = new CtClass[]{cp.get(InterfaceEjProxyHandler.class.getName())};
        buildConstructorMethod(cc, cctTypes, IMPLEMENTS_CONSTRUCTOR_BODY);
        Method[] methods = interfaceClass.getDeclaredMethods();
        for (Method method : methods) {
            buildImplementsMethod(cc, interfaceClass, method);
        }
        return cc.toClass();
    }

    private static Class<?> buildExtendsClass(Class<?> targetClass, String className) throws NotFoundException, CannotCompileException {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.makeClass(className);
        cc.setSuperclass(cp.get(targetClass.getName()));
        cc.addField(CtField.make(String.format(TARGET_FIELD_TEMPLATE, targetClass.getName()), cc));
        cc.addField(CtField.make(String.format(HANDLER_FIELD_TEMPLATE, ClassEjProxyHandler.class.getName()), cc));
        CtClass[] cctTypes = new CtClass[]{cp.get(targetClass.getName()), cp.get(ClassEjProxyHandler.class.getName())};
        buildConstructorMethod(cc, cctTypes, EXTENDS_CONSTRUCTOR_BODY);
        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            buildExtendsMethod(cc, targetClass, method);
        }
        return cc.toClass();
    }

    private static void buildConstructorMethod(CtClass cc, CtClass[] cctTypes, String parameterConstructorMethodBody) throws CannotCompileException {
        CtConstructor cct = CtNewConstructor.make(cctTypes, null, CtNewConstructor.PASS_NONE, null, null, cc);
        cct.setBody(parameterConstructorMethodBody);
        cc.addConstructor(cct);
        cct = CtNewConstructor.make(new CtClass[]{}, null, CtNewConstructor.PASS_NONE, null, null, cc);
        cct.setBody(EMPTY_METHOD_BODY);
        cc.addConstructor(cct);
    }

    private static void buildImplementsMethod(CtClass cc, Class<?> superClass, Method method) throws CannotCompileException {
        int modifiers = method.getModifiers();
        if (Modifier.isFinal(modifiers) || Modifier.isPrivate(modifiers) || Modifier.isStatic(modifiers)) {
            return;
        }
        //构建静态属性,避免运行是反射调用
        String staticFieldName = buildStaticField(method, cc, superClass);
        //构建实现方法
        ////组装方法头部
        String extendsMethodHeadString = buildMethodHead(method);
        ////组装方法体
        String extendsMethodBodyString = buildImplementsMethodBody(method, staticFieldName);
        CtMethod cm = CtMethod.make(extendsMethodHeadString + extendsMethodBodyString, cc);
        cc.addMethod(cm);
    }


    private static void buildExtendsMethod(CtClass cc, Class<?> superClass, Method method) throws CannotCompileException {
        int modifiers = method.getModifiers();
        if (Modifier.isFinal(modifiers) || Modifier.isPrivate(modifiers) || Modifier.isStatic(modifiers)) {
            return;
        }
        //构建静态属性,避免运行是反射调用
        String staticFieldName = buildStaticField(method, cc, superClass);
        //构建继承方法
        ////组装方法头部
        String extendsMethodHeadString = buildMethodHead(method);
        ////组装方法体
        String extendsMethodBodyString = buildExtendsMethodBody(method, staticFieldName);
        CtMethod cm = CtMethod.make(extendsMethodHeadString + extendsMethodBodyString, cc);
        cc.addMethod(cm);
    }

    private static String randomName() {
        return FIELD_NAME_PREFIX + UUID.randomUUID().toString().replace(MIDDLE_LINE_STR, EMPTY_STR);
    }


    /**
     * 构建静态属性,避免运行是反射调用
     *
     * @param method
     * @param cc
     * @param parentClass
     * @return java.lang.String
     * @auther: Evan·Jiang
     * @date: 2020/1/17 14:59
     */
    private static String buildStaticField(Method method, CtClass cc, Class<?> parentClass) throws CannotCompileException {
        List<String> parameterClassStrings = Arrays.asList(method.getParameterTypes()).stream().map(c -> {
            return c.getName() + CLASS_SUFFIX;
        }).collect(Collectors.toList());
        String staticFieldName = randomName();
        String staticField = null;
        if (parameterClassStrings.isEmpty()) {
            staticField = String.format(STATIC_FIELD_NONP_TEMPLATE, staticFieldName, parentClass.getName(), method.getName());
        } else {
            staticField = String.format(STATIC_FIELD_TEMPLATE, staticFieldName, parentClass.getName(), method.getName(), String.join(JOIN_STR, parameterClassStrings));
        }
        cc.addField(CtField.make(staticField, cc));
        return staticFieldName;
    }

    private static String buildMethodHead(Method method) {
        ////方法参数申明
        List<String> methodParametersStatementList = Arrays.asList(method.getParameters()).stream().map(m -> {
            return m.getType().getName() + ONE_SPACE_STR + m.getName();
        }).collect(Collectors.toList());
        String methodParametersStatementString = String.join(JOIN_STR, methodParametersStatementList);
        ////方法异常申明
        List<String> methodThrowsStatementList = Arrays.asList(method.getExceptionTypes()).stream().map(Class::getName).collect(Collectors.toList());
        String methodThrowsStatementString = String.join(JOIN_STR, methodThrowsStatementList);
        ////组装方法头部
        String extendsMethodHeadString = null;
        String openLevel = Modifier.toString(method.getModifiers());
        int idx = 0;
        if ((idx = openLevel.indexOf(ABSTRACT_METHOD_KEY)) > 0) {
            openLevel = openLevel.substring(0, idx);
        }
        if (methodThrowsStatementList.isEmpty()) {
            extendsMethodHeadString = String.format(METHOD_HEAD_TEMPLATE, openLevel, method.getReturnType().getName(), method.getName(), methodParametersStatementString);
        } else {
            extendsMethodHeadString = String.format(METHOD_HEAD_THROWS_TEMPLATE, openLevel, method.getReturnType().getName(), method.getName(), methodParametersStatementString, methodThrowsStatementString);
        }
        return extendsMethodHeadString;
    }


    private static String buildExtendsMethodBody(Method method, String staticFieldName) {
        String argsName = randomName();
        Parameter[] parameters = method.getParameters();
        int argsNum = parameters == null ? 0 : parameters.length;
        String argsAssignmentCode = buildHandlerArgsAssignmentCode(parameters, argsName);
        String parentArgs = buildParentArgs(parameters);
//
//        List<String> methodParametersInvokeList = Arrays.asList(method.getParameters()).stream().map(Parameter::getName).collect(Collectors.toList());
//        StringBuilder argsInitCode = new StringBuilder();
//        for (int idx = 0; idx < methodParametersInvokeList.size(); idx++) {
//            argsInitCode.append(String.format(ARGS_INIT_TEMPLATE, argsName, idx, methodParametersInvokeList.get(idx)));
//        }
//        String methodParametersInvokeString = String.join(JOIN_STR, methodParametersInvokeList);
        String extendsMethodBodyString = null;
        if (method.getReturnType() == void.class) {
            extendsMethodBodyString = String.format(EXTENDS_METHOD_BODY_VOID_TEMPLATE
                    , argsName, argsNum, argsAssignmentCode
                    , staticFieldName, argsName
                    , method.getName(), parentArgs
                    , staticFieldName, argsName
                    , staticFieldName, argsName
            );
        } else {
            extendsMethodBodyString = String.format(EXTENDS_METHOD_BODY_RETURN_TEMPLATE
                    , argsName, argsNum, argsAssignmentCode
                    , staticFieldName, argsName
                    , method.getName(), parentArgs
                    , staticFieldName, argsName
                    , staticFieldName, argsName
            );
        }
        return extendsMethodBodyString;
    }


    private static String buildImplementsMethodBody(Method method, String staticFieldName) {
        String argsName = randomName();
        Parameter[] parameters = method.getParameters();
        int argsNum = parameters == null ? 0 : parameters.length;
        String argsAssignmentCode = buildHandlerArgsAssignmentCode(parameters, argsName);
//        List<String> methodParametersInvokeList = Arrays.asList(method.getParameters()).stream().map(Parameter::getName).collect(Collectors.toList());
//        StringBuilder argsInitCode = new StringBuilder();
//        for (int idx = 0; idx < methodParametersInvokeList.size(); idx++) {
//            argsInitCode.append(String.format(ARGS_INIT_TEMPLATE, argsName, idx, methodParametersInvokeList.get(idx)));
//        }
        String implementsMethodBodyString = null;
        if (method.getReturnType() == void.class) {
            implementsMethodBodyString = String.format(IMPLEMENTS_METHOD_BODY_VOID_TEMPLATE
                    , argsName, argsNum, argsAssignmentCode
                    , staticFieldName, argsName
                    , staticFieldName, argsName
                    , staticFieldName, argsName
                    , staticFieldName, argsName);
        } else {
            implementsMethodBodyString = String.format(IMPLEMENTS_METHOD_BODY_RETURN_TEMPLATE
                    , argsName, argsNum, argsAssignmentCode
                    , staticFieldName, argsName, method.getReturnType().getName()
                    , staticFieldName, argsName
                    , staticFieldName, argsName
                    , staticFieldName, argsName);
        }
        return implementsMethodBodyString;
    }

    private static String buildHandlerArgsAssignmentCode(Parameter[] parameters, String argsName) {
        StringBuilder argsAssignmentCode = new StringBuilder();
        if (parameters != null) {
            for (int idx = 0; idx < parameters.length; idx++) {
                argsAssignmentCode.append(String.format(ARGS_INIT_TEMPLATE, argsName, idx, parameters[idx].getName()));
            }
        }
        return argsAssignmentCode.toString();
    }

    private static String buildParentArgs(Parameter[] parameters){
        return String.join(JOIN_STR,Arrays.asList(parameters).stream().map(Parameter::getName).collect(Collectors.toList()));
    }
}
