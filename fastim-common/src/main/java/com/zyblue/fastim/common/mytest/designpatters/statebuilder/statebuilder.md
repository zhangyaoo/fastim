参考 https://mp.weixin.qq.com/s/0GfCOUEw4svvSQVoShjJDw

设计模式：

- 状态模式
- 策略模式
- 继承模式
- 模板模式

### 1 纵向解决业务隔离和流程编排

  1. 每一个订单状态流转的流程中，都会有三个流程：校验、业务逻辑执行、数据更新持久化；于是再次抽象，可以将一个状态流转分为数据准备（prepare）——>校验（check）——>获取下一个状态（getNextState）——>业务逻辑执行（action）——>数据持久化（save）——>后续处理（after）这六个阶段
![09-icRnjQ](https://gitee.com/drunk_and_happy/oss/raw/master/mypic/statebuilder3.png
)
  2. 基础抽象类负责业务的模板方法拼装 AbstractStateProcessor
  
  3. 对于复杂的业务、其校验规则和校验逻辑也会更加复杂，将校验逻辑从复杂的业务流程中解耦出来、同时又需要把复杂的校验规则简单化，使整个校验逻辑更具有可扩展性和可维护性
  ![09-icRnjQ](https://gitee.com/drunk_and_happy/oss/raw/master/mypic/statebuilder4.png
  )
  
  4. 将数据在多个方法中进行传递有两种方案：一个是包装使用ThreadLocal、每个方法都可以对当前ThreadLocal进行赋值和取值；另一种是使用一个上下文Context对象做为每个方法的入参传递。
  用Map做为载体，业务在使用的时候可以根据需要随意的设置任何kv，但是这种情况对代码的可维护性和可读性是极大的挑战，所以这里使用泛型类来固定数据格式


### 2、初始化和运行
1. 系统初始化阶段，所有添加了@OrderProcessor注解的实现类都会被spring所管理成为spring bean，状态机引擎在通过监听spring bean的注册（BeanPostProcessor）来将这些状态处理器processor装载到自己管理的容器中
2. 直白来说、这个状态处理器容器其实就是一个多层map实现的，第一层map的key是状态（state），第二层map的key是状态对应的事件（event）、一个状态可以有多个要处理的事件，第三层map的key是具体的场景code（也就是bizCode和sceneId的组合），最后的value是AbstractStateProcessor集合。
3. 并且实现根据场景ID和业务ID找到具体Processors方法








### 3 状态模式总结

![09-icRnjQ](https://gitee.com/drunk_and_happy/oss/raw/master/mypic/statebuilder1.png
)


![09-icRnjQ](https://gitee.com/drunk_and_happy/oss/raw/master/mypic/statebuilder2.png
)
