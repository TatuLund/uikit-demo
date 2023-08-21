package org.vaadin.uikit.components.layout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.vaadin.uikit.components.interfaces.UkBoxShadow;
import org.vaadin.uikit.components.interfaces.UkHidden;
import org.vaadin.uikit.components.interfaces.UkMargin;
import org.vaadin.uikit.components.interfaces.UkPadding;
import org.vaadin.uikit.components.interfaces.UkSizing;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.html.Div;

@SuppressWarnings("serial")
public class UkGrid extends Composite<Div>
        implements HasSize, UkSizing, UkMargin, UkPadding, UkBoxShadow, UkHidden {

    public enum GapModifier {
        // @formatter:off
        SMALL("uk-grid-small"),
        MEDIUM("uk-grid-medium"),
        LARGE("uk-grid-large"),
        COLLAPSE("uk-grid-collapse");
        // @formatter:on

        private final String modifier;

        GapModifier(String modifier) {
            this.modifier = modifier;
        }

        public String getGapModfier() {
            return modifier;
        }
    }

    public enum ResponsiveBreak {
        // @formatter:off
        NONE(""),
        SMALL_640PX("@s"),
        MEDIUM_960PX("@m"),
        LARGE_1200PX("@l");
        // @formatter:on

        private final String responsiveBreak;

        ResponsiveBreak(String responsiveBreak) {
            this.responsiveBreak = responsiveBreak;
        }

        public String getResponsiveBreak() {
            return responsiveBreak;
        }
    }

    public class UkGridCell extends Composite<Div> {

        Div cellDiv = new Div();

        public UkGridCell(int width, int rowMaxWidth) {
            int rmw = rowMaxWidth;
            if (rowMaxWidth % width == 0) {
                rmw = rowMaxWidth / width;
                width = 1;
            }
            cellDiv.addClassName("uk-width-" + width + "-" + rmw
                    + responsiveBreak.getResponsiveBreak());
        }

        public UkGridCell(int width, int rowMaxWidth, Component... components) {
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

    public class UkGridRow implements Serializable {
        List<UkGridCell> cells = new ArrayList<>();
        int maxRowWidth;
        int totalWidth;

        public UkGridRow(int maxRowWidth) {
            this.maxRowWidth = maxRowWidth;
        }

        public UkGridRow withCell(int width) {
            totalWidth += width;
            if (totalWidth > maxRowWidth) {
                throw new IllegalStateException(
                        "Sum of widths can't be larger that maximum row width");
            }
            UkGridCell cell = new UkGridCell(width, maxRowWidth);
            cells.add(cell);
            return this;
        }

        public UkGridRow withCell(int width, Component... components) {
            totalWidth += width;
            if (totalWidth > maxRowWidth) {
                throw new IllegalStateException(
                        "Sum of widths can't be larger that maximum row width");
            }
            UkGridCell cell = new UkGridCell(width, maxRowWidth, components);
            cells.add(cell);
            return this;
        }

        public UkGridRow withRow(int maxRowWidth) {
            return UkGrid.this.withRow(maxRowWidth);
        }

        public void build() {
            UkGrid.this.build();
        }

        public UkGridCell getCell(int cell) {
            return cells.get(cell);
        }

        public List<UkGridCell> getCells() {
            return cells;
        }

        public int getCellCount() {
            return cells.size();
        }
    }

    List<UkGridRow> rows = new ArrayList<>();
    private boolean isBuilt = false;
    Div grid = new Div();
    private int numRows = 0;
    private ResponsiveBreak responsiveBreak = ResponsiveBreak.MEDIUM_960PX;

    public UkGrid() {
        this(ResponsiveBreak.MEDIUM_960PX);
    }

    public UkGrid(ResponsiveBreak responsiveBreak) {
        this.responsiveBreak = responsiveBreak;
        getElement().setAttribute("uk-grid", true);
    }

    public UkGridRow withRow(int maxRowWidth) {
        UkGridRow row = new UkGridRow(maxRowWidth);
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
        UkGridCell cellComponent = rows.get(row).getCell(cell);
        cellComponent.removeAll();
        cellComponent.add(components);
    }

    public void populate(UkGridRow row, int cell, Component... components) {
        if (!isBuilt) {
            throw new IllegalStateException(
                    "Can't populate grid that has not been built");
        }
        if (cell > row.getCellCount()) {
            throw new IllegalStateException(
                    "Cell " + cell + " does not exists in this grid row");
        }
        UkGridCell cellComponent = row.getCell(cell);
        cellComponent.removeAll();
        cellComponent.add(components);
    }

    public void populate(UkGridCell cell, Component... components) {
        if (!isBuilt) {
            throw new IllegalStateException(
                    "Can't populate grid that has not been built");
        }
        cell.removeAll();
        cell.add(components);
    }

    public void build() {
        for (UkGridRow row : rows) {
            numRows++;
            for (UkGridCell cell : row.getCells()) {
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
