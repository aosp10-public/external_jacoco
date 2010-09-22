/*******************************************************************************
 * Copyright (c) 2009, 2010 Mountainminds GmbH & Co. KG and Contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marc R. Hoffmann - initial API and implementation
 *    
 * $Id: $
 *******************************************************************************/
package org.jacoco.report.html.table;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.analysis.ICoverageNode;
import org.jacoco.core.analysis.ICoverageNode.CounterEntity;
import org.jacoco.report.ReportOutputFolder;
import org.jacoco.report.html.HTMLElement;
import org.jacoco.report.html.resources.Resources;
import org.jacoco.report.html.resources.Styles;

/**
 * Column that prints the coverage percentage for each item and the total
 * percentage in the footer. The implementation is stateless, instances might be
 * used in parallel.
 * 
 * @author Marc R. Hoffmann
 * @version $Revision: $
 */
public class PercentageColumn implements ICoverageTableColumn {

	private final String header;

	private final CounterEntity entity;

	private final NumberFormat percentageFormat = DecimalFormat
			.getPercentInstance();

	/**
	 * Creates a new column that is based on the {@link ICounter} for the given
	 * entity.
	 * 
	 * @param header
	 *            column header caption
	 * @param entity
	 *            counter entity for this column
	 */
	public PercentageColumn(final String header, final CounterEntity entity) {
		this.header = header;
		this.entity = entity;
	}

	public void init(final List<ICoverageTableItem> items,
			final ICoverageNode total) {
	}

	public boolean isVisible() {
		return true;
	}

	public String getStyle() {
		return Styles.CTR2;
	}

	public void header(final HTMLElement td, final Resources resources,
			final ReportOutputFolder base) throws IOException {
		td.text(header);
	}

	public void footer(final HTMLElement td, final ICoverageNode total,
			final Resources resources, final ReportOutputFolder base)
			throws IOException {
		cell(td, total);
	}

	public void item(final HTMLElement td, final ICoverageTableItem item,
			final Resources resources, final ReportOutputFolder base)
			throws IOException {
		cell(td, item.getNode());
	}

	private void cell(final HTMLElement td, final ICoverageNode node)
			throws IOException {
		final ICounter counter = node.getCounter(entity);
		final int total = counter.getTotalCount();
		if (total == 0) {
			td.text("n/a");
		} else {
			td.text(percentageFormat.format(counter.getCoveredRatio()));
		}
	}

}