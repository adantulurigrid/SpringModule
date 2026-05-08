# Application Context configuration

---

### 1. What is the difference between the @Configuration,@Component and @Service annotations ?

While all three are candidate components for Spring's ApplicationContext, they serve different architectural roles:

| Annotation | Primary Purpose | Key Characteristics |
| :--- | :--- | :--- |
| **@Component** | Generic Stereotype | The most basic unit of a Spring-managed bean. Used for any POJO you want Spring to manage. |
| **@Service** | Business Logic | A specialization of `@Component`. It carries no additional behavior over `@Component` but serves as a "semantic hint" for architectural layering. |
| **@Configuration** | Bean Factory | Used for classes that define `@Bean` methods. It is CGLIB-proxied, meaning calling a `@Bean` method from another `@Bean` method in the same class returns the *existing* bean instance (Singleton) rather than a new one. |

---

### 2. How can we customize the component scanning process ?

By default, `@SpringBootApplication` scans the package it is in and all sub-packages. You can customize this via:

* **Base Packages:** Specify exact packages to scan.
  ```java
  @ComponentScan(basePackages = {"com.myapp.service", "com.myapp.repository"})
  
---

### 3. What value will a property have if it is defined in two different profiles both of which are active ?

If a property (e.g., `app.timeout`) is defined in both `application-dev.properties` and `application-prod.properties`, and both profiles are active:

The value from the profile declared LAST in the activation list wins.

Example:

YAML
  ```java
spring.profiles.active: dev, prod
```

In this case, the values in `prod` will override the values in `dev`. Spring processes active profiles in the order they are listed.

---

### 4. Why would we use factory beans instead of regular beans ?

Standard beans are instantiated by Spring using their constructor. You use a FactoryBean<T> when the instantiation logic is too complex for a simple constructor.

* **Complex Initialization:** If a bean requires a heavy setup, many dependencies, or a specific factory pattern (like a Proxy or a ConnectionPool).

* **Integration Logic:** Many third-party libraries use FactoryBean to wrap non-Spring objects into the Spring Lifecycle.

* **Accessing the "Factory":** If you want the object created by the factory, you get beanName. If you want the factory instance itself, you use &beanName.
---

### 5. How can we override any property defined in any .properties file ?

Spring follows a strict Property Source Hierarchy. You can override any file-defined property using a source higher in the list. The most common ways are:

1. **Command Line Arguments:** (Highest priority)
`java -jar app.jar --server.port=9090`

2. **OS Environment Variables:**
`export SERVER_PORT=9090` (Spring translates `SERVER_PORT` to `server.port`).

3. **Config-specific Files:** `application-{profile}.properties` overrides `application.properties`.
---

### 6. Does PostDestruct get called for a prototype scope bean ?

No, `@PreDestroy` is NOT called for Prototype scope beans.

Spring is responsible for instantiating, configuring, and assembling a Prototype bean and handing it to the client. After that, the Spring container loses track of that specific instance.

* **The client code** is responsible for cleaning up Prototype beans and releasing any resources they hold.

* `@PostConstruct` is called (initialization), but the destruction lifecycle is ignored by the container.