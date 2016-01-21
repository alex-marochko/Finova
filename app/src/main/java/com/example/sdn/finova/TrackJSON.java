package com.example.sdn.finova;

import java.net.URI;

/**
 * Created by sdn on 1/19/16.
 */
public class TrackJSON {

    private int id;
    private long time_track_start;
    private long time_track_stop;
    private int time_track;
    private int time_in_traffic_jam;
    private int length;
    private int max_speed;
    private float average_speed;
    private int max_engine_rpm;
    private int average_engine_rpm;
    private int number_of_emergency_braking;
    private int number_of_sudden_acceleration;
    private float average_fuel_consumption;
    private float cost_of_track;
    private String addressStart;
    private String addressEnd;
    private String img;


    public TrackJSON(int id, long time_track_start, long time_track_stop, int time_track, int time_in_traffic_jam, int length, int max_speed, float average_speed, int max_engine_rpm, int average_engine_rpm, int number_of_emergency_braking, int number_of_sudden_acceleration, float average_fuel_consumption, float cost_of_track, String addressStart, String addressEnd, String img) {
        this.id = id;
        this.time_track_start = time_track_start;
        this.time_track_stop = time_track_stop;
        this.time_track = time_track;
        this.time_in_traffic_jam = time_in_traffic_jam;
        this.length = length;
        this.max_speed = max_speed;
        this.average_speed = average_speed;
        this.max_engine_rpm = max_engine_rpm;
        this.average_engine_rpm = average_engine_rpm;
        this.number_of_emergency_braking = number_of_emergency_braking;
        this.number_of_sudden_acceleration = number_of_sudden_acceleration;
        this.average_fuel_consumption = average_fuel_consumption;
        this.cost_of_track = cost_of_track;
        this.addressStart = addressStart;
        this.addressEnd = addressEnd;
        this.img = img;
    }

    public TrackJSON(int id, long time_track_start, long time_track_stop, int time_track, int length, String addressStart, String addressEnd, String img) {
        this.id = id;
        this.time_track_start = time_track_start;
        this.time_track_stop = time_track_stop;
        this.time_track = time_track;
        this.length = length;
        this.addressStart = addressStart;
        this.addressEnd = addressEnd;
        this.img = img;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime_track_start() {
        return time_track_start;
    }

    public void setTime_track_start(long time_track_start) {
        this.time_track_start = time_track_start;
    }

    public long getTime_track_stop() {
        return time_track_stop;
    }

    public void setTime_track_stop(long time_track_stop) {
        this.time_track_stop = time_track_stop;
    }

    public int getTime_track() {
        return time_track;
    }

    public void setTime_track(int time_track) {
        this.time_track = time_track;
    }

    public int getTime_in_traffic_jam() {
        return time_in_traffic_jam;
    }

    public void setTime_in_traffic_jam(int time_in_traffic_jam) {
        this.time_in_traffic_jam = time_in_traffic_jam;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getMax_speed() {
        return max_speed;
    }

    public void setMax_speed(int max_speed) {
        this.max_speed = max_speed;
    }

    public float getAverage_speed() {
        return average_speed;
    }

    public void setAverage_speed(float average_speed) {
        this.average_speed = average_speed;
    }

    public int getMax_engine_rpm() {
        return max_engine_rpm;
    }

    public void setMax_engine_rpm(int max_engine_rpm) {
        this.max_engine_rpm = max_engine_rpm;
    }

    public int getAverage_engine_rpm() {
        return average_engine_rpm;
    }

    public void setAverage_engine_rpm(int average_engine_rpm) {
        this.average_engine_rpm = average_engine_rpm;
    }

    public int getNumber_of_emergency_braking() {
        return number_of_emergency_braking;
    }

    public void setNumber_of_emergency_braking(int number_of_emergency_braking) {
        this.number_of_emergency_braking = number_of_emergency_braking;
    }

    public int getNumber_of_sudden_acceleration() {
        return number_of_sudden_acceleration;
    }

    public void setNumber_of_sudden_acceleration(int number_of_sudden_acceleration) {
        this.number_of_sudden_acceleration = number_of_sudden_acceleration;
    }

    public float getAverage_fuel_consumption() {
        return average_fuel_consumption;
    }

    public void setAverage_fuel_consumption(float average_fuel_consumption) {
        this.average_fuel_consumption = average_fuel_consumption;
    }

    public float getCost_of_track() {
        return cost_of_track;
    }

    public void setCost_of_track(float cost_of_track) {
        this.cost_of_track = cost_of_track;
    }

    public String getAddressStart() {
        return addressStart;
    }

    public void setAddressStart(String addressStart) {
        this.addressStart = addressStart;
    }

    public String getAddressEnd() {
        return addressEnd;
    }

    public void setAddressEnd(String addressEnd) {
        this.addressEnd = addressEnd;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


}
