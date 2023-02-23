INSERT INTO tests VALUES (DEFAULT, 'Тест дня: проверь, хорошо ли ты разбираешься в подростковом сленге',0, 0);

INSERT INTO questions VALUES(DEFAULT, 'Начнем с простого. Ты случайно услышала, что твой ребенок задонатил стримеру все свои «карманные» деньги. Что он вообще сделал?');
INSERT INTO answer VALUES(1, 1, 'Скорее всего, вложил деньги в прибыльное дело', 0);
INSERT INTO answer VALUES(1, 2, 'Перевел все свои деньги какому-то блогеру в сети', 1);
INSERT INTO answer VALUES(1, 3, 'Купил пончики к чаю', 0);

INSERT INTO questions VALUES(DEFAULT, 'Вы все вместе выбираете интересное кино для вечернего семейного просмотра. Отпрыск настойчиво просит «криповый фильм». Что он, собственно, имеет в виду?');
INSERT INTO answer VALUES(2, 1, 'Кажется, в программе вечера — ужастик', 1);
INSERT INTO answer VALUES(2, 2, 'Ура! Смотрим трендовое кино про криптовалюту!', 0);
INSERT INTO answer VALUES(2, 3, 'Наверняка, это блокбастер с брутальным главным героем по имени Крип', 0);

INSERT INTO questions values (DEFAULT, 'Ребенок с придыханием говорит, что у него появился краш. Начинать волноваться или само пройдет?');
INSERT INTO answer VALUES(3, 1, 'Может, пройдет, а может, на всю жизнь... Влюбился он!', 1);
INSERT INTO answer VALUES(3, 2, 'Он что, куда-то врезался? У него шишка или синяк?', 0);
INSERT INTO answer VALUES(3, 3, 'Купил краску для волос и хочет фиолетовую прическу...', 0);

INSERT INTO questions values (DEFAULT, 'Ребенок сердится и обиженно говорит, что поссорился с приятелями, потому что они постоянно флексят. Что он имеет в виду?');
INSERT INTO answer VALUES(4, 1, 'Фиксики, флексики... Видимо, те ребята смотрят мультики, из которых мой уже вырос', 0);
INSERT INTO answer VALUES(4, 2, 'Да они попросту выпендриваются!', 1);
INSERT INTO answer VALUES(4, 3, 'Эти бывшие друзья распускают слухи у него за спиной', 0);

INSERT INTO questions values (DEFAULT, 'Изи Пизи! Бодро сообщило чадо на просьбу прибраться в комнате. И как это понимать... Он вообще собирается наводить порядок?');
INSERT INTO answer VALUES(5, 1, 'Звучит оскорбительно. Будем разбираться вместе с отцом!', 0);
INSERT INTO answer VALUES(5, 2, 'Конечно! Он как раз собирается легко и непринужденно навести порядок', 1);
INSERT INTO answer VALUES(5, 3, 'Что-то знакомое, слышала в рекламе... Но причем тут вообще чипсы?', 0);

INSERT INTO questions values (DEFAULT, '«Го, в зоопарк!» — говорит ребенок. Что нужно сделать с зоопарком, простите?');
INSERT INTO answer VALUES(6, 1, 'Найти зоопарк на карте и прочитать вслух историю его открытия', 0);
INSERT INTO answer VALUES(6, 2, 'Пойти туда! Можно всем вместе', 1);
INSERT INTO answer VALUES(6, 3, 'Купить настольную игру с зоопарком', 0);

INSERT INTO questions values (DEFAULT, '«Кринжовый прикид!» — громко заявил подросток на предложение примерить обновки. Чем тебе это грозит?');
INSERT INTO answer VALUES(7, 1, 'Выглядит стремно. Он это не наденет', 1);
INSERT INTO answer VALUES(7, 2, 'Выглядит круто. Он хочет еще один такой комплект на вырост', 0);
INSERT INTO answer VALUES(7, 3, 'Кажется, это похоже на наряд его кумира!', 0);

INSERT INTO questions values (DEFAULT, '«Чекни мои фотки!» — требует сын или дочь. Ты в целом не против. Только что надо сделать?');
INSERT INTO answer VALUES(8, 1, 'Посмотреть новые фото ребенка в соцсетях (не забудь про лайкосики!)', 1);
INSERT INTO answer VALUES(8, 2, 'Оплатить, наконец, школьного фотографа, а то фото не отдают', 0);
INSERT INTO answer VALUES(8, 3, 'Распечатать любимые фото чада для семейного фотоальбома', 0);

INSERT INTO questions values (DEFAULT, 'Ребенок собрался «чилить» — прямо так и заявил! А чего тебе ждать от подобного заявления?');
INSERT INTO answer VALUES(9, 1, 'Ура! Он приготовит наше любимое блюдо из тайской кухни — остренькое, с перчиком', 0);
INSERT INTO answer VALUES(9, 2, 'Ничего страшного, пусть отдохнет крошка!', 1);
INSERT INTO answer VALUES(9, 3, 'Что бы это ни значило, надо проследить, мало ли что...', 0);

INSERT INTO questions values (DEFAULT, 'Ребенок, преданно глядя в глаза, говорит, что желает устроить на Новый год крутейший анбоксинг. Караул! Где такое дают?');
INSERT INTO answer VALUES(10, 1, 'Билеты на бокс? Продаются в кассах спортивного комплекса...', 0);
INSERT INTO answer VALUES(10, 2, 'Распаковка подарков на камеру, чтобы выложить затем в соцсети — отличное занятие на каникулы! С родителей — красиво упакованный презент', 1);
INSERT INTO answer VALUES(10, 3, 'В наше время вечеринки назывались как-то понятнее...', 0);

INSERT INTO test_question values (1, 1, 1);
INSERT INTO test_question values (1, 2, 2);
INSERT INTO test_question values (1, 3, 3);
INSERT INTO test_question values (1, 4, 4);
INSERT INTO test_question values (1, 5, 5);
INSERT INTO test_question values (1, 6, 6);
INSERT INTO test_question values (1, 7, 7);
INSERT INTO test_question values (1, 8, 8);
INSERT INTO test_question values (1, 9, 9);
INSERT INTO test_question values (1, 10, 10);

INSERT INTO test_result values (DEFAULT, 1, 6, 10, 'Ты просто топчик!');
INSERT INTO test_result values (DEFAULT, 1, 0, 5, 'Ты — отсталый шнурок!');

INSERT INTO comments values (DEFAULT, 1, now(), 'Булат Хафизов', 'Угарный тест');
INSERT INTO comments values (DEFAULT, 1, now(), 'Иван Жамков', 'Орал в голосину');