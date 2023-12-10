package org.vaadin.uikit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.vaadin.uikit.components.UkBeanTable;
import org.vaadin.uikit.components.UkBeanTable.ColumnWidth;
import org.vaadin.uikit.components.UkButton;
import org.vaadin.uikit.components.UkNotification;
import org.vaadin.uikit.components.input.UkIntegerField;
import org.vaadin.uikit.components.input.UkNumberField;
import org.vaadin.uikit.components.input.UkTextField;
import org.vaadin.uikit.components.UkButton.ButtonSize;
import org.vaadin.uikit.components.UkButton.ButtonVariant;
import org.vaadin.uikit.components.UkCard;
import org.vaadin.uikit.components.UkCard.CardVariant;
import org.vaadin.uikit.components.UkNotification.Status;
import org.vaadin.uikit.components.interfaces.UkBorder.BorderStyle;
import org.vaadin.uikit.components.UkIcons;
import org.vaadin.uikit.components.UkModal;
import org.vaadin.uikit.components.layout.UkForm;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.DoubleRangeValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@PageTitle("Table")
@Route(value = "table", layout = MainLayout.class)
public class TableView extends AbstractView {

    private List<MonthlyExpense> data;
    private int index = 0;

    UkBeanTable<MonthlyExpense> table = new UkBeanTable<>(MonthlyExpense.class,
            false, 12);
    Binder<MonthlyExpense> binder = new Binder<>();
    UkModal dialog = new UkModal();
    private MonthlyExpense expense;

    public TableView() {
        UkCard card = new UkCard();
        card.setTitle("Table");

        table.setHtmlAllowed(true);
        table.setSmall(true);
        table.addColumn("id", MonthlyExpense::getId)
                .setWidth(ColumnWidth.SHIRNK);
        table.addColumn("year", MonthlyExpense::getYear)
                .setWidth(ColumnWidth.SHIRNK);
        table.addColumn("month", expense -> "<i>" + expense.getMonth() + "</i>")
                .setWidth(ColumnWidth.EXPAND);
        table.addColumn("expense", MonthlyExpense::getExpenses)
                .setWidth(ColumnWidth.EXPAND);
        table.setStripes(true);

        dialog.setWidth(FixedWidth.XLARGE);
        dialog.setHeight(FixedHeight.LARGE);

        setupForm();
        add(dialog);

        table.addComponentColumn(null, expense -> {
            UkButton edit = new UkButton("edit");
            edit.setVariant(ButtonVariant.PRIMARY);
            edit.setSize(ButtonSize.SMALL);
            edit.setBorder(BorderStyle.ROUNDED);
            edit.setIcon(UkIcons.PENCIL.create());
            edit.addClickListener(event -> {
                this.expense = expense;
                binder.readBean(expense);
                dialog.show();
            });
            return edit;
        });
        table.setButtonBorder(BorderStyle.ROUNDED);
        data = getData();
        table.setItems(data);

        table.setWidthFull();
        card.setContent(table);
        card.setMargin(MarginSize.SMALL);
        card.setVariant(CardVariant.SECONDARY);
        card.setWidth(FixedWidth.XXLARGE);
        card.setOverflow(OverflowMode.AUTO);
        add(card);
    }

    private void setupForm() {
        UkForm form = new UkForm();
        form.setHorizontal(true);

        UkIntegerField year = new UkIntegerField(2000,
                LocalDate.now().getYear(), 1);
        UkNumberField expenses = new UkNumberField(0, Double.MAX_VALUE, 0.1);
        UkTextField month = new UkTextField();

        form.add("Year", year);
        form.add("Month", month);
        form.add("Expenses", expenses);

        List<String> months = Arrays
                .asList(new java.text.DateFormatSymbols().getMonths());

        binder.forField(year).asRequired().bind(MonthlyExpense::getYear,
                MonthlyExpense::setYear);
        binder.forField(expenses)
                .withValidator(new DoubleRangeValidator("Must non-negative", 0d,
                        Double.MAX_VALUE))
                .bind(MonthlyExpense::getExpenses, MonthlyExpense::setExpenses);
        binder.forField(month).asRequired()
                .withValidator(value -> months.contains(value),
                        "Must be valid month name")
                .bind(MonthlyExpense::getMonth, MonthlyExpense::setMonth);

        UkButton cancelButton = new UkButton("Cancel");
        cancelButton.addClickListener(e -> {
            dialog.hide();
        });
        UkButton saveButton = new UkButton("Save");
        saveButton.setVariant(ButtonVariant.PRIMARY);
        saveButton.addClickListener(e -> {
            saveExpense(expense);
        });

        dialog.addToFooter(cancelButton, saveButton);
        dialog.add(form);
    }

    public void saveExpense(MonthlyExpense expense) {
        try {
            binder.writeBean(expense);
            dialog.hide();
            UkNotification note = new UkNotification();
            note.withStatus(Status.SUCCESS).view("Saved");
            table.getDataProvider().refreshItem(expense);
        } catch (ValidationException ec) {
            UkNotification note = new UkNotification();
            note.withStatus(Status.DANGER).view("Input not valid");
        }
    }

    public List<MonthlyExpense> getData() {
        String[] monthNames = new java.text.DateFormatSymbols().getMonths();
        List<MonthlyExpense> data = new ArrayList<>();
        for (int year = 2000; year < 2020; year++) {
            for (int month = 0; month < 12; month++) {
                data.add(new MonthlyExpense(monthNames[month], year,
                        getExpenses()));
            }
        }
        return data;
    }

    public Double getExpenses() {
        return Math.floor((Math.random() * 1000) % 500 + 300);
    }

    public class MonthlyExpense {
        private int id;
        private String month;
        private Double expenses;
        private int year;

        public MonthlyExpense(String month, int year, Double expenses) {
            setId(index++);
            setMonth(month);
            setExpenses(expenses);
            setYear(year);
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public Double getExpenses() {
            return expenses;
        }

        public void setExpenses(Double expenses) {
            this.expenses = expenses;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + Objects.hash(id);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            MonthlyExpense other = (MonthlyExpense) obj;
            return id == other.id;
        }
    }

}
