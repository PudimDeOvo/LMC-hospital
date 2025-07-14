package com.example.clinic.Database.ReviewDatabase;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.example.clinic.Entities.Review.Review;

public class ReviewDatabase {

    private static ReviewDatabase instance;
    private static final String FILE_PATH = "src/main/database/Review.csv";

    private ReviewDatabase() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            File f = new File(FILE_PATH);
            if (f.length() == 0) {
                bw.write("doctorUsername,comment,date\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ReviewDatabase getInstance() {
        if (instance == null) {
            instance = new ReviewDatabase();
        }
        return instance;
    }

    public void addReview(Review eval) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String line = String.join(",",
                    eval.getDoctorUsername(),
                    eval.getComment().replace(",", ";"),
                    eval.getDate()
            );
            bw.write(line + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Review> getReviewForDoctor(String doctorUsername) {
        List<Review> evaluations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", 5);
                if (data.length == 3 && data[0].equalsIgnoreCase(doctorUsername)) {
                    Review eval = new Review(
                            data[0],
                            data[1],
                            data[2]
                    );
                    evaluations.add(eval);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return evaluations;
    }
}
