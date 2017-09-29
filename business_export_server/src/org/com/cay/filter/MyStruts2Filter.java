package org.com.cay.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter;

/**
 * Servlet Filter implementation class MyStruts2Filter
 */
@WebFilter(urlPatterns = "/*")
public class MyStruts2Filter extends StrutsPrepareAndExecuteFilter implements Filter {

	/**
	 * @see StrutsPrepareAndExecuteFilter#StrutsPrepareAndExecuteFilter()
	 */
	public MyStruts2Filter() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		HttpServletRequest req = (HttpServletRequest) request;
		if (req.getRequestURI().contains("/ws/")) {
			chain.doFilter(request, response);// 放行
		} else {
			super.doFilter(request, response, chain);// 执行父类的doFilter
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
