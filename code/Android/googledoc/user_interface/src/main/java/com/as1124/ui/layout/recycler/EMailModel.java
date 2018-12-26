package com.as1124.ui.layout.recycler;

public class EMailModel {

    private String topic;
    private String from;
    private String toUser;
    private String receiveDate;
    private String summary;

    public EMailModel() {
    }

    public EMailModel(String topic, String from, String receiveDate, String summary) {
        this.topic = topic;
        this.from = from;
        this.receiveDate = receiveDate;
        this.summary = summary;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
