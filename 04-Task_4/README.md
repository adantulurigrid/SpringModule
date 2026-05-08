# AutoConfiguration

---

### 1. What is the difference between regular configuration and autoconfiguration ?

| Feature | Regular Configuration (`@Configuration`) | Autoconfiguration (`@EnableAutoConfiguration`) |
| :--- | :--- | :--- |
| **Source** | Defined by the developer within the application source code. | Provided by Spring Boot starters or external libraries via `META-INF` files. |
| **Control** | Full manual control over bean instantiation and logic. | Opinionated "sensible defaults" based on classpath dependencies. |
| **Ordering** | Processed during the initial component scanning phase. | Processed in a secondary phase **after** user beans are registered. |
| **Purpose** | To define the specific business beans and wiring for your app. | To automatically "guess" and configure infrastructure (e.g., DBs, Security). |

---

### 2. Would all conditional annotations on bean definitions work in regular configuration classes ? Elaborate.

Yes. All `@Conditional...` annotations (e.g., `@ConditionalOnProperty`, `@ConditionalOnClass`) are fully functional within regular `@Configuration` classes.

### The Elaboration
While they work, the **timing** is different:
* **Regular Configuration:** These classes are processed by `ConfigurationClassPostProcessor`. If you use `@ConditionalOnBean` here, it will only "see" beans defined in the same configuration or those processed in earlier scan cycles.
* **Autoconfiguration:** These classes run in a specialized stage. This is why `@ConditionalOnMissingBean` is so effective in autoconfiguration, it is guaranteed to run after the developer’s regular `@Configuration` classes have had their chance to define beans.

---

### 3. How can we customize the auto configuration process ?

There are three main ways to modify or override the default behavior:

1.  **Properties Overriding:** Modify `application.properties` or `application.yml`. Most autoconfigurations use `@ConditionalOnProperty` or `@EnableConfigurationProperties`.
    ```yaml
    server.port: 9090
    spring.h2.console.enabled: true
    ```
2.  **Bean Overriding:** Define a bean of the same type in your own `@Configuration` class. Because autoconfigurations use `@ConditionalOnMissingBean`, your bean will take precedence and the auto-configured one will be skipped.
3.  **Explicit Exclusion:** Disable specific autoconfigurations entirely via the `@SpringBootApplication` annotation:
    ```java
    @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
    ```

---

### 4. What is the condition that causes a tomcat server to start on port 8080 when the application starts ?

The automatic startup of Tomcat on port 8080 is governed by **`ServletWebServerFactoryAutoConfiguration`**.

* **The Condition:** The server starts because `Tomcat.class` and `Servlet.class` are detected on the classpath (usually via `spring-boot-starter-web`).
* **The Port Logic:** The configuration looks for the `server.port` property.
* **The Default Value:** If no property is provided in your config files, Spring Boot defaults to the value defined in the `ServerProperties` class, where the port is hardcoded to **8080**.

$$ \text{Target Port} = \text{Environment Property} \ (\texttt{server.port}) \ \lor \ 8080 $$

---