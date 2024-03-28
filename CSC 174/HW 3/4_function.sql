DELIMITER $$
CREATE FUNCTION
No_of_painting_exhibited(exhibition_name varchar(60)) returns int
DETERMINISTIC
BEGIN
declare no_of_paintings int;
select count(sa.art) into no_of_paintings from shown_at sa , exhibition e, painting p 
where e.ename = sa.exhibition_name and e.ename=exhibition_name and sa.art = p.id_no 
GROUP by e.ename;
return (no_of_paintings);
end $$
DELIMITER ;

-- execute function
select e.ename, e.start_date, e.end_date, No_of_painting_exhibited("E1") from exhibition e
where e.ename = "E1";
select e.ename, e.start_date, e.end_date, No_of_painting_exhibited("E2") from exhibition e
where e.ename = "E2";
