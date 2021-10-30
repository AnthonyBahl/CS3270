package edu.weber.cs.w01113559.cs3270a9;

import androidx.annotation.NonNull;

public class Assignment {

    protected int id;
    protected String name;
    protected String due_at;
    protected String html_url;

    public Assignment(int id, String name, String due_at, String html_url) {
        this.id = id;
        this.name = name;
        this.due_at = due_at;
        this.html_url = html_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDue_at() {
        return due_at;
    }

    public void setDue_at(String due_at) {
        this.due_at = due_at;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}
