### USER 테이블
```
CREATE TABLE tb_user (
    id int8 NOT NULL,
    "password" varchar(255) NULL,
    email varchar(255) NULL,
    "name" varchar(255) NULL,
    provider varchar(255) NOT NULL,
    "role" varchar(255) NOT NULL,
    CONSTRAINT tb_user_pk PRIMARY KEY (id)
);

INSERT INTO TB_USER(id, "password", email, name, provider, "role") VALUES (
    1
   ,'$2a$10$E2y9hpP.ymhrJn9DBehwP.h2Ta4w.7JlOCFJKWNQpwjEj.ZR3H2uq'
   , 'bresting@gmail.com'
   , '사용자명'
   , 'LOCAL'
   , 'ROLE_ADMIN'
)

```