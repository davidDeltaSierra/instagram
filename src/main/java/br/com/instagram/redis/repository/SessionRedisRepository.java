package br.com.instagram.redis.repository;

import br.com.instagram.redis.model.SessionRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SessionRedisRepository extends CrudRepository<SessionRedis, String> {
    Optional<SessionRedis> findByUsername(String username);
}
