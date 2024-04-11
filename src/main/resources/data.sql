INSERT INTO board (id) VALUES (1);
INSERT INTO board (id) VALUES (2);
INSERT INTO board (id) VALUES (3);
INSERT INTO board (id) VALUES (4);
INSERT INTO board (id) VALUES (5);
INSERT INTO board (id) VALUES (6);
INSERT INTO board (id) VALUES (7);
INSERT INTO board (id) VALUES (8);
INSERT INTO board (id) VALUES (9);

INSERT INTO category (id,name, parent_id, root_id) VALUES ( 1, '남자', null , 1);

INSERT INTO category (id,name, parent_id, root_id) VALUES ( 2, '엑소', 1 , 1);

INSERT INTO category (id,name, parent_id, root_id) VALUES ( 3, '방탄소년단', 1 , 1);

INSERT INTO category (id,name, parent_id, board_id, root_id) VALUES ( 4, '공지사항', 2 , 1,1);
INSERT INTO category (id,name, parent_id, board_id, root_id) VALUES ( 5, '챈', 2 , 2, 1);
INSERT INTO category (id,name, parent_id, board_id, root_id) VALUES ( 6, '백현', 2, 3, 1);
INSERT INTO category (id,name, parent_id, board_id, root_id) VALUES ( 7, '시우민', 2 , 4, 1);

INSERT INTO category (id,name, parent_id, board_id, root_id )VALUES ( 8, '공지사항', 3 , 5, 1);
INSERT INTO category (id,name, parent_id, board_id, root_id )VALUES ( 9, '익명게시판', 3, 6 ,1);
INSERT INTO category (id,name, parent_id, board_id, root_id ) VALUES ( 10, '뷔', 3 , 7, 1);


INSERT INTO category (id,name, parent_id, root_id) VALUES ( 11, '여자', null , 11);

INSERT INTO category (id,name, parent_id, root_id) VALUES ( 12, '블랙핑크', 11 , 11);

INSERT INTO category (id,name, parent_id, board_id , root_id) VALUES ( 13, '공지사항', 12, 8 , 11);
INSERT INTO category (id,name, parent_id, board_id , root_id) VALUES ( 14, '익명게시판', 12 , 6, 11);
INSERT INTO category (id,name, parent_id, board_id , root_id)VALUES ( 15, '로제', 12 , 9, 11);




