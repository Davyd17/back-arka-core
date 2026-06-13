package com.arka.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Spring configuration class that enables an additional network connector in Tomcat.
 * <p>
 * This design is typically employed to separate external user-facing traffic from internal
 * administrative endpoints (e.g., Spring Boot Actuator or internal services communication).
 * </p>
 */
@Configuration
public class InternalServerConfig {

    @Value("${internal.server.port}")
    private int internalPort;


    /**
     * Customizes the embedded Tomcat servlet container factory.
     * <p>
     * This bean overrides the default container setup to inject the secondary
     * network connector alongside the primary application connector.
     * </p>
     *
     * @return a modified {@link TomcatServletWebServerFactory} containing the internal connector.
     */
    @Bean
    public TomcatServletWebServerFactory servletContainer(){

        TomcatServletWebServerFactory tomcat =
                new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(internalConnector());
        return tomcat;
    }


    /**
     * Instantiates and configures a secondary Tomcat HTTP connector.
     * <p>
     * The connector is initialized using the standard {@code Http11NioProtocol}
     * (Non-blocking I/O) and is bound to the port specified by {@code internalPort}.
     * </p>
     *
     * @return a configured Tomcat {@link Connector} ready to accept traffic.
     */
    @Bean
    public Connector internalConnector(){
        Connector connector =
                new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(internalPort);
        return connector;
    }
}
