package com.example.sdn.finova;

import java.net.URI;

/**
 * Created by sdn on 1/19/16.
 */
public class Track {

    private int id;
    private long timeTrackStart;
    private long timeTrackStop;
    private int timeTrack;
    private int timeInTrafficJam;
    private int length;
    private int maxSpeed;
    private float averageSpeed;
    private int maxEngineRpm;
    private int averageEngineRpm;
    private int numberOfEmergencyBraking;
    private int numberOfSuddenAcceleration;
    private float averageFuelConsumption;
    private float costOfTrack;
    private String addressStart;
    private String addressEnd;
    private URI img;


    public Track(int id, long timeTrackStart, long timeTrackStop, int timeTrack, int timeInTrafficJam, int length, int maxSpeed, float averageSpeed, int maxEngineRpm, int averageEngineRpm, int numberOfEmergencyBraking, int numberOfSuddenAcceleration, float averageFuelConsumption, float costOfTrack, String addressStart, String addressEnd, URI img) {
        this.id = id;
        this.timeTrackStart = timeTrackStart;
        this.timeTrackStop = timeTrackStop;
        this.timeTrack = timeTrack;
        this.timeInTrafficJam = timeInTrafficJam;
        this.length = length;
        this.maxSpeed = maxSpeed;
        this.averageSpeed = averageSpeed;
        this.maxEngineRpm = maxEngineRpm;
        this.averageEngineRpm = averageEngineRpm;
        this.numberOfEmergencyBraking = numberOfEmergencyBraking;
        this.numberOfSuddenAcceleration = numberOfSuddenAcceleration;
        this.averageFuelConsumption = averageFuelConsumption;
        this.costOfTrack = costOfTrack;
        this.addressStart = addressStart;
        this.addressEnd = addressEnd;
        this.img = img;
    }

    public Track(int id, long timeTrackStart, long timeTrackStop, int timeTrack, int length, String addressStart, String addressEnd, URI img) {
        this.id = id;
        this.timeTrackStart = timeTrackStart;
        this.timeTrackStop = timeTrackStop;
        this.timeTrack = timeTrack;
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

    public long getTimeTrackStart() {
        return timeTrackStart;
    }

    public void setTimeTrackStart(long timeTrackStart) {
        this.timeTrackStart = timeTrackStart;
    }

    public long getTimeTrackStop() {
        return timeTrackStop;
    }

    public void setTimeTrackStop(long timeTrackStop) {
        this.timeTrackStop = timeTrackStop;
    }

    public int getTimeTrack() {
        return timeTrack;
    }

    public void setTimeTrack(int timeTrack) {
        this.timeTrack = timeTrack;
    }

    public int getTimeInTrafficJam() {
        return timeInTrafficJam;
    }

    public void setTimeInTrafficJam(int timeInTrafficJam) {
        this.timeInTrafficJam = timeInTrafficJam;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public float getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(float averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public int getMaxEngineRpm() {
        return maxEngineRpm;
    }

    public void setMaxEngineRpm(int maxEngineRpm) {
        this.maxEngineRpm = maxEngineRpm;
    }

    public int getAverageEngineRpm() {
        return averageEngineRpm;
    }

    public void setAverageEngineRpm(int averageEngineRpm) {
        this.averageEngineRpm = averageEngineRpm;
    }

    public int getNumberOfEmergencyBraking() {
        return numberOfEmergencyBraking;
    }

    public void setNumberOfEmergencyBraking(int numberOfEmergencyBraking) {
        this.numberOfEmergencyBraking = numberOfEmergencyBraking;
    }

    public int getNumberOfSuddenAcceleration() {
        return numberOfSuddenAcceleration;
    }

    public void setNumberOfSuddenAcceleration(int numberOfSuddenAcceleration) {
        this.numberOfSuddenAcceleration = numberOfSuddenAcceleration;
    }

    public float getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    public void setAverageFuelConsumption(float averageFuelConsumption) {
        this.averageFuelConsumption = averageFuelConsumption;
    }

    public float getCostOfTrack() {
        return costOfTrack;
    }

    public void setCostOfTrack(float costOfTrack) {
        this.costOfTrack = costOfTrack;
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

    public URI getImg() {
        return img;
    }

    public void setImg(URI img) {
        this.img = img;
    }


}
