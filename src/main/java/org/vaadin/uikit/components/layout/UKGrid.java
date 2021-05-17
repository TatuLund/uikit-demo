package org.vaadin.uikit.components.layout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.vaadin.uikit.interfaces.UKMargin;
import org.vaadin.uikit.interfaces.UKPadding;
import org.vaadin.uikit.interfaces.UKWidthAndHeight;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.html.Div;

public class UKGrid extends Composite<Div>
        implements HasSize, UKWidthAndHeight, UKMargin, UKPadding {

    public enum GapModifier {
        SMALL("uk-grid-small"), 
        MEDIUM("uk-grid-medium"),
        LARGE("uk-grid-large"),
        COLLAPSE("uk-grid-collapse");

        private final String modifier;

        GapModifier(String modifier) {
            this.modifier = modifier;
        }

        public String getGapModfier() {
            return modifier;
        }
    }

    public enum ResponsiveBreak {
        NONE(""), 
        SMALL_640PX("@s"), 
        MEDIUM_960PX("@m"), 
        LARGE_1200PX("@l");

        private final String responsiveBreak;

        ResponsiveBreak(String responsiveBreak) {
            this.responsiveBreak = responsiveBreak;
        }

        public String getResponsiveBreak() {
            return responsiveBreak;
        }
    }

    public class UKGridCell extends Composite<Div> {

        Div cellDiv = new Div();

        public UKGridCell(int width, int rowMaxWidth) {
            int rmw = rowMaxWidth;
            if (rowMaxWidth % width == 0) {
                rmw = rowMaxWidth / width;
                width = 1;
            }
            cellDiv.addClassName("uk-width-" + width + "-" + rmw
                    + responsiveBreak.getResponsiveBreak());
        }

        public UKGridCell(int width, int rowMaxWidth, Component... components) {
            int rmw = rowMaxWidth;
            if (rowMaxWidth % width == 0) {
                rmw = rowMaxWidth / width;
                width = 1;
            }
            cellDiv.addClassName("uk-width-" + width + "-" + rmw
                    + responsiveBreak.getResponsiveBreak());
            if (components != null)
                cellDiv.add(components);
        }

        @Override
        protected Div initContent() {
            return cellDiv;
        }

        public void add(Component... components) {
            cellDiv.add(components);
        }

        public void remove(Component... components) {
            cellDiv.remove(components);
        }

        public void removeAll() {
            cellDiv.removeAll();
        }

        protected Component getCellDiv() {
            return cellDiv;
        }
    }

    public class UKGridRow implements Serializable {
        List<UKGridCell> cells = new ArrayList<>();
        int maxRowWidth;
        int totalWidth;

        public UKGridRow(int maxRowWidth) {
            this.maxRowWidth = maxRowWidth;
        }

        public UKGridRow withCell(int width) {
            totalWidth += width;
            if (totalWidth > maxRowWidth) {
                throw new IllegalStateException(
                        "Sum of widths can't be larger that maximum row width");
            }
            UKGridCell cell = new UKGridCell(width, maxRowWidth);
            cells.add(cell);
            return this;
        }

        public UKGridRow withCell(int width, Component... components) {
            totalWidth += width;
            if (totalWidth > maxRowWidth) {
                throw new IllegalStateException(
                        "Sum of widths can't be larger that maximum row width");
            }
            UKGridCell cell = new UKGridCell(width, maxRowWidth, components);
            cells.add(cell);
            return this;
        }

        public UKGridRow withRow(int maxRowWidth) {
            return UKGrid.this.withRow(maxRowWidth);
        }

        public void build() {
            UKGrid.this.build();
        }

        public UKGridCell getCell(int cell) {
            return cells.get(cell);
        }

        public List<UKGridCell> getCells() {
            return cells;
        }

        public int getCellCount() {
            return cells.size();
        }
    }

    List<UKGridRow> rows = new ArrayList<>();
    private boolean isBuilt = false;
    Div grid = new Div();
    private int numRows = 0;
    private ResponsiveBreak responsiveBreak = ResponsiveBreak.MEDIUM_960PX;

    public UKGrid() {
        this(ResponsiveBreak.MEDIUM_960PX);
    }

    public UKGrid(ResponsiveBreak responsiveBreak) {
        this.responsiveBreak = responsiveBreak;
        getElement().setAttribute("uk-grid", true);
    }

    public UKGridRow withRow(int maxRowWidth) {
        UKGridRow row = new UKGridRow(maxRowWidth);
        rows.add(row);
        return row;
    }

    public void populate(int row, int cell, Component... components) {
        if (!isBuilt) {
            throw new IllegalStateException(
                    "Can't populate grid that has not been built");
        }
        if (row > numRows && cell > rows.get(row).getCellCount()) {
            throw new IllegalStateException(
                    "Cell (" + row + "," + cell + ") does not exists in grid");
        }
        UKGridCell cellComponent = rows.get(row).getCell(cell);
        cellComponent.removeAll();
        cellComponent.add(components);
    }

    public void populate(UKGridRow row, int cell, Component... components) {
        if (!isBuilt) {
            throw new IllegalStateException(
                    "Can't populate grid that has not been built");
        }
        if (cell > row.getCellCount()) {
            throw new IllegalStateException(
                    "Cell " + cell + " does not exists in this grid row");
        }
        UKGridCell cellComponent = row.getCell(cell);
        cellComponent.removeAll();
        cellComponent.add(components);
    }

    public void populate(UKGridCell cell, Component... components) {
        if (!isBuilt) {
            throw new IllegalStateException(
                    "Can't populate grid that has not been built");
        }
        cell.removeAll();
        cell.add(components);
    }

    public void build() {
        for (UKGridRow row : rows) {
            numRows++;
            for (UKGridCell cell : row.getCells()) {
                grid.add(cell.getCellDiv());
            }
        }
        isBuilt = true;
    }

    public void setGapModifier(GapModifier modifier) {
        for (GapModifier mod : GapModifier.values()) {
            grid.removeClassName(mod.getGapModfier());
        }
        grid.addClassName(modifier.getGapModfier());
    }

    @Override
    protected Div initContent() {
        return grid;
    }

}
