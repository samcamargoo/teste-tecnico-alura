CREATE TABLE IF NOT EXISTS tb_courses_rating (
                                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                 course_id BIGINT,
                                                 rater_id BIGINT,
                                                 reason VARCHAR(255),
    rating INT,
    rated_at TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES tb_courses(id),
    FOREIGN KEY (rater_id) REFERENCES tb_users(id)
    );
