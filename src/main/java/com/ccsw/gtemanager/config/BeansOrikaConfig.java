package com.ccsw.gtemanager.config;


import com.ccsw.gtemanager.config.mapper.BeanMapper;
import com.ccsw.gtemanager.config.mapper.BeanMapperImpl;
import com.devonfw.module.beanmapping.common.base.BaseOrikaConfig;
import com.devonfw.module.json.common.base.ObjectMapperFactory;
import com.fasterxml.jackson.databind.Module;
import ma.glasnost.orika.MapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* Java bean configuration for Orika. The method {@link #configureCustomMapping(MapperFactory)} from
* {@link BaseOrikaConfig} can be overridden as per requirements.
*/
@Configuration
public class BeansOrikaConfig extends BaseOrikaConfig {

  /**
  * @return the {@link BeanMapper} implementation.
  */
  @Override
  @Bean
  public BeanMapper getBeanMapper() {

    return new BeanMapperImpl();
  }

  @Bean
  public Module configureObjectMapper() {

    ObjectMapperFactory objectMapper = new ObjectMapperFactory();
    return objectMapper.getExtensionModule();

  }

}