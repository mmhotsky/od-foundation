package cn.com.officedepot.foundation.spring.mybatis;

import java.io.Serializable;

import org.springframework.data.domain.Sort;

import cn.com.officedepot.foundation.spring.mybatis.hotfix.page.Pageable;

public interface BasePageableDao<T, PK extends Serializable> extends BaseDao<T, PK> {

	public Pageable<PK> findPk(T obj, Sort sort);

	public Pageable<T> find(T obj, Sort sort);

}
