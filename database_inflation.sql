INSERT INTO author (name, surname)
VALUES ('author_one_name', 'author_one_surname'),
       ('author_two_name', 'author_two_surname'),
       ('author_three_name', 'author_three_surname'),
       ('author_four_name', 'author_four_surname'),
       ('author_five_name', 'author_five_surname'),
       ('author_six_name', 'author_six_surname'),
       ('author_seven_name', 'author_seven_surname'),
       ('author_eight_name', 'author_eight_surname'),
       ('author_nine_name', 'author_nine_surname'),
       ('author_ten_name', 'author_ten_surname');

INSERT INTO news (title, short_text, full_text, creation_date, modification_date)
VALUES ('news_item_one_title', 'news_item_one_short_text', 'news_item_one_full_text', '2017-11-12', '2020-10-30'),
       ('news_item_two_title', 'news_item_two_short_text', 'news_item_two_full_text', '2017-11-12', '2019-10-10'),
       ('news_item_three_title', 'news_item_three_short_text', 'news_item_three_full_text', '2017-11-12',
        '2017-10-10'),
       ('news_item_four_title', 'news_item_four_short_text', 'news_item_four_full_text', '2019-11-01', '2015-10-01'),
       ('news_item_five_title', 'news_item_five_short_text', 'news_item_five_full_text', '2017-11-01', '2020-10-10'),
       ('news_item_six_title', 'news_item_six_short_text', 'news_item_six_full_text', '2017-11-02', '2020-10-10'),
       ('news_item_seven_title', 'news_item_seven_short_text', 'news_item_seven_full_text', '2017-10-12',
        '2020-12-10'),
       ('news_item_eight_title', 'news_item_eight_short_text', 'news_item_eight_full_text', '2017-10-12',
        '2020-10-12'),
       ('news_item_nine_title', 'news_item_nine_short_text', 'news_item_nine_full_text', '2017-11-12', '2020-12-11'),
       ('news_item_ten_title', 'news_item_ten__short_text', 'news_item_ten__full_text', '2017-11-12', '2020-10-10');

INSERT INTO tag (name)
VALUES ('tag_one'),
       ('tag_two'),
       ('tag_three'),
       ('tag_four'),
       ('tag_five'),
       ('tag_six'),
       ('tag_seven'),
       ('tag_eight'),
       ('tag_nine'),
       ('tag_ten');

INSERT INTO user_account (name, surname, username, password)
VALUES ('user_one_name', 'user_one_surname', 'user_one_username', 'user_one_password'),
       ('user_two_name', 'user_two_surname', 'user_two_username', 'user_two_password'),
       ('user_three_name', 'user_three_surname', 'user_three_username', 'user_three_password'),
       ('user_four_name', 'user_four_surname', 'user_four_username', 'user_four_password'),
       ('user_five_name', 'user_five_surname', 'user_five_username', 'user_five_password'),
       ('user_six_name', 'user_six_surname', 'user_six_username', 'user_six_password'),
       ('user_seven_name', 'user_seven_surname', 'user_seven_username', 'user_seven_password'),
       ('user_eight_name', 'user_eight_surname', 'user_eight_username', 'user_eight_password'),
       ('user_nine_name', 'user_nine_surname', 'user_nine_username', 'user_nine_password'),
       ('user_ten_name', 'user_ten__surname', 'user_ten__username', 'user_ten_password');

INSERT INTO user_role (name)
VALUES ('user_role_one'),
       ('user_role_two'),
       ('user_role_three'),
       ('user_role_four'),
       ('user_role_five'),
       ('user_role_six'),
       ('user_role_seven'),
       ('user_role_eight'),
       ('user_role_nine'),
       ('user_role_ten');

INSERT INTO news_author (author_id, news_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10);

INSERT INTO news_tag (news_id, tag_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10);

INSERT INTO users_and_roles (role_id, user_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10);
