/**
 *
 */
package it.geosolutions.nrl.service;

import it.geosolutions.nrl.model.CropDescriptor;
import it.geosolutions.nrl.persistence.dao.CropDescriptorDAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Transactional(value = "nrlTransactionManager")
public class CropDescriptorService {

    @Autowired
    private CropDescriptorDAO cropDescriptorDao;

    public CropDescriptorDAO getCropDescriptorDao() {
		return cropDescriptorDao;
	}

	public void setCropDescriptorDao(CropDescriptorDAO cropDescriptorDao) {
		this.cropDescriptorDao = cropDescriptorDao;
	}

	public List<CropDescriptor> getAll() {
        return cropDescriptorDao.findAll();
    }

    public void persist(CropDescriptor cd) {
        cropDescriptorDao.persist(cd);
    }

    public CropDescriptor get(String id) {
        return cropDescriptorDao.find(id);
    }

    public void update(CropDescriptor c) {
        cropDescriptorDao.merge(c);
    }

    public void delete(String id) {
        cropDescriptorDao.removeById(id);
    }
}
