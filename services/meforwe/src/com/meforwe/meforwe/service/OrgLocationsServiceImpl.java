/*Copyright (c) 2015-2016 wavemaker-com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker-com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker-com*/
package com.meforwe.meforwe.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.meforwe.meforwe.OrgLocations;


/**
 * ServiceImpl object for domain model class OrgLocations.
 *
 * @see OrgLocations
 */
@Service("meforwe.OrgLocationsService")
@Validated
public class OrgLocationsServiceImpl implements OrgLocationsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrgLocationsServiceImpl.class);


    @Autowired
    @Qualifier("meforwe.OrgLocationsDao")
    private WMGenericDao<OrgLocations, Integer> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<OrgLocations, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "meforweTransactionManager")
    @Override
    public OrgLocations create(OrgLocations orgLocations) {
        LOGGER.debug("Creating a new OrgLocations with information: {}", orgLocations);

        OrgLocations orgLocationsCreated = this.wmGenericDao.create(orgLocations);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(orgLocationsCreated);
    }

    @Transactional(readOnly = true, value = "meforweTransactionManager")
    @Override
    public OrgLocations getById(Integer orglocationsId) {
        LOGGER.debug("Finding OrgLocations by id: {}", orglocationsId);
        return this.wmGenericDao.findById(orglocationsId);
    }

    @Transactional(readOnly = true, value = "meforweTransactionManager")
    @Override
    public OrgLocations findById(Integer orglocationsId) {
        LOGGER.debug("Finding OrgLocations by id: {}", orglocationsId);
        try {
            return this.wmGenericDao.findById(orglocationsId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No OrgLocations found with id: {}", orglocationsId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "meforweTransactionManager")
    @Override
    public List<OrgLocations> findByMultipleIds(List<Integer> orglocationsIds, boolean orderedReturn) {
        LOGGER.debug("Finding OrgLocations by ids: {}", orglocationsIds);

        return this.wmGenericDao.findByMultipleIds(orglocationsIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "meforweTransactionManager")
    @Override
    public OrgLocations update(OrgLocations orgLocations) {
        LOGGER.debug("Updating OrgLocations with information: {}", orgLocations);

        this.wmGenericDao.update(orgLocations);
        this.wmGenericDao.refresh(orgLocations);

        return orgLocations;
    }

    @Transactional(value = "meforweTransactionManager")
    @Override
    public OrgLocations delete(Integer orglocationsId) {
        LOGGER.debug("Deleting OrgLocations with id: {}", orglocationsId);
        OrgLocations deleted = this.wmGenericDao.findById(orglocationsId);
        if (deleted == null) {
            LOGGER.debug("No OrgLocations found with id: {}", orglocationsId);
            throw new EntityNotFoundException(String.valueOf(orglocationsId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "meforweTransactionManager")
    @Override
    public void delete(OrgLocations orgLocations) {
        LOGGER.debug("Deleting OrgLocations with {}", orgLocations);
        this.wmGenericDao.delete(orgLocations);
    }

    @Transactional(readOnly = true, value = "meforweTransactionManager")
    @Override
    public Page<OrgLocations> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all OrgLocations");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "meforweTransactionManager")
    @Override
    public Page<OrgLocations> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all OrgLocations");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "meforweTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service meforwe for table OrgLocations to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "meforweTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service meforwe for table OrgLocations to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "meforweTransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "meforweTransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }



}