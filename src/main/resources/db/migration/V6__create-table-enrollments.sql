CREATE TABLE IF NOT EXISTS tb_enrollments (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          user_id BIGINT,
                                          course_id BIGINT,
                                          registration_date TIMESTAMP,
                                          FOREIGN KEY (user_id) REFERENCES tb_users(id),
    FOREIGN KEY (course_id) REFERENCES tb_courses(id)
    );
