SELECT *
FROM "student";

SELECT *
from student
where age > 20
  and age < 30;

SELECT name
from student;

SELECT *
from student
where name like '%о%';

SELECT *
from student
where age < id;

SELECT *
from student
order by age
