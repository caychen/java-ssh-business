package org.com.cay.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface IExportWebService {
	
	@WebMethod
	public String exportE(String jsonStr) throws Exception;
}
