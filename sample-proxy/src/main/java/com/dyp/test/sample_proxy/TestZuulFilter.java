package com.dyp.test.sample_proxy;

import org.springframework.context.annotation.Configuration;

import com.netflix.zuul.ZuulFilter;

@Configuration
public class TestZuulFilter extends ZuulFilter{

	@Override
	public boolean shouldFilter() {
		System.out.println("in shouldFilter function");
		return true; //decide whether to invoke run function
	}

	@Override
	public Object run() {
		System.out.println("in run function");
		return null;
	}

	@Override
	public String filterType() {
		System.out.println("in filter type function");
		return "route";
	}

	@Override
	public int filterOrder() {
		System.out.println("in filter order function");
		return 2;  //in multiple filter decide runnig order
	}

}
