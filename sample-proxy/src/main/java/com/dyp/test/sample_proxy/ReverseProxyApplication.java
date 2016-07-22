package com.dyp.test.sample_proxy;

import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/*
 * Zuul proxy will find filter auto, all extends ZuulFilter interface will 
 * be injected in  proxy as filter
 */
@SpringBootApplication
@EnableZuulProxy
public class ReverseProxyApplication {	
	// can comment  servletContainer()
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");

				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/**");
				securityConstraint.addCollection(collection);

				context.addConstraint(securityConstraint);
			}
		};

		return tomcat;
	}
	
	/**
	 * please refer zuul part in application.yml that will specified the routed rule
	 */
	public static void main(String[] args) {
		System.out.println("Reverse Proxy ...");
		SpringApplication.run(ReverseProxyApplication.class, args);
	}
}
