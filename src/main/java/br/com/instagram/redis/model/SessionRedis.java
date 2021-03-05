package br.com.instagram.redis.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RedisHash
public class SessionRedis implements Serializable {
    @Id
    private String id;
    @Indexed
    private String username;
    private Map<String, String> cookies;

    @TimeToLive(unit = TimeUnit.DAYS)
    private Long timeout = 360L;
}
