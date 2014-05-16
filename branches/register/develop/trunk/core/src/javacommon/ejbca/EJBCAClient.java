package javacommon.ejbca;

import java.security.cert.Certificate;
import java.util.List;

import org.ejbca.core.protocol.ws.client.gen.EjbcaWS;

public interface EJBCAClient {
    //根据userName,得到证书
	public List<Certificate> findCerts(String userName, boolean onlyValid); 
	//得到EjbcaWS
	public EjbcaWS getEjbcaWS() throws Exception;
}
