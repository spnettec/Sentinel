# Sentinel Reactor Adapter

Sentinel provides integration module for [Reactor](https://projectreactor.io/).

Add the following dependency in `pom.xml` (if you are using Maven):

```xml
<dependency>
   <groupId>com.github.heyoulin.Sentinel</groupId>
    <artifactId>sentinel-reactor-adapter</artifactId>
    <version>x.y.z</version>
</dependency>
```

Example:

```java
someService.doSomething() // return type: Mono<T> or Flux<T>
   .transform(new SentinelReactorTransformer<>(resourceName)) // transform here
   .subscribe();
```
