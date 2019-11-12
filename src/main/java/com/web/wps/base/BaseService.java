package com.web.wps.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Optional;

/**
 *
 * @author zm
 * @see abstract
 */
public abstract class BaseService<M extends BaseEntity, ID extends Serializable> {

	protected BaseRepository<M, ID> baseRepository;

	protected abstract void setDao(BaseRepository baseRepository);

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 保存单个实体
	 *
	 * @param m
	 *            实体
	 * @return 返回保存的实体
	 */
	public M save(M m) {
		return baseRepository.save(m);
	}

	/**
	 * 更新单个实体
	 *
	 * @param m
	 *            实体
	 * @return 返回更新的实体
	 */
	public M update(M m) {
		return baseRepository.save(m);
	}

	/**
	 * 根据主键删除相应实体
	 *
	 * @param id
	 *            主键
	 */
	public void delete(ID id) {
		baseRepository.deleteById(id);
	}

	/**
	 * 删除实体
	 *
	 * @param m
	 *            实体
	 */
	public void delete(M m) {
		baseRepository.delete(m);
	}

	/**
	 * 按照主键查询
	 *
	 * @param id
	 *            主键
	 * @return 返回id对应的实体
	 */
	public M findOne(ID id) {
		Optional<M> om = baseRepository.findById(id);
		return om.orElse(null);
	}

	/**
	 * 实体是否存在
	 *
	 * @param id
	 *            主键
	 * @return 存在 返回true，否则false
	 */
	public boolean exists(ID id) {
		return baseRepository.existsById(id);
	}

	/**
	 * 统计实体总数
	 *
	 * @return 实体总数
	 */
	public long count() {
		return baseRepository.count();
	}

	/**
	 * 分页及排序查询实体
	 *
	 * @param pageable
	 *            分页及排序数据
	 * @return
	 */
	public Page<M> findAll(Pageable pageable) {
		return baseRepository.findAll(pageable);
	}

	/**
	 * 获取全部
	 *
	 *            获取全部
	 * @return
	 */
	public Iterable<M> findAll(){
		return baseRepository.findAll();
	}

}
