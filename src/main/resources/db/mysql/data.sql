-- 여기에는 insert into 같은 것

INSERT IGNORE INTO admin VALUES (1, 'admin', '1234');

INSERT IGNORE INTO users VALUES (1, 'kh', '1234');
INSERT IGNORE INTO users VALUES (2, 'chong', '4567');


-- member table 테스트값 세팅
INSERT IGNORE INTO member(email, name, pwd, root) VALUES('test@test.com', '박미미', '1111aaaa', '기본회원');
INSERT INTO member(email, name, pwd, root) VALUES('abc@def.com', '김미미', 'asdf1111','기본회원');
INSERT INTO member(email, name, pwd, root) VALUES('a@a.a', '미미', 'qwer1234','기본회원');