package com.ej;


public class Test {
/**
 private OrderDto orderDto = new OrderDto();


 @org.junit.Test public void jdk() {
 //这里换成OrderServiceImpl是否可行
 OrderService orderService = new OrderServiceImpl();
 JdkProxyHandler<OrderService> jdkProxyHandler = new JdkProxyHandler<>(orderService);

 OrderService proxy = JdkProxyFactory.getProxy(jdkProxyHandler);
 proxy.checkParams(orderDto);
 proxy.createOrder(orderDto);
 proxy.sendSms(orderDto);
 System.out.println(proxy);
 }


 @org.junit.Test public void cglib() throws InterruptedException {
 OrderService proxy = CglibProxyFactory.getProxy(OrderService.class, new MethodInterceptor() {
 @Override public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
 System.out.println(method);
 return null;
 }
 });
 proxy.checkParams(orderDto);
 proxy.createOrder(orderDto);
 proxy.sendSms(orderDto);
 System.out.println(proxy);
 System.out.println("============================");
 OrderServiceImpl2 proxy2 = CglibProxyFactory.getProxy(OrderServiceImpl2.class, new MethodInterceptor() {
 @Override public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
 Object invoke = null;
 try {
 System.out.println("执行目标方法前我可以干点啥呢？？？");
 System.out.println("===================" + method.toString());
 invoke = proxy.invokeSuper(obj, args);
 } finally {
 System.out.println("执行目标方法后我还可以干点啥呢？？？\n");
 }
 return invoke;
 }
 });
 proxy2.checkParams(orderDto);
 proxy2.createOrder(orderDto);
 proxy2.sendSms(orderDto);
 proxy2.toString();
 //System.out.println(proxy1);
 Thread.sleep(1000000L);
 }

 @org.junit.Test public void ejp() throws Exception {
 OrderServiceImpl2 orderServiceImpl2 = new OrderServiceImpl2();
 OrderServiceImpl2 proxy2 = EjProxyFactory.newEjProxy(orderServiceImpl2, new ClassEjProxyHandler() {
 @Override public void before(Object target, Method method, Object[] args) {
 System.out.println("执行目标方法前我可以干点啥呢？？？");
 }

 @Override public void throwable(Object target, Method method, Object[] args, Throwable e) {
 System.out.println("执行目标方法出异常后我可以干点啥呢？？？");
 }

 @Override public void after(Object target, Method method, Object[] args) {
 System.out.println("执行目标方法后我还可以干点啥呢？？？\n");
 }
 });
 proxy2.checkParams(orderDto);
 proxy2.createOrder(orderDto);
 proxy2.sendSms(orderDto);
 proxy2.toString();
 }

 @org.junit.Test public void compare() throws Exception {
 final OrderService target = new OrderServiceImpl();
 OrderService jdkProxy = JdkProxyFactory.getProxy(target, new InvocationHandler() {
 @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
 Object invoke = null;
 try {
 invoke = method.invoke(target, args);
 } finally {
 }
 return invoke;
 }
 });
 OrderService cglibProxy = CglibProxyFactory.getProxy(target.getClass(), new MethodInterceptor() {
 @Override public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
 Object invoke = null;
 try {
 invoke = proxy.invokeSuper(obj, args);
 } finally {
 }
 return invoke;
 }
 });
 OrderService ejProxy = EjProxyFactory.newEjProxy(target, new ClassEjProxyHandler() {
 @Override public void before(Object target, Method method, Object[] args) {

 }

 @Override public void throwable(Object target, Method method, Object[] args, Throwable e) {

 }

 @Override public void after(Object target, Method method, Object[] args) {

 }
 });
 long jdkTime = run(jdkProxy);
 long cglibTime = run(cglibProxy);
 long ejTime = run(ejProxy);
 System.out.println("jdk:"+jdkTime);
 System.out.println("cglib:"+cglibTime);
 System.out.println("ej:"+ejTime);
 System.out.println();
 jdkTime = run(jdkProxy);
 cglibTime = run(cglibProxy);
 ejTime = run(ejProxy);
 System.out.println("jdk:"+jdkTime);
 System.out.println("cglib:"+cglibTime);
 System.out.println("ej:"+ejTime);
 System.out.println();
 jdkTime = run(jdkProxy);
 cglibTime = run(cglibProxy);
 ejTime = run(ejProxy);
 System.out.println("jdk:"+jdkTime);
 System.out.println("cglib:"+cglibTime);
 System.out.println("ej:"+ejTime);
 System.out.println(jdkProxy);
 System.out.println(cglibProxy);
 System.out.println(ejProxy);
 Thread.sleep(1000000L);
 }

 private long run(OrderService orderService){
 long times = 80000000L;
 long start = System.currentTimeMillis();
 for (long idx = 0; idx < times; idx++) {
 orderService.checkParams(orderDto);
 orderService.createOrder(orderDto);
 orderService.sendSms(orderDto);
 }
 return System.currentTimeMillis() - start;
 }
 **/
}
