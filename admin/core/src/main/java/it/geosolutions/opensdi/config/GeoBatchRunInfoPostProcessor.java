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
package it.geosolutions.opensdi.config;

import it.geosolutions.opensdi.dto.GeobatchRunInfo;
import it.geosolutions.opensdi.service.GeoBatchClient;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * GeoBatchRunInformation post processor interface.
 * 
 * @author adiaz
 * @see GeoBatchClient
 */
public abstract class GeoBatchRunInfoPostProcessor implements BeanPostProcessor {

/**
 * Process a GeobatchrunInfo bean
 * 
 * @param runInformation
 * @param parameters optional parameters to parse to the operation
 * @return processed bean
 */
public abstract GeobatchRunInfo postProcessAfterOperation(
        GeobatchRunInfo runInformation, Object... parameters);

/**
 * Register this instance for GeoBatch client instances
 */
public Object postProcessAfterInitialization(Object bean, String beanName)
        throws BeansException {
    if (bean instanceof GeoBatchClient) {
        ((GeoBatchClient) bean).registerPostProcessor(this);
    }
    return bean;
}

/**
 * Empty method.
 * 
 * @see BeanPostProcessor#postProcessBeforeInitialization(Object, String)
 */
public Object postProcessBeforeInitialization(Object bean, String beanName)
        throws BeansException {
    return bean;
}

}
