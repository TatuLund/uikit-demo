package org.vaadin.uikit;

import org.vaadin.uikit.components.UkButton;
import org.vaadin.uikit.components.UkCard;
import org.vaadin.uikit.components.UkIcons;
import org.vaadin.uikit.components.UkProgress;
import org.vaadin.uikit.components.UkSpinner;
import org.vaadin.uikit.components.layout.UkFlex;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Progress")
@Route(value = "progress", layout = MainLayout.class)
public class ProgressView extends AbstractView {

    public ProgressView()  {
        UkCard card = new UkCard();
        card.setTitle("Progress");
        UkFlex flex = new UkFlex();
        flex.setDirection(Direction.COLUMN);
        flex.setVerticalAlignment(VerticalAlignment.MIDDLE);
        flex.setHorizontalAlignment(HorizontalAlignment.BETWEEN);
        flex.setHeight(FixedHeight.MEDIUM);
        card.setContent(flex);
        
        UkProgress progress = new UkProgress();
        progress.setWidth(FixedWidth.MEDIUM);
        progress.setTooltip("Progress bar");
        progress.setValue(0);
        flex.add(progress);

        UkSpinner spinner = new UkSpinner(2);
        flex.add(spinner);
        spinner.setVisible(false);
        
        UkButton button = new UkButton(UkIcons.PLAY.create());
        button.addClickListener(event -> {
            Thread thread = new Thread(() -> {
                getUI().ifPresent(ui -> {
                    ui.access(() -> {
                        spinner.setVisible(true);
                    });
                });
                for (int i=0;i<11;i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    final int value = i*10;
                    getUI().ifPresent(ui -> {
                        ui.access(() -> {
                            progress.setValue(value);
                        });
                    });
                }
                getUI().ifPresent(ui -> {
                    ui.access(() -> {
                        spinner.setVisible(false);
                    });
                });
            });
                        
            thread.start();
        });

        flex.add(button);
        
        add(card);
    }
}
