package com.example.ec2spring;

import com.example.ec2spring.upload.FileStorageProperties;
import com.twelvemonkeys.servlet.image.IIOProviderContextListener;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
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
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        super.onStartup(servletContext);
        servletContext.addListener(IIOProviderContextListener.class);
    }



}
