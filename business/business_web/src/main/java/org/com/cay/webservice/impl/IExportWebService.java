
package org.com.cay.webservice.impl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.com.cay.webservice.ObjectFactory;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "IExportWebService", targetNamespace = "http://webservice.cay.com.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IExportWebService {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     * @throws Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "exportE", targetNamespace = "http://webservice.cay.com.org/", className = "org.com.cay.webservice.ExportE")
    @ResponseWrapper(localName = "exportEResponse", targetNamespace = "http://webservice.cay.com.org/", className = "org.com.cay.webservice.ExportEResponse")
    public String exportE(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0)
        throws Exception
    ;

}
