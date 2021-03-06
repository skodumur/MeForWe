/*Copyright (c) 2015-2016 wavemaker-com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker-com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker-com*/
package com.meforwe.meforwe.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wavemaker.commons.wrapper.StringWrapper;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.manager.ExportedFileManager;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import com.meforwe.meforwe.OrgLocations;
import com.meforwe.meforwe.service.OrgLocationsService;


/**
 * Controller object for domain model class OrgLocations.
 * @see OrgLocations
 */
@RestController("meforwe.OrgLocationsController")
@Api(value = "OrgLocationsController", description = "Exposes APIs to work with OrgLocations resource.")
@RequestMapping("/meforwe/OrgLocations")
public class OrgLocationsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrgLocationsController.class);

    @Autowired
	@Qualifier("meforwe.OrgLocationsService")
	private OrgLocationsService orgLocationsService;

	@Autowired
	private ExportedFileManager exportedFileManager;

	@ApiOperation(value = "Creates a new OrgLocations instance.")
    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public OrgLocations createOrgLocations(@RequestBody OrgLocations orgLocations) {
		LOGGER.debug("Create OrgLocations with information: {}" , orgLocations);

		orgLocations = orgLocationsService.create(orgLocations);
		LOGGER.debug("Created OrgLocations with information: {}" , orgLocations);

	    return orgLocations;
	}

    @ApiOperation(value = "Returns the OrgLocations instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public OrgLocations getOrgLocations(@PathVariable("id") Integer id) {
        LOGGER.debug("Getting OrgLocations with id: {}" , id);

        OrgLocations foundOrgLocations = orgLocationsService.getById(id);
        LOGGER.debug("OrgLocations details with id: {}" , foundOrgLocations);

        return foundOrgLocations;
    }

    @ApiOperation(value = "Updates the OrgLocations instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public OrgLocations editOrgLocations(@PathVariable("id") Integer id, @RequestBody OrgLocations orgLocations) {
        LOGGER.debug("Editing OrgLocations with id: {}" , orgLocations.getOrgId());

        orgLocations.setOrgId(id);
        orgLocations = orgLocationsService.update(orgLocations);
        LOGGER.debug("OrgLocations details with id: {}" , orgLocations);

        return orgLocations;
    }

    @ApiOperation(value = "Deletes the OrgLocations instance associated with the given id.")
    @RequestMapping(value = "/{id:.+}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public boolean deleteOrgLocations(@PathVariable("id") Integer id) {
        LOGGER.debug("Deleting OrgLocations with id: {}" , id);

        OrgLocations deletedOrgLocations = orgLocationsService.delete(id);

        return deletedOrgLocations != null;
    }

    /**
     * @deprecated Use {@link #findOrgLocations(String, Pageable)} instead.
     */
    @Deprecated
    @ApiOperation(value = "Returns the list of OrgLocations instances matching the search criteria.")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<OrgLocations> searchOrgLocationsByQueryFilters( Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering OrgLocations list by query filter:{}", (Object) queryFilters);
        return orgLocationsService.findAll(queryFilters, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of OrgLocations instances matching the optional query (q) request param. If there is no query provided, it returns all the instances. Pagination & Sorting parameters such as page& size, sort can be sent as request parameters. The sort value should be a comma separated list of field names & optional sort order to sort the data on. eg: field1 asc, field2 desc etc ")
    @RequestMapping(method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<OrgLocations> findOrgLocations(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering OrgLocations list by filter:", query);
        return orgLocationsService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns the paginated list of OrgLocations instances matching the optional query (q) request param. This API should be used only if the query string is too big to fit in GET request with request param. The request has to made in application/x-www-form-urlencoded format.")
    @RequestMapping(value="/filter", method = RequestMethod.POST, consumes= "application/x-www-form-urlencoded")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Page<OrgLocations> filterOrgLocations(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering OrgLocations list by filter", query);
        return orgLocationsService.findAll(query, pageable);
    }

    @ApiOperation(value = "Returns downloadable file for the data matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
    @RequestMapping(value = "/export/{exportType}", method = {RequestMethod.GET,  RequestMethod.POST}, produces = "application/octet-stream")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public Downloadable exportOrgLocations(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
         return orgLocationsService.export(exportType, query, pageable);
    }

    @ApiOperation(value = "Returns a URL to download a file for the data matching the optional query (q) request param and the required fields provided in the Export Options.") 
    @RequestMapping(value = "/export", method = {RequestMethod.POST}, consumes = "application/json")
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    public StringWrapper exportOrgLocationsAndGetURL(@RequestBody DataExportOptions exportOptions, Pageable pageable) {
        String exportedFileName = exportOptions.getFileName();
        if(exportedFileName == null || exportedFileName.isEmpty()) {
            exportedFileName = OrgLocations.class.getSimpleName();
        }
        exportedFileName += exportOptions.getExportType().getExtension();
        String exportedUrl = exportedFileManager.registerAndGetURL(exportedFileName, outputStream -> orgLocationsService.export(exportOptions, pageable, outputStream));
        return new StringWrapper(exportedUrl);
    }

	@ApiOperation(value = "Returns the total count of OrgLocations instances matching the optional query (q) request param. If query string is too big to fit in GET request's query param, use POST method with application/x-www-form-urlencoded format.")
	@RequestMapping(value = "/count", method = {RequestMethod.GET, RequestMethod.POST})
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Long countOrgLocations( @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
		LOGGER.debug("counting OrgLocations");
		return orgLocationsService.count(query);
	}

    @ApiOperation(value = "Returns aggregated result with given aggregation info")
	@RequestMapping(value = "/aggregations", method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
	public Page<Map<String, Object>> getOrgLocationsAggregatedValues(@RequestBody AggregationInfo aggregationInfo, Pageable pageable) {
        LOGGER.debug("Fetching aggregated results for {}", aggregationInfo);
        return orgLocationsService.getAggregatedValues(aggregationInfo, pageable);
    }


    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service OrgLocationsService instance
	 */
	protected void setOrgLocationsService(OrgLocationsService service) {
		this.orgLocationsService = service;
	}

}