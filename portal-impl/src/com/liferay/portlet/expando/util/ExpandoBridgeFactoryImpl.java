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

package com.liferay.portlet.expando.util;

import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

/**
 * @author Raymond Augé
 */
@DoPrivileged
public class ExpandoBridgeFactoryImpl implements ExpandoBridgeFactory {

	public ExpandoBridge getExpandoBridge(long companyId, String className) {
		PortalRuntimePermission.checkExpandoBridge(className);

		return new ExpandoBridgeImpl(companyId, className);
	}

	public ExpandoBridge getExpandoBridge(
		long companyId, String className, long classPK) {

		PortalRuntimePermission.checkExpandoBridge(className);

		return new ExpandoBridgeImpl(companyId, className, classPK);
	}

}