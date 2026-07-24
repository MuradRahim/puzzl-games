DELETE FROM answers;
DELETE FROM questions;
DELETE FROM topics;

INSERT INTO topics (slug, title, description)
VALUES ('sql', 'Как хорошо ты знаешь SQL?',
        'Проверьте знание основ SQL: запросы SELECT, JOIN, агрегатные функции и работа с данными.'),
       ('java', 'Java для начинающих',
        'Базовые вопросы по синтаксису Java, ООП, коллекциям и исключениям.'),
       ('spring', 'Spring Boot: основы',
        'Тест на понимание Spring Boot, DI, REST и конфигурации приложений.');

INSERT INTO questions (topic_id, question_text, sort_order)
SELECT t.id, q.text, q.sort_order
FROM topics t
         JOIN (VALUES ('sql', 'Какой оператор используется для выборки данных из таблицы?', 1),
                      ('sql', 'Какой тип JOIN возвращает только совпадающие строки из обеих таблиц?', 2),
                      ('sql', 'Какая функция подсчитывает количество строк в результате запроса?', 3),
                      ('sql', 'Какой оператор фильтрует строки после GROUP BY?', 4),
                      ('sql', 'Как удалить все строки из таблицы, сохранив её структуру?', 5)) AS q(slug, text, sort_order)
              ON t.slug = q.slug;

INSERT INTO questions (topic_id, question_text, sort_order)
SELECT t.id, q.text, q.sort_order
FROM topics t
         JOIN (VALUES ('java', 'Какой модификатор доступа делает поле доступным только внутри класса?', 1),
                      ('java', 'Какой интерфейс реализуют все коллекции, поддерживающие итерацию?', 2),
                      ('java', 'Какой ключевое слово используется для наследования класса?', 3),
                      ('java', 'Какой тип используется для хранения целых чисел без дробной части?', 4),
                      ('java', 'Какой блок обрабатывает исключения в Java?', 5)) AS q(slug, text, sort_order)
              ON t.slug = q.slug;

INSERT INTO questions (topic_id, question_text, sort_order)
SELECT t.id, q.text, q.sort_order
FROM topics t
         JOIN (VALUES ('spring', 'Какая аннотация помечает главный класс Spring Boot приложения?', 1),
                      ('spring', 'Какой стартер добавляет поддержку REST API?', 2),
                      ('spring', 'Какая аннотация внедряет зависимость через конструктор или поле?', 3),
                      ('spring', 'Где обычно хранят настройки datasource в Spring Boot?', 4),
                      ('spring', 'Какая аннотация обрабатывает HTTP GET запросы?', 5)) AS q(slug, text, sort_order)
              ON t.slug = q.slug;

INSERT INTO answers (question_id, answer_text, is_correct)
SELECT q.id, a.text, a.is_correct
FROM questions q
         JOIN topics t ON t.id = q.topic_id
         JOIN (VALUES ('sql', 1, 'SELECT', TRUE),
                      ('sql', 1, 'INSERT', FALSE),
                      ('sql', 1, 'UPDATE', FALSE),
                      ('sql', 1, 'DELETE', FALSE),
                      ('sql', 2, 'INNER JOIN', TRUE),
                      ('sql', 2, 'LEFT JOIN', FALSE),
                      ('sql', 2, 'CROSS JOIN', FALSE),
                      ('sql', 2, 'FULL OUTER JOIN', FALSE),
                      ('sql', 3, 'COUNT()', TRUE),
                      ('sql', 3, 'SUM()', FALSE),
                      ('sql', 3, 'AVG()', FALSE),
                      ('sql', 3, 'MAX()', FALSE),
                      ('sql', 4, 'HAVING', TRUE),
                      ('sql', 4, 'WHERE', FALSE),
                      ('sql', 4, 'ORDER BY', FALSE),
                      ('sql', 4, 'LIMIT', FALSE),
                      ('sql', 5, 'TRUNCATE', TRUE),
                      ('sql', 5, 'DROP', FALSE),
                      ('sql', 5, 'ALTER', FALSE),
                      ('sql', 5, 'REVOKE', FALSE)) AS a(slug, sort_order, text, is_correct)
              ON t.slug = a.slug AND q.sort_order = a.sort_order;

INSERT INTO answers (question_id, answer_text, is_correct)
SELECT q.id, a.text, a.is_correct
FROM questions q
         JOIN topics t ON t.id = q.topic_id
         JOIN (VALUES ('java', 1, 'private', TRUE),
                      ('java', 1, 'public', FALSE),
                      ('java', 1, 'protected', FALSE),
                      ('java', 1, 'default', FALSE),
                      ('java', 2, 'Iterable', TRUE),
                      ('java', 2, 'Serializable', FALSE),
                      ('java', 2, 'Comparable', FALSE),
                      ('java', 2, 'Cloneable', FALSE),
                      ('java', 3, 'extends', TRUE),
                      ('java', 3, 'implements', FALSE),
                      ('java', 3, 'inherits', FALSE),
                      ('java', 3, 'super', FALSE),
                      ('java', 4, 'int', TRUE),
                      ('java', 4, 'float', FALSE),
                      ('java', 4, 'double', FALSE),
                      ('java', 4, 'String', FALSE),
                      ('java', 5, 'catch', TRUE),
                      ('java', 5, 'finally', FALSE),
                      ('java', 5, 'throw', FALSE),
                      ('java', 5, 'throws', FALSE)) AS a(slug, sort_order, text, is_correct)
              ON t.slug = a.slug AND q.sort_order = a.sort_order;

INSERT INTO answers (question_id, answer_text, is_correct)
SELECT q.id, a.text, a.is_correct
FROM questions q
         JOIN topics t ON t.id = q.topic_id
         JOIN (VALUES ('spring', 1, '@SpringBootApplication', TRUE),
                      ('spring', 1, '@Configuration', FALSE),
                      ('spring', 1, '@ComponentScan', FALSE),
                      ('spring', 1, '@EnableAutoConfiguration', FALSE),
                      ('spring', 2, 'spring-boot-starter-web', TRUE),
                      ('spring', 2, 'spring-boot-starter-jdbc', FALSE),
                      ('spring', 2, 'spring-boot-starter-test', FALSE),
                      ('spring', 2, 'spring-boot-starter-aop', FALSE),
                      ('spring', 3, '@Autowired', TRUE),
                      ('spring', 3, '@Bean', FALSE),
                      ('spring', 3, '@Value', FALSE),
                      ('spring', 3, '@Qualifier', FALSE),
                      ('spring', 4, 'application.yaml', TRUE),
                      ('spring', 4, 'pom.xml', FALSE),
                      ('spring', 4, 'schema.sql', FALSE),
                      ('spring', 4, 'logback.xml', FALSE),
                      ('spring', 5, '@GetMapping', TRUE),
                      ('spring', 5, '@PostMapping', FALSE),
                      ('spring', 5, '@RequestMapping', FALSE),
                      ('spring', 5, '@ResponseBody', FALSE)) AS a(slug, sort_order, text, is_correct)
              ON t.slug = a.slug AND q.sort_order = a.sort_order;
