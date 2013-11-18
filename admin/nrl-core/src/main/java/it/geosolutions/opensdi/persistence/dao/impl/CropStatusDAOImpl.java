/*
 *  nrl Crop Information Portal
 *  https://github.com/geosolutions-it/crop-information-portal
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

package it.geosolutions.opensdi.persistence.dao.impl;

import it.geosolutions.opensdi.model.CropStatus;
import it.geosolutions.opensdi.persistence.dao.CropStatusDAO;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

/**
 *
 * @author adiaz
 */
@Transactional(value = "opensdiTransactionManager")
public class CropStatusDAOImpl extends BaseDAO<CropStatus, Long> implements CropStatusDAO {

    private final static Logger LOGGER = LoggerFactory.getLogger(CropStatusDAOImpl.class);

    @Override
    public void persist(CropStatus... entities) {
        super.persist(entities);
    }

    @Override
    public CropStatus merge(CropStatus entity) {
        return super.merge(entity);
    }

    @Override
    public boolean remove(CropStatus entity) {
        return super.remove(entity);
    }

    @Override
    public boolean removeById(Long id) {
        return super.removeById(id);
    }

    @Override
    public boolean removeByPK(String crop, String month, String factor, Integer dec) {
    	Search search = new Search(CropStatus.class);
    	search.addFilterEqual("crop", crop);
    	search.addFilterEqual("month", month);
    	search.addFilterEqual("factor", factor);
    	search.addFilterEqual("dec", dec);
        
    	CropStatus found;
        try {
            found = searchUnique(search);
        } catch (NonUniqueResultException e) {
            LOGGER.error("Non unique result searching for CropStatus " + search, e);
            return false;
        } catch (NoResultException e) {
            return false;
        }

        return remove(found);
    }
    
}
