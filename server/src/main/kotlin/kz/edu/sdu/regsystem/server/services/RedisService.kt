package kz.edu.sdu.regsystem.server.services

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kz.edu.sdu.regsystem.server.domain.*
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedisService(val redisTemplate: RedisTemplate<String, String>) {
    fun set(key: String, list: ArrayList<*>) {
        val gson = Gson()
        val json = gson.toJson(list)
        redisTemplate.opsForValue().set(key, json, 30, TimeUnit.DAYS)
    }

    fun getAreas(key: String) : List<Area> {
        val data = redisTemplate.opsForValue().get(key)
        val gson = Gson()
        val collectionType = object : TypeToken<List<Area>>() {

        }.type
        val areas : List<Area> = gson.fromJson(data, collectionType)

        return areas
    }

    fun getCities(key: String) : List<City> {
        val data = redisTemplate.opsForValue().get(key)
        val gson = Gson()
        val collectionType = object : TypeToken<List<City>>() {

        }.type
        val cities : List<City> = gson.fromJson(data, collectionType)

        return cities
    }

    fun getSchools(key: String) : List<School> {
        val data = redisTemplate.opsForValue().get(key)
        val gson = Gson()
        val collectionType = object : TypeToken<List<School>>() {

        }.type
        val schools : List<School> = gson.fromJson(data, collectionType)

        return schools
    }

    fun getFaculties(key: String) : List<Faculty> {
        val data = redisTemplate.opsForValue().get(key)
        val gson = Gson()
        val collectionType = object : TypeToken<List<Faculty>>() {

        }.type
        val faculties : List<Faculty> = gson.fromJson(data, collectionType)

        return faculties
    }

    fun getSpecialties(key: String) : List<Specialty> {
        val data = redisTemplate.opsForValue().get(key)
        val gson = Gson()
        val collectionType = object : TypeToken<List<Specialty>>() {

        }.type
        val specialties : List<Specialty> = gson.fromJson(data, collectionType)

        return specialties
    }
}