package com.inspire12.homepage.domain.repository;

import com.inspire12.homepage.domain.model.FileMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMetaRepository extends JpaRepository<FileMeta, Integer> {
}
