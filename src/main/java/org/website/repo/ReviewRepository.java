package org.website.repo;

import org.springframework.data.repository.CrudRepository;
import org.website.models.Reviews;

public interface ReviewRepository extends CrudRepository<Reviews, Long> {

}
