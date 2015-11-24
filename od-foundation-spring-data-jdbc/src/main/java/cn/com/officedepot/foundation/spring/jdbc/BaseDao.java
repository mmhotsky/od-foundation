package cn.com.officedepot.foundation.spring.jdbc;

import java.io.Serializable;

public interface BaseDao<T, PK extends Serializable> {

	public T saveOrUpdate(T obj);

	public Iterable<T> saveOrUpdate(Iterable<T> objs);

	public long count(T obj);

	public boolean exists(PK pk);

	public boolean exists(T obj);

	public Iterable<PK> findPk(T obj);

	public PK findOnePk(T obj);

	public T findOne(PK pk);

	public T findOne(T obj);

	public Iterable<T> find(Iterable<PK> pks);

	public Iterable<T> find(T obj);

	public void deleteOne(PK pk);

	public void delete(T obj);

	public void delete(Iterable<T> objs);

}
