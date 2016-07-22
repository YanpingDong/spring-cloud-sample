package com.dyp.test.sample_proxy;

import org.springframework.context.annotation.Configuration;

import com.netflix.zuul.ZuulFilter;

@Configuration
public class TestZuulFilter2 extends ZuulFilter{

	@Override
	public boolean shouldFilter() {
		System.out.println("in shouldFilter function--> filter2");
		return true; //decide whether to invoke run function
	}

	@Override
	public Object run() {
		System.out.println("in run function --> filter2");
		return null;
	}

	@Override
	public String filterType() {
		System.out.println("in filter type function --> filter2");
		return "route";
	}

	@Override
	public int filterOrder() {
		System.out.println("in filter order function --> filter2");
		return 1;//in multiple filter decide runnig order
	}

}
