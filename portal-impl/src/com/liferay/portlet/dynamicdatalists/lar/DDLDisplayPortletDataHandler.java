/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.dynamicdatalists.lar;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;

import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Michael C. Han
 */
public class DDLDisplayPortletDataHandler extends DDLPortletDataHandler {

	public DDLDisplayPortletDataHandler() {
		setDataPortletPreferences(
			"recordSetId", "displayDDMTemplateId", "formDDMTemplateId");
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletPreferences == null) {
			return portletPreferences;
		}

		portletPreferences.setValue("recordSetId", StringPool.BLANK);
		portletPreferences.setValue("displayDDMTemplateId", StringPool.BLANK);
		portletPreferences.setValue("formDDMTemplateId", StringPool.BLANK);
		portletPreferences.setValue("editable", Boolean.TRUE.toString());
		portletPreferences.setValue("spreadsheet", Boolean.FALSE.toString());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.dynamicdatalist",
			portletDataContext.getScopeGroupId());

		long recordSetId = GetterUtil.getLong(
			portletPreferences.getValue("recordSetId", null), 0);

		if (recordSetId == 0) {
			if (_log.isDebugEnabled()) {
				_log.debug("No record set found for " + portletId);
			}

			return StringPool.BLANK;
		}

		Element rootElement = addExportRootElement();

		DDLRecordSet recordSet = DDLRecordSetLocalServiceUtil.getRecordSet(
			recordSetId);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, rootElement, recordSet);

		return rootElement.formattedString();
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.dynamicdatalist",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		if (Validator.isNull(data)) {
			return null;
		}

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element recordSetElement = rootElement.element("record-set");

		if (recordSetElement != null) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, recordSetElement);
		}

		long importedRecordSetId = GetterUtil.getLong(
			portletPreferences.getValue("recordSetId", null));
		long importedDisplayDDMTemplateId = GetterUtil.getLong(
			portletPreferences.getValue("displayDDMTemplateId", null));
		long importedFormDDMTemplateId = GetterUtil.getLong(
			portletPreferences.getValue("formDDMTemplateId", null));

		Map<Long, Long> recordSetIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDLRecordSet.class);

		long recordSetId = MapUtil.getLong(
			recordSetIds, importedRecordSetId, importedRecordSetId);

		Map<Long, Long> templateIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMTemplate.class);

		long displayDDMTemplateId = MapUtil.getLong(
			templateIds, importedDisplayDDMTemplateId,
			importedDisplayDDMTemplateId);

		long formDDMTemplateId = MapUtil.getLong(
			templateIds, importedFormDDMTemplateId, importedFormDDMTemplateId);

		portletPreferences.setValue("recordSetId", String.valueOf(recordSetId));
		portletPreferences.setValue(
			"displayDDMTemplateId", String.valueOf(displayDDMTemplateId));
		portletPreferences.setValue(
			"formDDMTemplateId", String.valueOf(formDDMTemplateId));

		return portletPreferences;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDLDisplayPortletDataHandler.class);

}