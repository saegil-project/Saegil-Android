package com.saegil.map.map

import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.math.cos
import kotlin.math.sin

@ExperimentalTime
@ExperimentalCoroutinesApi
class MapApiCallTest {

    private data class Location(
        val latitude: Double,
        val longitude: Double
    ) {
        fun toLatLng() = LatLng(latitude, longitude)
    }

    private fun generateRandomLocation(previousLocation: Location? = null): Location {
        if (previousLocation == null) {
            // 초기 위치는 서울 지역 근처의 위도/경도 범위
            val latitude = Random.nextDouble(37.4, 37.7)
            val longitude = Random.nextDouble(126.8, 127.2)
            return Location(latitude, longitude)
        }

        // 이전 위치에서 작은 변화를 주어 새로운 위치 생성
        // 대부분의 경우 0.1km 이내의 변화를 주고, 가끔 더 큰 변화를 줌
        val distance = if (Random.nextDouble() < 0.8) {
            // 80% 확률로 0.1km 이내의 변화
            Random.nextDouble(0.01, 0.1)
        } else {
            // 20% 확률로 0.1km 이상의 변화
            Random.nextDouble(0.1, 0.5)
        }

        // 방향을 랜덤하게 선택 (0-360도)
        val bearing = Random.nextDouble(0.0, 360.0)
        val bearingRad = Math.toRadians(bearing)

        // 거리를 위도/경도 변화량으로 변환
        val latChange = distance * cos(bearingRad) / 111.0 // 1도 = 약 111km
        val lonChange = distance * sin(bearingRad) / (111.0 * cos(Math.toRadians(previousLocation.latitude)))

        return Location(
            latitude = previousLocation.latitude + latChange,
            longitude = previousLocation.longitude + lonChange
        )
    }

    @Test
    fun `compare API call frequency between optimized and non-optimized versions`(): Unit = runTest {
        // 1000개의 연속적인 위치 변화 생성
        val locations = mutableListOf<Location>()
        var currentLocation: Location? = null
        
        repeat(1000) {
            currentLocation = generateRandomLocation(currentLocation)
            locations.add(currentLocation!!)
        }

        var nonOptimizedCallCount = 0
        var optimizedCallCount = 0
        var lastLocation: Location? = null

        // 최적화 안한 버
        locations.forEach { location ->
            nonOptimizedCallCount++
        }

        // 최적전 한 버전
        locations.forEach { location ->
            val distance = lastLocation?.let {
                calculateDistance(it.toLatLng(), location.toLatLng())
            }

            if (distance == null || distance > MapConstants.THRESHOLD) {
                optimizedCallCount++
            }

            lastLocation = location
        }

        println("Test Results:")
        println("Total location changes: ${locations.size}")
        println("Non-optimized API calls: $nonOptimizedCallCount")
        println("Optimized API calls: $optimizedCallCount")
        println("Reduction in API calls: ${((nonOptimizedCallCount - optimizedCallCount) * 100.0 / nonOptimizedCallCount).toInt()}%")
    }
} 