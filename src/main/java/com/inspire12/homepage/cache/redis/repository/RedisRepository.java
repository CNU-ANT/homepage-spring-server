package com.inspire12.homepage.cache.redis.repository;

import com.inspire12.homepage.cache.redis.model.Redis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<Redis, Long> {
}
