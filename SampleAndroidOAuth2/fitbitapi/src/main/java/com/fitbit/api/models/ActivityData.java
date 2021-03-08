package com.fitbit.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivityData {
    @SerializedName("activityId")
    @Expose
    private Integer activityId;
    @SerializedName("activityParentId")
    @Expose
    private Integer activityParentId;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("calories")
    @Expose
    private Double calories;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("logId")
    @Expose
    private Integer logId;
    @SerializedName("steps")
    @Expose
    private Integer steps;
    @SerializedName("hasStartTime")
    @Expose
    private Boolean hasStartTime;
    @SerializedName("isFavorite")
    @Expose
    private Boolean isFavorite;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getActivityParentId() {
        return activityParentId;
    }

    public void setActivityParentId(Integer activityParentId) {
        this.activityParentId = activityParentId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public Boolean getHasStartTime() {
        return hasStartTime;
    }

    public void setHasStartTime(Boolean hasStartTime) {
        this.hasStartTime = hasStartTime;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }
}
