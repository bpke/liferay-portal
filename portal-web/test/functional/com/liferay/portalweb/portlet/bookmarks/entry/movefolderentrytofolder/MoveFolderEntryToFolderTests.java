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

package com.liferay.portalweb.portlet.bookmarks.entry.movefolderentrytofolder;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.bookmarks.entry.addfolderentry.TearDownBookmarksEntryTest;
import com.liferay.portalweb.portlet.bookmarks.folder.addfolder.AddFolder1Test;
import com.liferay.portalweb.portlet.bookmarks.folder.addfolder.AddFolder2Test;
import com.liferay.portalweb.portlet.bookmarks.folder.addfolder.TearDownBookmarksFolderTest;
import com.liferay.portalweb.portlet.bookmarks.portlet.addportletbookmarks.AddPageBookmarksTest;
import com.liferay.portalweb.portlet.bookmarks.portlet.addportletbookmarks.AddPortletBookmarksTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MoveFolderEntryToFolderTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageBookmarksTest.class);
		testSuite.addTestSuite(AddPortletBookmarksTest.class);
		testSuite.addTestSuite(AddFolder1Test.class);
		testSuite.addTestSuite(AddFolder2Test.class);
		testSuite.addTestSuite(AddFolder1EntryTest.class);
		testSuite.addTestSuite(MoveFolderEntryToFolderTest.class);
		testSuite.addTestSuite(ViewFolder2EntryTest.class);
		testSuite.addTestSuite(TearDownBookmarksFolderTest.class);
		testSuite.addTestSuite(TearDownBookmarksEntryTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}