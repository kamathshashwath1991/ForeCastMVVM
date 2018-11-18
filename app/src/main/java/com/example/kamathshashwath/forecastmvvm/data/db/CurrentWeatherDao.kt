package com.example.kamathshashwath.forecastmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kamathshashwath.forecastmvvm.data.CURRENT_WEATHER_ID
import com.example.kamathshashwath.forecastmvvm.data.CurrentWeatherEntry
import com.example.kamathshashwath.forecastmvvm.data.db.unitlocalized.ImperialCurrentWeatherEntry
import com.example.kamathshashwath.forecastmvvm.data.db.unitlocalized.MetricCurrentWeatherEntry
import com.example.kamathshashwath.forecastmvvm.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query(value = "SELECT * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntry>

    @Query(value = "SELECT * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial (): LiveData<ImperialCurrentWeatherEntry>
}
