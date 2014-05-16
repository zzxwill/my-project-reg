package javacommon.ejbca;

import java.security.PublicKey;
import java.util.List;


public class EjbcaWSTest {
	public static void main(String[] args){
		//取得证书
		EJBCAClientImpl ejbcaClientImpl=new EJBCAClientImpl();
		List<java.security.cert.Certificate> certs=ejbcaClientImpl.findCerts("Server", true);
		if(certs.size()>0){
			java.security.cert.Certificate cert=certs.get(0);
			PublicKey publicKey=cert.getPublicKey();
			System.out.println(publicKey);
		}else{
			System.out.println("数据库中不存在此用户！");
		}

	}
}
