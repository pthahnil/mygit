package com.autho.main.basedao;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

/**
 * 封装Dao
 * @author lxr
 */
public interface BaseDao<T> {
	
	public Session getSession();

    public T getById(int id);

    // 根据主键获取实体并加锁。如果没有相应的实体，返回 null。
    public T getWithLock(int id, LockMode lock);
    
    public List<T> getAll();

    // 根据主键获取实体。如果没有相应的实体，抛出异常。
    public T load(int id);

    // 根据主键获取实体并加锁。如果没有相应的实体，抛出异常。
    public T loadWithLock(int id, LockMode lock);

    // 获取全部实体。
    public List<T> loadAll();

    // 更新实体
    public void update(T entity);

    // 更新实体并加锁
    public void updateWithLock(T entity, LockMode lock);

    // 存储实体到数据库
    public int save(T entity);

    // 增加或更新实体
    public void saveOrUpdate(T entity);

    // 增加或更新集合中的全部实体
    public void saveOrUpdateAll(Collection<T> entities);

    // 删除指定的实体
    public void delete(T entity);

    // 加锁并删除指定的实体
    public void deleteWithLock(T entity, LockMode lock);

    // 根据主键删除指定实体
    public void deleteById(int id);

    // 根据主键加锁并删除指定的实体
    public void deleteByKeyWithLock(int id, LockMode lock);

    // 删除集合中的全部实体
    public void deleteAll(Collection<T> entities);

    // -------------------- HQL ----------------------------------------------

    // 使用HSQL语句直接增加、更新、删除实体
    public int bulkUpdate(String hql);

    // 使用带参数的HSQL语句增加、更新、删除实体
    public int bulkUpdate(String hql, Object[] values);

    // 使用HSQL语句检索数据
    public List find(String hql);

    // 使用带参数的HSQL语句检索数据
    public List find(String hql, Object[] values);

    // 使用带命名的参数的HSQL语句检索数据
    public List findByNamedParam(String hql, String[] paramNames,
            Object[] values);

    // 使用命名的HSQL语句检索数据
    public List findByNamedQuery(String queryName);

    // 使用带参数的命名HSQL语句检索数据
    public List findByNamedQuery(String queryName, Object[] values);

    // 使用带命名参数的命名HSQL语句检索数据
    public List findByNamedQueryAndNamedParam(String queryName,
            String[] paramNames, Object[] values);

    // 使用HSQL语句检索数据，返回 Iterator
    public Iterator iterate(String hql);

    // 使用带参数HSQL语句检索数据，返回 Iterator
    public Iterator iterate(String hql, Object[] values);

    // 关闭检索返回的 Iterator
    public void closeIterator(Iterator it);

    // -------------------------------- Criteria ------------------------------

    // 创建与会话无关的检索标准对象
    public DetachedCriteria createDetachedCriteria();

    // 创建与会话绑定的检索标准对象
    public Criteria createCriteria();

    // 使用指定的检索标准检索数据
    public List findByCriteria(DetachedCriteria criteria);

    // 使用指定的检索标准检索数据，返回部分记录
    public List findByCriteria(DetachedCriteria criteria, int firstResult,
            int maxResults);

    // 使用指定的实体及属性检索（满足除主键外属性＝实体值）数据
    public List<T> findEqualByEntity(T entity, String[] propertyNames);

    // 使用指定的实体及属性(非主键)检索（满足属性 like 串实体值）数据
    public List<T> findLikeByEntity(T entity, String[] propertyNames);

    // 使用指定的检索标准检索数据，返回指定范围的记录
    public Integer getRowCount(DetachedCriteria criteria);

    // 使用指定的检索标准检索数据，返回指定统计值
    public Object getStatValue(DetachedCriteria criteria, String propertyName,
            String StatName);

    // -------------------------------- Others --------------------------------

    // 加锁指定的实体
    public void lock(T entity, LockMode lockMode);

    // 强制初始化指定的实体
    public void initialize(Object proxy);

    // 强制立即更新缓冲数据到数据库（否则仅在事务提交时才更新）
    public void flush();

}