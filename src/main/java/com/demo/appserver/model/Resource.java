package com.demo.appserver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;

    public Resource() {}

    public Resource(String content){
        this.content = content;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }
    
    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }   
    
}