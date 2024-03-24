CREATE TABLE IF NOT EXISTS tb_user_roles (
                                             user_id BIGINT,
                                             role_id BIGINT,
                                             FOREIGN KEY (user_id) REFERENCES tb_users(id),
    FOREIGN KEY (role_id) REFERENCES tb_roles(id),
    PRIMARY KEY (user_id, role_id)
    );