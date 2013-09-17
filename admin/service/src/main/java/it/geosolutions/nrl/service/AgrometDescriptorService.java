/**
 *
 */
package it.geosolutions.nrl.service;

import it.geosolutions.nrl.model.AgrometDescriptor;
import it.geosolutions.nrl.model.CropDescriptor;
import it.geosolutions.nrl.persistence.dao.AgrometDescriptorDAO;
import it.geosolutions.nrl.persistence.dao.CropDescriptorDAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Transactional(value = "nrlTransactionManager")
public class AgrometDescriptorService {

    @Autowired
    private AgrometDescriptorDAO agrometDescriptorDao;

    public AgrometDescriptorDAO getAgrometDescriptorDao() {
		return agrometDescriptorDao;
	}

	public void setAgrometDescriptorDao(AgrometDescriptorDAO descriptorDao) {
		this.agrometDescriptorDao = descriptorDao;
	}

	public List<AgrometDescriptor> getAll() {
        return agrometDescriptorDao.findAll();
    }

    public void persist(AgrometDescriptor cd) {
        agrometDescriptorDao.persist(cd);
    }

    public AgrometDescriptor get(String id) {
        return agrometDescriptorDao.find(id);
    }

    public void update(AgrometDescriptor c) {
        agrometDescriptorDao.merge(c);
    }

    public void delete(String id) {
        agrometDescriptorDao.removeById(id);
    }
}
