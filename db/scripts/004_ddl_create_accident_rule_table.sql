CREATE TABLE accident_rule (
    id serial primary key,
    accisent_id int not null references accidents (id) not null,
    rule_id int not null references rules (id) not null
);