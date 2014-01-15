/*
 *  OpenSDI Manager
 *  Copyright (C) 2013 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.opensdi.destination.dao.impl;

import it.geosolutions.opensdi.destination.dao.GenericDAO;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Based on http://community.jboss.org/docs/DOC-13955
 * @param <T> entity type
 * @param <ID> primary key
 */
public abstract class GenericHibernateDAOImpl<T, ID extends Serializable> extends HibernateDaoSupport implements GenericDAO<T, ID> {

        protected Class<T> persistentClass;

    @SuppressWarnings({"unchecked"})
    public GenericHibernateDAOImpl() {
        try {
            persistentClass = (Class<T>)
                    ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (ClassCastException e) {
            //can be raised when DAO is inherited twice
            persistentClass = (Class<T>)
                    ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
    }

    public T findById(Serializable id) {
            T entity;
        entity = (T) getHibernateTemplate().get(persistentClass, id);

        return entity;
    }

    public List<T> findAll() {
            return findByCriteria();
    }

    @SuppressWarnings("unchecked")
        public List<T> findAllFromTo(Integer first, Integer last){
            Criteria criteria = getSession().createCriteria(persistentClass);
            criteria.setFirstResult(first);
            criteria.setMaxResults(last-first);
                return criteria.list();
    }

        @SuppressWarnings("unchecked")
        public List<T> findByExample(T exampleInstance, String[] excludeProperty) {
        DetachedCriteria crit = DetachedCriteria.forClass(persistentClass);
        Example example = Example.create(exampleInstance);
                for (String exclude : excludeProperty) {
                        example.excludeProperty(exclude);
                }
                crit.add(example);
                return getHibernateTemplate().findByCriteria(crit);
        }

        public T makePersistent(T entity) {
                getHibernateTemplate().saveOrUpdate(entity);
                return entity;
        }

        public void makeTransient(T entity) {
                getHibernateTemplate().delete(entity);
        }

    public Long getResults(){
            return (Long) getSession().createCriteria(persistentClass).setProjection(Projections.count("id")).uniqueResult();
    }

        /**
         * Use this inside subclasses as a convenience method.
         */
        @SuppressWarnings("unchecked")
        protected List<T> findByCriteria(Criterion... detachedCriterias) {
                DetachedCriteria crit = DetachedCriteria.forClass(persistentClass);
                for (Criterion c : detachedCriterias) {
                        crit.add(c);
                }
                return getHibernateTemplate().findByCriteria(crit);
        }

}