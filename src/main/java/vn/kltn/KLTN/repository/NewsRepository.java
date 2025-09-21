package vn.kltn.KLTN.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import vn.kltn.KLTN.entity.News;

@RepositoryRestResource
public interface NewsRepository extends JpaRepository<News, String> {

}
