package org.vaadin.uikit.components;

import com.vaadin.flow.component.UI;

public class UKNotification {

    public enum Position {
        TOP_LEFT("top-left"),
        TOP_CENTER("top-center"),
        TOP_RIGHT("top-right"),
        BOTTOM_LEFT("bottom-left"),
        BOTTOM_CENTER("bottom-center"),
        BOTTOM_RIGHT("bottom-right");

        private final String position;

        Position(String position) {
            this.position = position;
        }

        public String getPosition() {
            return position;
        }
    }

    public enum Status {
        PRIMARY("primary"),
        SUCCESS("success"),
        WARNING("warning"),
        DANGER("danger");

        private final String status;

        Status(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    private String message = "";
    private Status status = Status.PRIMARY;
    private int timeout = 5000;
    private Position position = Position.TOP_CENTER;

    public UKNotification withStatus(Status status) {
        this.status = status;
        return this;
    }

    public UKNotification withPosition(Position position) {
        this.position = position;
        return this;
    }

    public UKNotification withTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public void view(String message) {
        show(message, status, position, timeout);
    }

    public static void show(String message) {
        UI.getCurrent().getPage().executeJs("UIkit.notification({message: $0})",
                message);
    }

    public static void show(String message, Status status, Position position,
            int timeout) {
        UI.getCurrent().getPage().executeJs(
                "UIkit.notification({message: $0, status: $1, pos: $2, timeout: $3})",
                message, status.getStatus(), position.getPosition(), timeout);
    }

}
