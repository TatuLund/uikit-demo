package org.vaadin.uikit;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.dom.Element;

@Tag("table")
public class UKTable extends HtmlComponent implements UKTableOptions {

	public abstract class UKAbstractTableCell extends HtmlComponent {
		
		public UKAbstractTableCell(String text) {
			this(1,1,text);
		}

		public UKAbstractTableCell(Component component) {
			this(1,1,component);
		}

		public UKAbstractTableCell(int colSpan, int rowSpan, Component component) {
			setColSpan(colSpan);
			setRowSpan(rowSpan);
			setContent(component);
		}

		public UKAbstractTableCell(int colSpan, int rowSpan, String text) {
			setColSpan(colSpan);
			setRowSpan(rowSpan);
			setText(text);
		}

		private void setColSpan(int colSpan) {
			if (colSpan > 1) {
				getElement().setAttribute("colspan", ""+colSpan);
			} else {
				getElement().removeAttribute("colspan");				
			}
		}

		private void setRowSpan(int rowSpan) {
			if (rowSpan > 1) {
				getElement().setAttribute("rowspan", ""+rowSpan);
			} else {
				getElement().removeAttribute("rowspan");				
			}
		}

		public void setContent(Component component) {
			getElement().removeAllChildren();
			getElement().appendChild(component.getElement());
		}

		public void setText(String text) {
			getElement().setText(text);
		}

		public void clear() {
			getElement().setText("");
		}

		public void setPreserveWidth(boolean presereWidth) {
			if (presereWidth) {
				addClassName("uk-presere-width");
			} else {
				removeClassName("uk-presere-width");			
			}
		}
	}

	@Tag("td")
	public class UKTableCell extends UKAbstractTableCell {

		public UKTableCell(String text) {
			super(text);
		}

		public UKTableCell(Component component) {
			super(component);
		}

		public UKTableCell(int colSpan, int rowSpan, Component component) {
			super(colSpan,rowSpan,component);
		}

		public UKTableCell(int colSpan, int rowSpan, String text) {
			super(colSpan,rowSpan,text);
		}
	}

	@Tag("th")
	public class UKTableHeaderCell extends UKAbstractTableCell {

		public UKTableHeaderCell(String text) {
			super(text);
		}

		public UKTableHeaderCell(Component component) {
			super(component);
		}

		public UKTableHeaderCell(int colSpan, int rowSpan, Component component) {
			super(colSpan,rowSpan,component);
		}

		public UKTableHeaderCell(int colSpan, int rowSpan, String text) {
			super(colSpan,rowSpan,text);
		}
	}
		
	@Tag("tr")
	public class UKTableRow extends HtmlComponent {
		List<UKTableCell> cells = new ArrayList<>(); 
		int maxRowWidth;
		int totalWidth;

		public UKTableRow() {
		}

		public UKTableRow withCell(String text) {
			UKTableCell cell = new UKTableCell(text);
			cells.add(cell);
			return this;
		}

		public UKTableRow withCell(Component component) {
			UKTableCell cell = new UKTableCell(component);
			cells.add(cell);
			return this;
		}

		public UKTableRow withCell(int colSpan, int rowSpan, Component component) {
			UKTableCell cell = new UKTableCell(colSpan,rowSpan,component);
			cells.add(cell);
			return this;
		}

		public UKTableRow withCell(int colSpan, int rowSpan, String text) {
			UKTableCell cell = new UKTableCell(colSpan,rowSpan,text);
			cells.add(cell);
			return this;
		}

		public UKTableRow withRow() {
			return UKTable.this.withRow();
		}
		
		public UKTableRow withFooterRow() {
			return UKTable.this.withFooterRow();
		}
		
		public void build() {
			UKTable.this.build();
		}

		public UKTableCell getCell(int cell) {
			return cells.get(cell);
		}

		public List<UKTableCell> getCells() {
			return cells;
		}

		public int getCellCount() {
			return cells.size();
		}
	}

	public class UKTableHeaderRow extends UKTableRow {
		List<UKTableHeaderCell> cells = new ArrayList<>(); 
		int maxRowWidth;
		int totalWidth;

		public UKTableHeaderRow() {
		}

		public UKTableHeaderRow withCell(String text) {
			UKTableHeaderCell cell = new UKTableHeaderCell(text);
			cells.add(cell);
			return this;
		}

