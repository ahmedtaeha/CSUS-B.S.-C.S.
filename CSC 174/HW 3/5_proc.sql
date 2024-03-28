DELIMITER $$
create procedure
paintings_sp (
in exhibition_name varchar(60),
in artist_name varchar(30))
BEGIN
select a.aname , ao.description, p.`style` ,e.ename  from exhibition e , painting p , artist a , shown_at sa , art_object ao 
where e.ename = sa.exhibition_name and ao.artist_name = a.aname and sa.art = p.id_no and p.id_no = ao.id_no and e.ename = exhibition_name and a.aname = artist_name;
end $$
DELIMITER ;

-- execute procedure
call paintings_sp('E1', 'John Doe');
call paintings_sp('E2', 'John Smith');
