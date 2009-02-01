create table commute(id integer primary key autoincrement, name text);

create table commutepoint(id integer primary key autoincrement,
  name text,
  commute_id integer not null constraint fk_commute_id references commute(id));

create table commutepoint_forwardpoints(commutepoint_id1 int not null, commutepoint_id2 int not null);
create table commutepoint_reversepoints(commutepoint_id1 int not null, commutepoint_id2 int not null);

create table commutepoint_instance(commutepoint_id int not null, timestamp int);