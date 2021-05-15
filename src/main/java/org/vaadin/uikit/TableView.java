package org.vaadin.uikit;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.uikit.UKButton.ButtonVariant;

import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;

@Route("table")
@StyleSheet("context://uikit.css")
@JavaScript("context://uikit.js")
@JavaScript("context://uikit-icons.js")
public class TableView extends UKFlex {

    int year = 2000;
    private List<MonthlyExpense> data;
    private ListDataProvider<MonthlyExpense> dp;
    private int index = 0;

    public TableView() {
    	setDirection(Direction.COLUMN);
    	setVerticalAlignment(VerticalAlignment.MIDDLE);
    	setHorizontalAlignment(HorizontalAlignment.AROUND);
    	setSizeFull();
    	setSizeFull();
        UKBeanTable<MonthlyExpense> table = new UKBeanTable<>(MonthlyExpense.class,false,12);
        table.setHtmlAllowed(true);
        table.setSmall(true);
        table.addColumn("year", MonthlyExpense::getYear);
        table.addColumn("month", expense -> "<i>" + expense.getMonth() + "</i>");
        table.addColumn("expense", MonthlyExpense::getExpenses);
        table.setStripes(true);
        table.addComponentColumn(null, expense -> {
        	UKButton edit = new UKButton("edit");
        	edit.setVariant(ButtonVariant.PRIMARY);
        	edit.addClickListener(event -> {
        		UKNotification.show("Not implemented");
        	});
           return edit;
        });
//        table.setColumns("year","month","expenses");
        data = getData();
        table.setItems(data);
    	UKButton plus = new UKButton("+");
    	UKButton minus = new UKButton("-");
        dp = (ListDataProvider<MonthlyExpense>) table.getDataProvider();
//        dp.setFilter(expense -> expense.getYear() == year);
        plus.addClickListener(event -> {
            year++;
            dp.setFilter(expense -> expense.getYear() == year);
        });
        minus.addClickListener(event -> {
            year--;
            dp.setFilter(expense -> expense.getYear() == year);
        });
        table.setWidthFull();
        UKFlex buttons = new UKFlex();
        buttons.add(plus,minus);
        add(table);        
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

    public  Double getExpenses() {
        return Math.floor((Math.random() * 1000) % 500 + 300);
    }

    public class MonthlyExpense {
        private String month;
        private Double expenses;
        private int year;
        private String status = "Open";

        public MonthlyExpense(String month, int year, Double expenses) {
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

    }

}
