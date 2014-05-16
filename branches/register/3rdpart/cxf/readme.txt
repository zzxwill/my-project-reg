该目录下已经是可以嵌入到osgi环境下做部署使用的全部jar包.

MANIFEST.MF文件如下引入即可:

Bundle-Classpath: .,WEB-INF/classes,WEB-INF/lib/XmlSchema-1.4.2.jar,
 WEB-INF/lib/oro-2.0.8.jar,WEB-INF/lib/xml-resolver-1.2.jar,WEB-I
 NF/lib/saaj-impl-1.3.2.jar,WEB-INF/lib/jaxb-xjc-2.1.7.jar,WEB-INF/lib/commons-collections-3.1.jar,
 WEB-INF/lib/saaj-api-1.3.jar,WEB-INF/lib/geronimo-annotation_1.0_spec-1.1.1.jar,WEB-I
 NF/lib/commons-lang-2.4.jar,WEB-INF/lib/wstx-asl-3.2.6.jar,WEB-INF/lib/asm-2.2
 .3.jar,WEB-INF/lib/geronimo-javamail_1.4_spec-1.3.jar,WEB-INF/lib/vel
 ocity-1.5.jar,WEB-INF/lib/jaxb-api-2.1.jar,WEB-INF/lib/geronimo-jaxws_2.1_spec-1.0.jar,WEB-INF/lib/neeth
 i-2.0.4.jar,WEB-INF/lib/geronimo-stax-api_1.0_spec-1.0.1.jar,WEB-INF/lib/geronimo-activation_1.1_spec-1.0.2.jar,WEB-INF
 /lib/wsdl4j-1.6.2.jar,WEB-INF/lib/jaxb-impl-2.1.7.jar,WEB-INF/lib/ger
 onimo-ws-metadata_2.0_spec-1.1.2.jar,WEB-INF/lib/FastInfoset-1.2.2.jar,WEB-INF/lib/cxf-bundle-minimal-2.1.3.jar
Import-Package: javax.servlet,javax.servlet.http,javax.servlet.resources
Web-ContextPath: oetlclient
DynamicImport-Package: javax.*, org.*
Ignore-Package: javax.jws
Embed-Dependency: *;scope=compile
Bundle-Version: 2.0.0
Bundle-ManifestVersion: 2
Embed-Directory: WEB-INF/lib
