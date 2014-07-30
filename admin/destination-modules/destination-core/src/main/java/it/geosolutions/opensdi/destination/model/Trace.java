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
package it.geosolutions.opensdi.destination.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * Entity for trace element
 * 
 * @author adiaz
 */
@Entity(name = "Trace")
@Table(name = "siig_t_tracciamento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "siig_t_tracciamento")
@XmlRootElement(name = "Trace")
public class Trace implements Serializable {

/** serialVersionUID */
private static final long serialVersionUID = 1448032597899191650L;

private BigInteger id_tracciamento;

private BigInteger nr_rec_shape;

private BigInteger nr_rec_storage;

private BigInteger nr_rec_scartati;

private BigInteger nr_rec_scartati_siig;

private String nome_file;

private Date data;

private Date data_imp_storage;

private Date data_elab;

private Date data_imp_siig;

private BigInteger fk_processo;

@GenericGenerator(name = "BigIntGenerator", strategy = "it.geosolutions.opensdi.destination.hibernate.BigIntGenerator")
/**
 * @return the id_tracciamento
 */
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BigIntGenerator")
@Id
public BigInteger getId_tracciamento() {
    return id_tracciamento;
}



public BigInteger getFk_processo() {
    return fk_processo;
}



public void setFk_processo(BigInteger fk_processo) {
    this.fk_processo = fk_processo;
}



/**
 * @param id_tracciamento the id_tracciamento to set
 */
public void setId_tracciamento(BigInteger id_tracciamento) {
    this.id_tracciamento = id_tracciamento;
}

/**
 * @return the nr_rec_shape
 */
@Column(nullable = false)
public BigInteger getNr_rec_shape() {
    return nr_rec_shape;
}

/**
 * @param nr_rec_shape the nr_rec_shape to set
 */
public void setNr_rec_shape(BigInteger nr_rec_shape) {
    this.nr_rec_shape = nr_rec_shape;
}

/**
 * @return the nr_rec_storage
 */
@Column(nullable = false)
public BigInteger getNr_rec_storage() {
    return nr_rec_storage;
}

/**
 * @param nr_rec_storage the nr_rec_storage to set
 */
public void setNr_rec_storage(BigInteger nr_rec_storage) {
    this.nr_rec_storage = nr_rec_storage;
}

/**
 * @return the nr_rec_scartati
 */
@Column(nullable = false)
public BigInteger getNr_rec_scartati() {
    return nr_rec_scartati;
}

/**
 * @param nr_rec_scartati the nr_rec_scartati to set
 */
public void setNr_rec_scartati(BigInteger nr_rec_scartati) {
    this.nr_rec_scartati = nr_rec_scartati;
}

/**
 * @return the nr_rec_scartati_siig
 */
@Column(nullable = false)
public BigInteger getNr_rec_scartati_siig() {
    return nr_rec_scartati_siig;
}

/**
 * @param nr_rec_scartati_siig the nr_rec_scartati_siig to set
 */
public void setNr_rec_scartati_siig(BigInteger nr_rec_scartati_siig) {
    this.nr_rec_scartati_siig = nr_rec_scartati_siig;
}

/**
 * @return the nome_file
 */
@Column(nullable = false)
public String getNome_file() {
    return nome_file;
}

/**
 * @param nome_file the nome_file to set
 */
public void setNome_file(String nome_file) {
    this.nome_file = nome_file;
}

/**
 * @return the data
 */
@Column(nullable = false)
public Date getData() {
    return data;
}

/**
 * @param data the data to set
 */
public void setData(Date data) {
    this.data = data;
}

/**
 * @return the data_imp_storage
 */
@Column(nullable = false)
public Date getData_imp_storage() {
    return data_imp_storage;
}

/**
 * @param data_imp_storage the data_imp_storage to set
 */
public void setData_imp_storage(Date data_imp_storage) {
    this.data_imp_storage = data_imp_storage;
}

/**
 * @return the data_elab
 */
@Column(nullable = false)
public Date getData_elab() {
    return data_elab;
}

/**
 * @param data_elab the data_elab to set
 */
public void setData_elab(Date data_elab) {
    this.data_elab = data_elab;
}

/**
 * @return the data_imp_siig
 */
@Column
public Date getData_imp_siig() {
    return data_imp_siig;
}

/**
 * @param data_imp_siig the data_imp_siig to set
 */
public void setData_imp_siig(Date data_imp_siig) {
    this.data_imp_siig = data_imp_siig;
}

@Override
public String toString() {
    return "Trace{" + "id=" + id_tracciamento + '}';
}

}
