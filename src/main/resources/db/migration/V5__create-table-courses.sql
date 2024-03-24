CREATE TABLE IF NOT EXISTS tb_courses (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(255) NOT NULL,
    code VARCHAR(10) UNIQUE NOT NULL,
    instructor_id BIGINT,
    description TEXT,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    inactivated_at TIMESTAMP,
    FOREIGN KEY (instructor_id) REFERENCES tb_users(id)
    );
