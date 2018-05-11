package com.demo.appserver.repository;

import com.demo.appserver.model.Resource;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {}