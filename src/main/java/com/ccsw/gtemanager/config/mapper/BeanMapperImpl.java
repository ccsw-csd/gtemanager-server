package com.ccsw.gtemanager.config.mapper;

import com.devonfw.module.beanmapping.common.impl.orika.BeanMapperImplOrika;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

/**
* @author coedevon
*/
public class BeanMapperImpl extends BeanMapperImplOrika implements BeanMapper {

  /**
  * {@inheritDoc}
  */
  public <T> Page<T> mapPage(Page source, Class<T> targetClass) {

    if (source == null) {
      return null;
    }

    List list = mapList(source.getContent(), targetClass);

    return new PageImpl<>(list, source.getPageable(), source.getTotalElements());
  }

}