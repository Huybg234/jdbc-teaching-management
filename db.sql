create table teacher(
    id number primary key,
    name nvarchar2(50) not null,
    address nvarchar2(100) not null,
    phone_number nvarchar2(10) not null,
    teacher_level nvarchar2(50) not null
);

create table subject(
    id number primary key,
    name nvarchar2(50) not null,
    total_lesson number not null,
    theory_lesson number not null,
    theory_expense number not null
);


create table teaching_time_sheet(
    teacher_id number not null,
    subject_id number not null,
    class_number number not null,
    constraint teaching_time_sheet_PK primary key (teacher_id, subject_id)
);
