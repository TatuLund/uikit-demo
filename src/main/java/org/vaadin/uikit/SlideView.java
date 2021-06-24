package org.vaadin.uikit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.vaadin.uikit.components.UkCard;
import org.vaadin.uikit.components.UkNotification;
import org.vaadin.uikit.components.UkSlideshow;
import org.vaadin.uikit.components.UkSlideshow.Animation;
import org.vaadin.uikit.components.UkSlideshow.NavMode;
import org.vaadin.uikit.components.UkVideo;
import org.vaadin.uikit.components.interfaces.UkInverse.Invert;
import org.vaadin.uikit.components.layout.UkFlex;
import org.vaadin.uikit.components.util.ClassResourceFactory;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;

@PreserveOnRefresh
@PageTitle("SlideShow")
@Route(value = "slide", layout = MainLayout.class)
public class SlideView extends UkFlex {

    public SlideView() {
        setDirection(Direction.COLUMN);
        setVerticalAlignment(VerticalAlignment.MIDDLE);
        setHorizontalAlignment(HorizontalAlignment.AROUND);
        setSizeFull();
        UkSlideshow slideShow = new UkSlideshow();
        UkCard card = new UkCard();
        card.setTitle("Slideshow");
        try {
            slideShow.addSlide("photo.jpg").add(new H3("Daily temperature"),createChart("#ffffff"));
            slideShow.addSlide("light.jpg");
            slideShow.addSlide("dark.jpg");
        } catch (FileNotFoundException e) {
            UkNotification.show("File not found");
        }
//        slideShow.setNavMode(NavMode.DOTS);
        slideShow.setInverse(Invert.LIGHT);
        card.setContent(slideShow);
        card.setWidth(1,2);
        slideShow.setAnimation(Animation.FADE);
//        slide.play();
        
        add(card);

//        File file;
//        try {
//            file = new ClassResourceFactory("big_buck_bunny.mp4").getFile();
//            UkVideo video = new UkVideo(file,"Bunny video");
//            add(video);
//        } catch (FileNotFoundException e) {
//            UkNotification.show("Video not found");
//        }

    }

    Html createChart(String color) {
        Random random = new Random();
        List<Integer> data = random.ints(300, -100, 100).boxed()
                .collect(Collectors.toList());
        String svg = "<div><svg class=\"uk-animation-stroke\" style=\"width: 100%; height: 100%; --uk-animation-stroke: 100000;\" preserveAspectRatio=\"none\" viewBox=\"0 -100 600 200\"><polyline points=\"";
        int index = 0;
        for (int number : data) {
            svg += index + "," + number + " ";
            index += 2;
        }
        svg += "\" style=\"stroke-width: 1;fill:none;stroke:" + color
                + "\"></polyline></svg></div>";
        Html chart = new Html(svg);
        chart.getElement().getStyle().set("height", "100px");
        return chart;
    }

}
