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
package it.geosolutions.opensdi.destination.service.impl;

import it.geosolutions.opensdi.destination.dao.TraceDAO;
import it.geosolutions.opensdi.destination.dao.TraceLineDAO;
import it.geosolutions.opensdi.destination.dto.TraceDto;
import it.geosolutions.opensdi.destination.dto.TraceLineDto;
import it.geosolutions.opensdi.destination.model.Trace;
import it.geosolutions.opensdi.destination.model.TraceLine;
import it.geosolutions.opensdi.destination.service.TraceService;
import it.geosolutions.opensdi.dto.GeobatchRunInfo;
import it.geosolutions.opensdi.utils.GeoBatchRunInfoUtils;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Trace Service implementation
 * 
 * @author adiaz
 */
@Transactional
public class TraceServiceImpl implements TraceService {

@Autowired
private TraceDAO traceDao;

@Autowired
private TraceLineDAO traceLineDao;

/**
 * Remove all run information for file contained in run information
 * 
 * @param runInfo
 */
public void cleanByRun(GeobatchRunInfo runInfo) {
    String fileName = GeoBatchRunInfoUtils.getFileName(runInfo, true);
    List<Trace> traces = traceDao.searchTraceByFile(fileName);
    if (traces != null) {
        for (Trace trace : traces) {
            traceLineDao.deleteByTrace(trace.getId_tracciamento());
            traceDao.makeTransient(trace);
        }
    }
}

/**
 * Search closest trace element for a run information. This method uses dates
 * include in run information and trace elements to obtain the closest one
 * 
 * @param runInfo
 * @return trace element closest to a run information
 */
public TraceDto searchClosestTraceByRun(GeobatchRunInfo runInfo) {
    TraceDto traceDto = null;
    if (runInfo != null) {
        Trace trace = traceDao.searchClosestTraceByFile(
                GeoBatchRunInfoUtils.getFileName(runInfo, true),
                runInfo.getLastExecutionDate());
        List<TraceLine> lines = null;
        if (trace != null) {
            lines = traceLineDao.searchByTrace(trace.getId_tracciamento());
        }
        traceDto = getTrace(trace, lines);
    }
    return traceDto;
}

/**
 * Save trace element. Just for test proposal.
 * 
 * @param trace to be saved
 * @return trace saved
 */
public TraceDto saveTrace(TraceDto trace) {
    Trace traceEntity = getTrace(trace);
    if (traceEntity.getId_tracciamento() != null) {
        // Clean old lines
        traceLineDao.deleteByTrace(traceEntity.getId_tracciamento());
    }
    List<TraceLine> lines = getTraceLines(trace);
    List<TraceLine> lineEntities = null;
    // save trace
    traceEntity = traceDao.makePersistent(traceEntity);
    // save lines
    if (lines != null) {
        lineEntities = new LinkedList<TraceLine>();
        for (TraceLine line : lines) {
            line.setId_tracciamento(traceEntity.getId_tracciamento());
            lineEntities.add(traceLineDao.makePersistent(line));
        }
    }
    return getTrace(traceEntity, lineEntities);
}

/**
 * Search trace elements for a run information.
 * 
 * @param runInfo
 * @return trace elements for a run information
 */
public List<TraceDto> searchByRun(GeobatchRunInfo runInfo) {
    List<TraceDto> list = new LinkedList<TraceDto>();
    if (runInfo != null) {
        List<Trace> traces = traceDao.searchTraceByFile(GeoBatchRunInfoUtils
                .getFileName(runInfo, true));
        if (traces != null) {
            for (Trace trace : traces) {
                List<TraceLine> lines = null;
                if (trace != null) {
                    lines = traceLineDao.searchByTrace(trace
                            .getId_tracciamento());
                }
                list.add(getTrace(trace, lines));
            }
        }
    }
    return list;
}

/**
 * Obtain a trace dto
 * 
 * @param trace entity
 * @param lines entities
 * @return trace dto
 */
private TraceDto getTrace(Trace trace, List<TraceLine> lines) {
    TraceDto traceDto = null;
    if (trace != null) {
        traceDto = new TraceDto();
        // copy plain data
        traceDto.setData(trace.getData());
        traceDto.setData_elab(trace.getData_elab());
        traceDto.setData_imp_siig(trace.getData_imp_siig());
        traceDto.setData_imp_storage(trace.getData_imp_storage());
        traceDto.setId_tracciamento(trace.getId_tracciamento());
        traceDto.setNome_file(trace.getNome_file());
        traceDto.setNr_rec_scartati(trace.getNr_rec_scartati());
        traceDto.setNr_rec_scartati_siig(trace.getNr_rec_scartati_siig());
        traceDto.setNr_rec_shape(trace.getNr_rec_shape());
        traceDto.setNr_rec_storage(trace.getNr_rec_storage());
        // lines
        if (lines != null) {
            List<TraceLineDto> lineDtos = new LinkedList<TraceLineDto>();
            for (TraceLine line : lines) {
                lineDtos.add(getTraceLine(line));
            }
            traceDto.setLines(lineDtos);
        }
    }
    return traceDto;
}

/**
 * Obtain a line dto
 * 
 * @param line entity
 * @return line dto
 */
private TraceLineDto getTraceLine(TraceLine line) {
    TraceLineDto lineDto = null;
    if (line != null) {
        lineDto = new TraceLineDto();
        // copy plain data
        lineDto.setCodice_log(line.getCodice_log());
        lineDto.setDescr_errore(line.getDescr_errore());
        lineDto.setId_tematico_shape_orig(line.getId_tematico_shape_orig());
        lineDto.setId_tracciamento(line.getId_tracciamento());
        lineDto.setProgressivo(line.getProgressivo());
    }
    return lineDto;
}

/**
 * Obtain a trace entity
 * 
 * @param trace dto
 * @return trace entity
 */
private Trace getTrace(TraceDto trace) {
    Trace traceEntity = null;
    if (trace != null) {
        traceEntity = new Trace();
        // copy plain data
        traceEntity.setData(trace.getData());
        traceEntity.setData_elab(trace.getData_elab());
        traceEntity.setData_imp_siig(trace.getData_imp_siig());
        traceEntity.setData_imp_storage(trace.getData_imp_storage());
        traceEntity.setId_tracciamento(trace.getId_tracciamento());
        traceEntity.setNome_file(trace.getNome_file());
        traceEntity.setNr_rec_scartati(trace.getNr_rec_scartati());
        traceEntity.setNr_rec_scartati_siig(trace.getNr_rec_scartati_siig());
        traceEntity.setNr_rec_shape(trace.getNr_rec_shape());
        traceEntity.setNr_rec_storage(trace.getNr_rec_storage());
    }
    return traceEntity;
}

/**
 * Obtain a line dto
 * 
 * @param line entity
 * @return line dto
 */
private TraceLine getTraceLine(TraceLineDto lineDto) {
    TraceLine line = null;
    if (lineDto != null) {
        line = new TraceLine();
        // copy plain data
        line.setCodice_log(lineDto.getCodice_log());
        line.setDescr_errore(lineDto.getDescr_errore());
        line.setId_tematico_shape_orig(lineDto.getId_tematico_shape_orig());
        line.setId_tracciamento(lineDto.getId_tracciamento());
        line.setProgressivo(lineDto.getProgressivo());
    }
    return line;
}

/**
 * Obtain all lines for a trace
 * 
 * @param trace dto
 * @return trace line entities
 */
private List<TraceLine> getTraceLines(TraceDto trace) {
    List<TraceLine> lines = null;
    // lines
    if (trace != null && trace.getLines() != null) {
        lines = new LinkedList<TraceLine>();
        for (TraceLineDto line : trace.getLines()) {
            lines.add(getTraceLine(line));
        }
    }
    return lines;
}

}
