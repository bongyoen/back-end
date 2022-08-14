package com.example.ec2spring;

import com.example.ec2spring.upload.FileStorageProperties;
import com.twelvemonkeys.servlet.image.IIOProviderContextListener;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
//import kr.uicom.uics.payapp.PayappProperties;
//import kr.uicom.uics.pbTaxInvoice.PbTaxInvoiceProperties;
//import kr.uicom.uics.upload.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaAuditing
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class,
})

public class Ec2springApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Ec2springApplication.class, args);

        String path = System.getProperty("user.dir");
        System.out.println("현재 작업 경로: " + path);

        System.out.println("hello wordl!!");
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.out.println("hello wordl!!22");

        super.onStartup(servletContext);
        // Register the listener from Twelvemonkeys to support CMYK image handling with ImageIO
        servletContext.addListener(IIOProviderContextListener.class);
    }



}
