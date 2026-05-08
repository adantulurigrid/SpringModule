# Spring Boot Testing

---

### 1. How can we configure the application context when running tests with @SpringBootTest annotation ?

When using `@SpringBootTest`, you can refine or augment the application context in several ways:

* **Inline Properties:** Use the `properties` attribute to override specific settings.
    ```java
    @SpringBootTest(properties = "server.port=9091")
    ```
* **Test Property Sources:** Load a specific file for the test.
    ```java
    @TestPropertySource(locations = "classpath:test-config.properties")
    ```
* **Inner @TestConfiguration Classes:** Use a static nested class annotated with `@TestConfiguration` to define additional beans or override existing ones specifically for that test class.
* **ApplicationContextInitializer:** For programmatic control, you can use the `initializers` attribute.

---

### 2. How can you exclude auto configuration from a test ?

If you want to prevent certain autoconfigurations from running during a test (to save time or avoid external dependencies), you have two main options:

1.  **Via Annotation Attribute:**
    ```java
    @SpringBootTest(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration")
    ```
2.  **Using @ImportAutoConfiguration:** If using sliced tests (like `@WebMvcTest`), you can use `@ImportAutoConfiguration(exclude = ...)` to manually manage which autoconfigs are active.

---

### 3. How many application contexts can be cached when running tests ? What are the side effects if we increase the cache size ? What would be the side effects if there was no caching ?

Spring’s **Context Caching** mechanism ensures that if multiple test classes share the same configuration, the `ApplicationContext` is reused rather than recreated.

* **Default Cache Size:** By default, the cache size is **32**.
* **Increasing the Cache Size:**
    * *Side Effect:* You can set `spring.test.context.cache.maxSize` higher. The main drawback is **high memory consumption**. Storing many contexts (especially with heavy beans like Hibernate) can lead to `OutOfMemoryError`.
* **Side Effects of NO Caching:**
    * *Performance Hit:* Total test execution time would skyrocket. Re-initializing the DB, scanning components, and wiring beans for every single test class is extremely expensive.
    * *Resource Exhaustion:* Constant opening and closing of resource pools (like HikariCP) might lead to socket or connection leaks in the OS environment.

---

### 4. Can @MockBean be used if the bean is not already defined in the application context ?

**Yes, `@MockBean` can be used even if the bean is not already defined in the context.**

* **Behavior:** When Spring encounters `@MockBean` for a type that doesn't exist in the context, it simply creates a mock of that type and adds it as a new bean to the `ApplicationContext`.
* **Use Case:** This is extremely useful for "mocking out" external services or infrastructure components that your code depends on but aren't actually configured in your test profile.
* **Note:** If a bean of that type *does* exist, `@MockBean` will replace the original bean with the mock for the duration of that test.