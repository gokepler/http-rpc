# http-rpc

![koala](koala.png)

It is a library for you to build multiple RPC service **EASY** and **QUICKLY** in Java. It's based on http.

It contains three modules,`http-rpc-core`、`http-rpc-server` and `http-rpc-client`；

## Quick Start

### Step 1 [server]

Add denpendency in your maven `pom.xml` file for server project.

```
<dependency>
    <groupId>io.http.rpc</groupId>
    <artifactId>http-rpc-core</artifactId>
    <version>0.9.0</version>
</dependency>
<dependency>
    <groupId>io.http.rpc</groupId>
    <artifactId>http-rpc-server</artifactId>
    <version>0.9.0</version>
</dependency>
```

### Step 2 [server]

Add Service Java

```
package com.service;

import io.http.rpc.annotation.ServiceMapping;
import io.http.rpc.annotation.ServiceNamespace;

@ServiceNamespace("equipmentService")
public class EquipmentService {

    @ServiceMapping
    public Equipment getOne(Integer id) {
        return new Equipment(10, "台式电脑!");
    }
}
```

The `@ServiceNamespace` and `@ServiceMapping` descript the service `namespace` and `name`; 

`@ServiceNamespace` is required for the service class, this annotation value is not required, the default value is the full name of the service java class, such as `com.service.EquipmentService`;

`@ServiceMapping` is not required for the service method for service, this annotation value is not required, the default value is the method name;

### Step 3 [server]

Edit your spring `applicationContext.xml`, add the follow 

```
<bean id="happyService" name="/scream" class="io.http.rpc.server.HttpServiceExporter">
</bean>
<bean class="io.http.rpc.server.auto.ServiceScannerConfigurer">
    <property name="basePackage" value="com.service" />
    <property name="serviceExporter" ref="happyService" />
</bean>
```

`io.http.rpc.server.HttpServiceExporter` is the core service exporter, the bean name is the entrance;

`io.http.rpc.server.auto.ServiceScannerConfigurer` will scan all the 
`@ServiceNamespace` annotated Java Class, first you need to provide `basePackage`;

Now, you can startup your web server.

### Step 4 [client]

Add denpendency in your maven `pom.xml` file for client project.

```
<dependency>
    <groupId>io.http.rpc</groupId>
    <artifactId>http-rpc-core</artifactId>
    <version>0.9.0</version>
</dependency>
<dependency>
    <groupId>io.http.rpc</groupId>
    <artifactId>http-rpc-client</artifactId>
    <version>0.9.0</version>
</dependency>
```

### Step 5 [client]

Add Java service interface.

```
package com.client;

import io.http.rpc.annotation.ServiceMapping;
import io.http.rpc.annotation.ServiceNamespace;

@ServiceNamespace("equipmentService")
public interface EquipmentService {

    Equipment getOne(Integer id);
}
```

Edit your spring `applicationContext.xml`, add the follow

```
<bean id="serviceProxyFactory" class="io.http.rpc.client.ServiceProxyFactory">
    <property name="url" value="http://localhost:9091/{contextPath}/scream" />
</bean>
<bean class="io.http.rpc.client.auto.ServiceScannerConfigurer">
    <property name="basePackage" value="com.client" />
    <property name="serviceProxyFactory" ref="serviceProxyFactory" />
</bean>
```

`io.http.rpc.client.ServiceProxyFactory` is the service proxy factory, it can help you build service proxy with the `url`.

`io.http.rpc.client.auto.ServiceScannerConfigurer` will scan all the `@ServiceNamespace` annotated Java interface, and the is will make proxy bean, the bean will injected to spring context, first you need to provide `basePackage`;


### Step 6 [client]

Add the auto proxy interface to your controller.

```
@Controller
public class HelloController {

    @Autowired
    private EquipmentService service;

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    @ResponseBody
    public Equipment hello(@RequestParam(value = "name") String name) {

        return service.getOne();
    }
}

```

DONE!
