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

import com.meforwe.meforwe.Donations;


/**
 * ServiceImpl object for domain model class Donations.
 *
 * @see Donations
 */
@Service("meforwe.DonationsService")
@Validated
public class DonationsServiceImpl implements DonationsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DonationsServiceImpl.class);


    @Autowired
    @Qualifier("meforwe.DonationsDao")
    private WMGenericDao<Donations, Integer> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<Donations, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "meforweTransactionManager")
    @Override
    public Donations create(Donations donations) {
        LOGGER.debug("Creating a new Donations with information: {}", donations);

        Donations donationsCreated = this.wmGenericDao.create(donations);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(donationsCreated);
    }

    @Transactional(readOnly = true, value = "meforweTransactionManager")
    @Override
    public Donations getById(Integer donationsId) {
        LOGGER.debug("Finding Donations by id: {}", donationsId);
        return this.wmGenericDao.findById(donationsId);
    }

    @Transactional(readOnly = true, value = "meforweTransactionManager")
    @Override
    public Donations findById(Integer donationsId) {
        LOGGER.debug("Finding Donations by id: {}", donationsId);
        try {
            return this.wmGenericDao.findById(donationsId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No Donations found with id: {}", donationsId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "meforweTransactionManager")
    @Override
    public List<Donations> findByMultipleIds(List<Integer> donationsIds, boolean orderedReturn) {
        LOGGER.debug("Finding Donations by ids: {}", donationsIds);

        return this.wmGenericDao.findByMultipleIds(donationsIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "meforweTransactionManager")
    @Override
    public Donations update(Donations donations) {
        LOGGER.debug("Updating Donations with information: {}", donations);

        this.wmGenericDao.update(donations);
        this.wmGenericDao.refresh(donations);

        return donations;
    }

    @Transactional(value = "meforweTransactionManager")
    @Override
    public Donations delete(Integer donationsId) {
        LOGGER.debug("Deleting Donations with id: {}", donationsId);
        Donations deleted = this.wmGenericDao.findById(donationsId);
        if (deleted == null) {
            LOGGER.debug("No Donations found with id: {}", donationsId);
            throw new EntityNotFoundException(String.valueOf(donationsId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "meforweTransactionManager")
    @Override
    public void delete(Donations donations) {
        LOGGER.debug("Deleting Donations with {}", donations);
        this.wmGenericDao.delete(donations);
    }

    @Transactional(readOnly = true, value = "meforweTransactionManager")
    @Override
    public Page<Donations> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Donations");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "meforweTransactionManager")
    @Override
    public Page<Donations> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Donations");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "meforweTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service meforwe for table Donations to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "meforweTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service meforwe for table Donations to {} format", options.getExportType());
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