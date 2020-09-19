package io.hashimati.domains;

import java.util.Objects;

public class MyMessage {

    private String myMessage;

    public MyMessage() {
    }

    public MyMessage(String myMessage) {
        this.myMessage = myMessage;
    }

    public String getMyMessage() {
        return myMessage;
    }

    public void setMyMessage(String myMessage) {
        this.myMessage = myMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyMessage myMessage1 = (MyMessage) o;
        return Objects.equals(myMessage, myMessage1.myMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myMessage);
    }

    @Override
    public String toString() {
        return "MyMessage{" +
                "myMessage='" + myMessage + '\'' +
                '}';
    }
}
