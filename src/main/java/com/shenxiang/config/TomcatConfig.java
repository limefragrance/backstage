package com.shenxiang.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {

    //错误 More than the maximum allowed number of headers, [100] +ymal一起使用
    @Bean
    public ConfigurableServletWebServerFactory configurableServletWebServerFactory(){
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.setProtocol("org.apache.coyote.http11.Http11NioProtocol");
        factory.setPort(8850);
        factory.addConnectorCustomizers( connector -> {
            Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
            protocol.setDisableUploadTimeout(false);
            //protocol.setAcceptCount(200);
            //protocol.setMaxConnections(200);
            protocol.setMaxHeaderCount(20000);
            protocol.setConnectionTimeout(20000);
            protocol.setMaxHttpHeaderSize(209715200);
            protocol.setMaxSavePostSize(4194304);
        } );
        return factory;
    }

//    @Bean
//    public TomcatServletWebServerFactory webServerFactory() {
//        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
//        factory.addConnectorCustomizers((Connector connector) -> {
//            connector.setProperty("relaxedPathChars", "\"<>[\\]^`{|}");
//            connector.setProperty("relaxedQueryChars", "\"<>[\\]^`{|}");
//        });
//        return factory;
//    }
}
