FROM mysql:5.7
MAINTAINER HeJun hejun.web@qq.com

COPY docker-entrypoint-initdb.d /docker-entrypoint-initdb.d

EXPOSE 3306

CMD ["mysqld", \
        "--character-set-server=utf8mb4", \
        "--default-time_zone=+8:00", \
        "--lower_case_table_names=1", \
        "--group_concat_max_len=102400", \
        "--sql_mode=STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION"]