		public UKTableHeaderRow withCell(Component component) {
			UKTableHeaderCell cell = new UKTableHeaderCell(component);
			cells.add(cell);
			return this;
		}

		public UKTableHeaderRow withCell(int colSpan, int rowSpan, Component component) {
			UKTableHeaderCell cell = new UKTableHeaderCell(colSpan,rowSpan,component);
			cells.add(cell);
			return this;
		}

		public UKTableHeaderRow withCell(int colSpan, int rowSpan, String text) {
			UKTableHeaderCell cell = new UKTableHeaderCell(colSpan,rowSpan,text);
			cells.add(cell);
			return this;
		}

		public UKTableRow withRow() {
			return UKTable.this.withRow();
		}
		
		public void build() {
			UKTable.this.build();
		}

		public UKTableHeaderCell getHeaderCell(int cell) {
			return cells.get(cell);
		}

		public List<UKTableHeaderCell> getHeaderCells() {
			return cells;
		}

		public int getCellCount() {
			return cells.size();
		}
	}	

	List<UKTableRow> rows = new ArrayList<>();
	private boolean isBuilt = false;
	private int numRows = 0;
	Element bodyElement = new Element("tbody");
	Element headerElement = new Element("thead");
	Element footerElement = new Element("tfoot");
	private UKTableHeaderRow headerRow;
	private UKTableRow footerRow;
	
	public UKTable() {
        addClassNames("uk-table","uk-table-responsive");
	}
	
	public UKTableRow withRow() {
		UKTableRow row = new UKTableRow();
		rows.add(row);
		return row;
	}

	public UKTableHeaderRow withHeaderRow() {
		headerRow = new UKTableHeaderRow();
		return headerRow;
	}

	public UKTableRow withFooterRow() {
		footerRow = new UKTableRow();
		return footerRow;
	}

	public void populate(int row, int cell, Component component) {
		UKTableCell cellComponent = getCell(row,cell);
		cellComponent.setContent(component);
	}

	public void populate(UKTableRow row, int cell, Component component) {
		int rowIndex = rows.indexOf(row);
		UKTableCell cellComponent = getCell(rowIndex,cell);
		cellComponent.setContent(component);
	}

	public void populate(UKTableCell cell, Component component) {
		if (isBuilt) {
			throw new IllegalStateException("Can't populate table that has not been built");			
		}
		cell.setContent(component);
	}

	public void populate(int row, int cell, String text) {
		UKTableCell cellComponent = getCell(row,cell);
		cellComponent.setText(text);
	}

	public void populate(UKTableRow row, int cell, String text) {
		int rowIndex = rows.indexOf(row);
		UKTableCell cellComponent = getCell(rowIndex,cell);
		cellComponent.setText(text);
	}

	public void populate(UKTableCell cell, String text) {
		if (!isBuilt) {
			throw new IllegalStateException("Can't populate table that has not been built");			
		}
		cell.setText(text);
	}

	public UKTableCell getCell(int row, int cell) {
		if (!isBuilt) {
			throw new IllegalStateException("Can't access table that has not been built");			
		}
		if (row > numRows && cell > rows.get(row).getCellCount()) {
			throw new IllegalStateException("Cell ("+row+","+cell+") does not exists in table");			
		}
		return rows.get(row).getCell(cell);
	}
	
	public void build() {
		if (headerRow != null) {
			getElement().appendChild(headerElement);
			for (UKTableHeaderCell cell : headerRow.getHeaderCells()) {
				headerRow.getElement().appendChild(cell.getElement());
			}
			headerElement.appendChild(headerRow.getElement());
		}
		
		if (rows.isEmpty()) {
			throw new IllegalStateException("Can't build table without rows");
		}
		getElement().appendChild(bodyElement);
		for (UKTableRow row : rows) {
			numRows ++;
			bodyElement.appendChild(row.getElement());
			for (UKTableCell cell : row.getCells()) {
				row.getElement().appendChild(cell.getElement());
			}
		}

		if (footerRow != null) {
			getElement().appendChild(footerElement);		
			for (UKTableCell cell : footerRow.getCells()) {
				footerRow.getElement().appendChild(cell.getElement());
			}
			footerElement.appendChild(footerRow.getElement());		
		}

		isBuilt = true;
	}

}
