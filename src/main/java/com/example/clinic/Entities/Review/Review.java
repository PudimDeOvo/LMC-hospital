package com.example.clinic.Entities.Review;

public class Review {
    private String doctorUsername;
    private String comment;
    private String date;

    public Review(String doctorUsername, String comment, String date) {
        this.doctorUsername = doctorUsername;
        this.comment = comment;
        this.date = date;
    }

    public String getDoctorUsername() {
        return doctorUsername;
    }

    public void setDoctorUsername(String doctorUsername) {
        this.doctorUsername = doctorUsername;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
