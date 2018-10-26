This project reproduces an issue detected when camel is being run in a spring cloud environment. 
 
###  Bug Description
In Spring cloud environment some components might create a child application context that spring cloud mantains. Such is the case for Feign and Ribbon Clients. 

See 7.2 - https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html
"A central concept in Spring Cloud’s Feign support is that of the named client. Each feign client is part of an ensemble of components that work together to contact a remote server on demand, and the ensemble has a name that you give it as an application developer using the @FeignClient annotation. Spring Cloud creates a new ensemble as an ApplicationContext on demand for each named client using FeignClientsConfiguration."


This child application contexts are going to be ApplicationEvent sources for the camel context who is defined as an ApplicationEvent listener a parent context.
SpringCamelContext is intended to be started on a ContextRefreshedEvent.
RoutesCollector is going to add routes on a ContextRefreshedEvent.

So, when there are child application contexts we end having some inconsistencies in the routes being added and in the camel context lifecycle. 


### Reproducing the bug

Let s define a spring cloud project such as we have 3 camel routes and 2 feign client services.

Route 1 is going to depend on StoreClient feign service. 

Route 2 is going to depend on StoreUpdater feign service. 

Route 3 does not depend on any feign client. 

Routes are defined as SpringRouteBuilder 's classes and annotated with @Component stereotype so the application context is going to try to instantiate them. 

Instantiating a bean means resolving it is dependencies and instantiating them. So for Route1 and Route2, the feign clients are going to be instantiated. 

For every feign client, FeignClientFactoryBean is going to create a child application context that ends publishing a ContextRefreshedEvent. 

With the current implementation all of them are going to be triggers to add routes to the camel context (RoutesCollector) and start the camel context(SpringCamelContext).

### Expected result
Camel context should be created and 3 routes should be added to the camel context and started

### Current result
2 routes being added, and routes are being added and removed(shutdown) and added from/to the camel context on startup.

### Solution
The trigger that signals the instant to add the routes and start the camel context should be a contextrefreshedEvent emitted by the application context that was the factory of the camel context instance. 
So the application context `id`, that is the source of the ContextRefreshedEvent, must be considered as well, to choose the proper ContextRefreshedEvents that the camel context must handle.    
