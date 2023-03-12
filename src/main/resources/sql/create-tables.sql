create table tests
(
    id bigint primary key not null,
    name text not null,
    points_sum int not null default 0,
    points_count int not null default 0
);

create table questions
(
    id bigint primary key not null,
    text text not null
);

create table test_question
(
    test_id bigint not null references tests(id),
    q_id bigint not null references questions(id),
    number int not null,
    primary key (test_id, q_id)
);

create table answer
(
    q_id bigint not null references questions(id),
    ans_num int not null,
    text text not null,
    rate int not null,
    primary key (q_id, ans_num)
);

create table test_result
(
    id bigint primary key not null,
    test_id bigint not null references tests(id),
    left_bound int not null,
    right_bound int not null,
    description text not null
);

create table comments
(
    id bigint primary key not null,
    test_id bigint not null references tests(id),
    write_date timestamp not null,
    writer text,
    comment text not null
);

create table roles
(
    id bigint primary key not null,
    name varchar(20) not null
);

create table users
(
    id bigint primary key not null,
    name text not null,
    email text unique not null,
    password text not null,
    role_id bigint not null references roles(id)
);