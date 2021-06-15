package org.vaadin.uikit.components.layout;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.uikit.components.interfaces.UkTableOptions;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.dom.Element;

@Tag("table")
public class UkTable extends HtmlComponent implements UkTableOptions {

    public abstract class AbstractTableCell extends HtmlComponent {

        public AbstractTableCell(String text) {
            this(1, 1, text);
        }

        public AbstractTableCell(Component component) {
            this(1, 1, component);
        }

        public AbstractTableCell(int colSpan, int rowSpan,
                Component component) {
            setColSpan(colSpan);
            setRowSpan(rowSpan);
            setContent(component);
        }

        public AbstractTableCell(int colSpan, int rowSpan, String text) {
            setColSpan(colSpan);
            setRowSpan(rowSpan);
            setText(text);
        }

        private void setColSpan(int colSpan) {
            if (colSpan > 1) {
                getElement().setAttribute("colspan", "" + colSpan);
            } else {
                getElement().removeAttribute("colspan");
            }
        }

        private void setRowSpan(int rowSpan) {
            if (rowSpan > 1) {
                getElement().setAttribute("rowspan", "" + rowSpan);
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
    public class UkTableCell extends AbstractTableCell {

        public UkTableCell(String text) {
            super(text);
        }

        public UkTableCell(Component component) {
            super(component);
        }

        public UkTableCell(int colSpan, int rowSpan, Component component) {
            super(colSpan, rowSpan, component);
        }

        public UkTableCell(int colSpan, int rowSpan, String text) {
            super(colSpan, rowSpan, text);
        }
    }

    @Tag("th")
    public class UkTableHeaderCell extends AbstractTableCell {

        public UkTableHeaderCell(String text) {
            super(text);
        }

        public UkTableHeaderCell(Component component) {
            super(component);
        }

        public UkTableHeaderCell(int colSpan, int rowSpan,
                Component component) {
            super(colSpan, rowSpan, component);
        }

        public UkTableHeaderCell(int colSpan, int rowSpan, String text) {
            super(colSpan, rowSpan, text);
        }
    }

    @Tag("tr")
    public class UkTableRow extends HtmlComponent {
        List<UkTableCell> cells = new ArrayList<>();
        int maxRowWidth;
        int totalWidth;

        public UkTableRow() {
        }

        public UkTableRow withCell(String text) {
            UkTableCell cell = new UkTableCell(text);
            cells.add(cell);
            return this;
        }

        public UkTableRow withCell(Component component) {
            UkTableCell cell = new UkTableCell(component);
            cells.add(cell);
            return this;
        }

        public UkTableRow withCell(int colSpan, int rowSpan,
                Component component) {
            UkTableCell cell = new UkTableCell(colSpan, rowSpan, component);
            cells.add(cell);
            return this;
        }

        public UkTableRow withCell(int colSpan, int rowSpan, String text) {
            UkTableCell cell = new UkTableCell(colSpan, rowSpan, text);
            cells.add(cell);
            return this;
        }

        public UkTableRow withRow() {
            return UkTable.this.withRow();
        }

        public UkTableRow withFooterRow() {
            return UkTable.this.withFooterRow();
        }

        public void build() {
            UkTable.this.build();
        }

        public UkTableCell getCell(int cell) {
            return cells.get(cell);
        }

        public List<UkTableCell> getCells() {
            return cells;
        }

        public int getCellCount() {
            return cells.size();
        }
    }

    public class UkTableHeaderRow extends UkTableRow {
        List<UkTableHeaderCell> cells = new ArrayList<>();
        int maxRowWidth;
        int totalWidth;

        public UkTableHeaderRow() {
        }

        public UkTableHeaderRow withCell(String text) {
            UkTableHeaderCell cell = new UkTableHeaderCell(text);
            cells.add(cell);
            return this;
        }

        public UkTableHeaderRow withCell(Component component) {
            UkTableHeaderCell cell = new UkTableHeaderCell(component);
            cells.add(cell);
            return this;
        }

        public UkTableHeaderRow withCell(int colSpan, int rowSpan,
                Component component) {
            UkTableHeaderCell cell = new UkTableHeaderCell(colSpan, rowSpan,
                    component);
            cells.add(cell);
            return this;
        }

        public UkTableHeaderRow withCell(int colSpan, int rowSpan,
                String text) {
            UkTableHeaderCell cell = new UkTableHeaderCell(colSpan, rowSpan,
                    text);
            cells.add(cell);
            return this;
        }

        public UkTableRow withRow() {
            return UkTable.this.withRow();
        }

        public void build() {
            UkTable.this.build();
        }

        public UkTableHeaderCell getHeaderCell(int cell) {
            return cells.get(cell);
        }

        public List<UkTableHeaderCell> getHeaderCells() {
            return cells;
        }

        public int getCellCount() {
            return cells.size();
        }
    }

    List<UkTableRow> rows = new ArrayList<>();
    private boolean isBuilt = false;
    private int numRows = 0;
    Element bodyElement = new Element("tbody");
    Element headerElement = new Element("thead");
    Element footerElement = new Element("tfoot");
    private UkTableHeaderRow headerRow;
    private UkTableRow footerRow;

    public UkTable() {
        addClassNames("uk-table", "uk-table-responsive");
    }

    public UkTableRow withRow() {
        UkTableRow row = new UkTableRow();
        rows.add(row);
        return row;
    }

    public UkTableHeaderRow withHeaderRow() {
        headerRow = new UkTableHeaderRow();
        return headerRow;
    }

    public UkTableRow withFooterRow() {
        footerRow = new UkTableRow();
        return footerRow;
    }

    public void populate(int row, int cell, Component component) {
        UkTableCell cellComponent = getCell(row, cell);
        cellComponent.setContent(component);
    }

    public void populate(UkTableRow row, int cell, Component component) {
        int rowIndex = rows.indexOf(row);
        UkTableCell cellComponent = getCell(rowIndex, cell);
        cellComponent.setContent(component);
    }

    public void populate(UkTableCell cell, Component component) {
        if (isBuilt) {
            throw new IllegalStateException(
                    "Can't populate table that has not been built");
        }
        cell.setContent(component);
    }

    public void populate(int row, int cell, String text) {
        UkTableCell cellComponent = getCell(row, cell);
        cellComponent.setText(text);
    }

    public void populate(UkTableRow row, int cell, String text) {
        int rowIndex = rows.indexOf(row);
        UkTableCell cellComponent = getCell(rowIndex, cell);
        cellComponent.setText(text);
    }

    public void populate(UkTableCell cell, String text) {
        if (!isBuilt) {
            throw new IllegalStateException(
                    "Can't populate table that has not been built");
        }
        cell.setText(text);
    }

    public UkTableCell getCell(int row, int cell) {
        if (!isBuilt) {
            throw new IllegalStateException(
                    "Can't access table that has not been built");
        }
        if (row > numRows && cell > rows.get(row).getCellCount()) {
            throw new IllegalStateException(
                    "Cell (" + row + "," + cell + ") does not exists in table");
        }
        return rows.get(row).getCell(cell);
    }

    public void build() {
        if (headerRow != null) {
            getElement().appendChild(headerElement);
            for (UkTableHeaderCell cell : headerRow.getHeaderCells()) {
                headerRow.getElement().appendChild(cell.getElement());
            }
            headerElement.appendChild(headerRow.getElement());
        }

        if (rows.isEmpty()) {
            throw new IllegalStateException("Can't build table without rows");
        }
        getElement().appendChild(bodyElement);
        for (UkTableRow row : rows) {
            numRows++;
            bodyElement.appendChild(row.getElement());
            for (UkTableCell cell : row.getCells()) {
                row.getElement().appendChild(cell.getElement());
            }
        }

        if (footerRow != null) {
            getElement().appendChild(footerElement);
            for (UkTableCell cell : footerRow.getCells()) {
                footerRow.getElement().appendChild(cell.getElement());
            }
            footerElement.appendChild(footerRow.getElement());
        }

        isBuilt = true;
    }

}
