insert into users (username, password, role) values
('admin', 'admin123', 'ADMIN'),
('lenchik', 'qwerty', 'USER'),
('ivan', '12345', 'USER'),
('masha', 'pass', 'USER'),
('petya', 'p@ssword', 'USER'),
('katya', 'kitty', 'USER'),
('dima', '123', 'USER'),
('sveta', '321', 'USER'),
('oleg', 'pass1', 'USER'),
('nina', 'pass2', 'USER');

insert into profiles (user_id, firstname, lastname, birth_date) values
(1, 'Super', 'Admin', '1990-01-01'),
(2, 'Лёнчик', 'Крутой', '2000-05-10'),
(3, 'Иван', 'Иванов', '1995-03-20'),
(4, 'Мария', 'Петрова', '1998-07-12'),
(5, 'Пётр', 'Сидоров', '1988-02-17'),
(6, 'Екатерина', 'Смирнова', '1999-09-09'),
(7, 'Дмитрий', 'Орлов', '1993-12-01'),
(8, 'Светлана', 'Кузнецова', '1997-06-22'),
(9, 'Олег', 'Попов', '1985-11-15'),
(10, 'Нина', 'Васильева', '2001-04-30');

insert into tasks (user_id, title, description, status, difficulty) values
(2, 'Сделать CRUD', 'Реализовать CRUD для юзеров', 'IN_PROGRESS', 'MEDIUM'),
(2, 'Покрыть тестами', 'Написать юнит и интеграционные тесты', 'TODO', 'HARD'),
(3, 'Выучить SQL', 'Повторить SELECT, JOIN, GROUP BY', 'DONE', 'EASY'),
(3, 'Покрыть сервис логами', 'Внедрить логирование через AOP', 'IN_PROGRESS', 'MEDIUM'),
(4, 'Сходить в магазин', 'Купить продукты', 'TODO', 'EASY'),
(5, 'Починить велосипед', 'Смазать цепь и подкачать колёса', 'TODO', 'MEDIUM'),
(6, 'Подготовиться к собесу', 'Spring Core, JPA, многопоточность', 'IN_PROGRESS', 'HARD'),
(7, 'Закончить проект', 'Финализировать pet-project', 'TODO', 'HARD'),
(8, 'Сделать презентацию', 'Подготовить слайды для митинга', 'DONE', 'MEDIUM'),
(9, 'Сходить в спортзал', 'Тренировка в 7 вечера', 'TODO', 'EASY'),
(10, 'Прочитать книгу', 'Дочитать Clean Code', 'IN_PROGRESS', 'MEDIUM');

insert into tags (name) values
('java'),
('spring'),
('jpa'),
('sql'),
('docker'),
('testing'),
('frontend'),
('backend'),
('urgent'),
('low-priority');

insert into task_tag (task_id, tag_id) values
(1, 1), (1, 2), (1, 3),
(2, 6), (2, 1),
(3, 4),
(4, 2), (4, 5),
(5, 9),
(6, 1), (6, 2), (6, 3),
(7, 7), (7, 8),
(8, 9), (8, 10),
(9, 9),
(10, 1), (10, 6);