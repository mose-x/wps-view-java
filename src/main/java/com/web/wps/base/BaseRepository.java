/*
 * www.takead.cn
 * Copyright (c) 2012-2013 All Rights Reserved.
 */
package com.web.wps.base;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 *
 * @author zm
 * @see abstract
 */
@NoRepositoryBean
public interface BaseRepository<M extends BaseEntity, ID extends Serializable>
		extends PagingAndSortingRepository<M, ID>,JpaSpecificationExecutor<M> {


}
