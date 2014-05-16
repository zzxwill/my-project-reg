package javacommon.ejbca;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ejbca.core.protocol.ws.client.gen.EjbcaWS;
import org.ejbca.core.protocol.ws.client.gen.EjbcaWSService;
import org.ejbca.core.protocol.ws.common.CertificateHelper;

public class EJBCAClientImpl implements EJBCAClient {
	
	private final static Log logger = LogFactory.getLog(EJBCAClientImpl.class);
	private static EjbcaWS ejbcaraws = null;
	
	
	public EjbcaWS getEjbcaWS() throws Exception{
		String urlstr="https://ca.yeeach.com:8443/ejbca/ejbcaws/ejbcaws?wsdl";
		System.setProperty("javax.net.ssl.trustStore","E:\\PKI\\学习阶段成果收集\\java代码实验\\EjbcaWS\\keystore\\keystore.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "shijianyi");
		System.setProperty("javax.net.ssl.keyStore","E:\\PKI\\学习阶段成果收集\\java代码实验\\EjbcaWS\\keystore\\superadmin.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "shijianyi");
		QName qname = new QName("http://ws.protocol.core.ejbca.org/","EjbcaWSService");
		EjbcaWSService service = new EjbcaWSService(new URL(urlstr),qname);
		service.getEjbcaWSPort();		
		ejbcaraws = service.getEjbcaWSPort();
		return ejbcaraws;
	}
	
	public List<java.security.cert.Certificate> findCerts(String userName, boolean onlyValid) {
		List<java.security.cert.Certificate> result = new ArrayList<java.security.cert.Certificate>();
		try{
			ejbcaraws=getEjbcaWS();
			List<org.ejbca.core.protocol.ws.client.gen.Certificate> certs= ejbcaraws.findCerts(userName, onlyValid);
			if(result==null || result.size() == 0){ 
				if(certs==null || certs.size() == 0){ 
					return result;      
				}
			}
			for (org.ejbca.core.protocol.ws.client.gen.Certificate cert : certs) 
            { 
				result.add(CertificateHelper.getCertificate(cert.getCertificateData())); 
            }    
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

}
