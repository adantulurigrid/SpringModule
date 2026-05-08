# Inversion of control and Dependency Injection

---

### 1. What is the ApplicationContext ?

The **ApplicationContext** is the central interface within a Spring application that provides configuration information. It is a more advanced version of the `BeanFactory`.

* **Bean Management:** It instantiates, configures, and manages the lifecycle of beans.
* **Resource Access:** It provides a generic way to load file resources (like XML or properties).
* **Event Publication:** It supports event propagation to interested listeners.
* **Internationalization:** It provides resolve messages, supporting internationalization (i18n).
* **Automatic Registration:** It automatically detects and registers `BeanFactoryPostProcessors` and `BeanPostProcessors`.

---

### 2. What are the tradeoffs of different approaches to injecting beans ?

Spring offers three main ways to inject dependencies, each with its own "pro/con" profile:

| Approach | Pros | Cons |
| :--- | :--- | :--- |
| **Constructor Injection** | Encourages immutability (`final` fields); ensures required dependencies aren't null; easiest for unit testing. | Can result in long constructor signatures if there are many dependencies. |
| **Setter Injection** | Good for optional dependencies; allows reconfiguration or re-injection later. | Objects can be in an "incomplete" state (null dependencies) during runtime. |
| **Field Injection** | Very concise code; no boilerplate. | Impossible to use with `final` fields; hidden dependencies; makes unit testing harder (requires reflection/Mockito). |

---

### 3. Why do we need to use @Qualifier when multiple of the same type are defined ?

Spring typically injects dependencies **by type**. If you have one interface (`PaymentService`) and two implementations (`CreditCardService` and `PaypalService`), Spring will throw a `NoUniqueBeanDefinitionException` because it doesn't know which one to pick.

**`@Qualifier`** solves this by:
* Providing a specific "name" or "alias" to the bean.
* Allowing the developer to specify exactly which bean instance should be wired into the injection point.

> **Alternative:** You can also use `@Primary` on one of the implementations to set a "default" choice.

---

### 4. How to avoid loading of heavy beans (like caches or other beans with heavy init logic) on startup and decrease startup time?

To decrease startup time and prevent "heavy" beans (like large caches or external connection pools) from initializing immediately, use **Lazy Initialization**.

* **At the Bean Level:** Mark a specific bean with `@Lazy`. It will only be created when it is first requested by another bean or the application code.
    ```java
    @Bean
    @Lazy
    public HeavyCache heavyCache() { ... }
    ```
* **At the Global Level:** You can set `spring.main.lazy-initialization=true` in `application.properties`. This makes **all** beans lazy by default.
* **Tradeoff:** While this speeds up startup, it may shift "startup errors" or latency spikes to the first user request.

---

### 5. What are Spring lifecycle stages and methods?

The Spring bean lifecycle is a sequence of steps from instantiation to destruction.

1.  **Instantiation:** Spring creates the bean instance (via constructor).
2.  **Populate Properties:** Dependencies are injected (via setters or fields).
3.  **Aware Interfaces:** If the bean implements `BeanNameAware` or `ApplicationContextAware`, Spring passes the relevant IDs/context.
4.  **BeanPostProcessor (Pre-init):** `postProcessBeforeInitialization` is called.
5.  **Initialization:**
    * `@PostConstruct` method.
    * `afterPropertiesSet()` (from `InitializingBean` interface).
    * Custom `init-method`.
6.  **BeanPostProcessor (Post-init):** `postProcessAfterInitialization` is called. The bean is now ready for use.
7.  **Destruction:** (Triggered when the context closes)
    * `@PreDestroy` method.
    * `destroy()` (from `DisposableBean` interface).
    * Custom `destroy-method`.

$$ \text{Lifecycle} = \text{Creation} \rightarrow \text{Injection} \rightarrow \text{Awareness} \rightarrow \text{Initialization} \rightarrow \text{Usage} \rightarrow \text{Destruction} $$