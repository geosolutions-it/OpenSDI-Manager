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
package it.geosolutions.opensdi.destination.dto;

import java.io.Serializable;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO for trace line
 * 
 * @author adiaz
 *
 */
@XmlRootElement(name = "TraceLine")
public class TraceLineDto implements Serializable{

    /** serialVersionUID */
    private static final long serialVersionUID = -8481245082518107726L;
    
    private BigInteger progressivo;
    private String codice_log;
    private String descr_errore;
    private BigInteger id_tematico_shape_orig;
    private BigInteger id_tracciamento;

    /**
     * @return the progressivo
     */
    public BigInteger getProgressivo() {
        return progressivo;
    }

    /**
     * @param progressivo the progressivo to set
     */
    public void setProgressivo(BigInteger progressivo) {
        this.progressivo = progressivo;
    }

    /**
     * @return the codice_log
     */
    public String getCodice_log() {
        return codice_log;
    }

    /**
     * @param codice_log the codice_log to set
     */
    public void setCodice_log(String codice_log) {
        this.codice_log = codice_log;
    }

    /**
     * @return the descr_errore
     */
    public String getDescr_errore() {
        return descr_errore;
    }

    /**
     * @param descr_errore the descr_errore to set
     */
    public void setDescr_errore(String descr_errore) {
        this.descr_errore = descr_errore;
    }

    /**
     * @return the id_tematico_shape_orig
     */
    public BigInteger getId_tematico_shape_orig() {
        return id_tematico_shape_orig;
    }

    /**
     * @param id_tematico_shape_orig the id_tematico_shape_orig to set
     */
    public void setId_tematico_shape_orig(BigInteger id_tematico_shape_orig) {
        this.id_tematico_shape_orig = id_tematico_shape_orig;
    }

    /**
     * @return the id_tracciamento
     */
    public BigInteger getId_tracciamento() {
        return id_tracciamento;
    }

    /**
     * @param id_tracciamento the id_tracciamento to set
     */
    public void setId_tracciamento(BigInteger id_tracciamento) {
        this.id_tracciamento = id_tracciamento;
    }

    @Override
    public String toString() {
        return "TraceLine{" 
                + "trace=" + id_tracciamento + ',' 
                + "progressivo=" + progressivo + '}';
    }


}
