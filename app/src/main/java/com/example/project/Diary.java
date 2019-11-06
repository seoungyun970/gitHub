package com.example.project;

public class Diary {
    private String id;
    private String title;
    private String content;
    private String name;

    public Diary(String id,String title, String content, String name) {
        this.id=id;
        this.title = title;
        this.content = content;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "id="+id+'\''+
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
