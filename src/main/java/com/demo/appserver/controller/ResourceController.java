package com.demo.appserver.controller;

import java.util.List;

import com.demo.appserver.model.Resource;
import com.demo.appserver.repository.ResourceRepository;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resource")
public class ResourceController {
    private ResourceRepository resourceRepository;

    public ResourceController(ResourceRepository resourceRepository){
        this.resourceRepository = resourceRepository;
    }
    @GetMapping("/greeting/internal")
    public String greeting_internal(){
        return "Greetings internal user";
    }

    @GetMapping("/greeting")
    public String greeting(){
        return "Greetings customer";
    }

    @GetMapping
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    @PostMapping
    public void addResource(@RequestBody Resource resource) {
        resourceRepository.save(resource);
    }

    @PutMapping("/{id}")
    public void editResource(@PathVariable long id, @RequestBody Resource resource) {
        Resource existingRes = resourceRepository.findById(id).get();
        Assert.notNull(existingRes, "Resource not found");
        existingRes.setContent(resource.getContent());
        resourceRepository.save(existingRes);
    }

    @DeleteMapping("/{id}")
    public void deleteResource(@PathVariable long id) {
        resourceRepository.deleteById(id);
    }
}