package kz.edu.sdu.regsystem.server.services

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisService(val redisTemplate: RedisTemplate<String, String>) {

}