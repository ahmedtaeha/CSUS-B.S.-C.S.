create or replace view artist_no_of_sculpture as
select a.aname "artist_name", count(a.aname) "no_of_sculpture"  from sculpture s, artist a, art_object ao  
where s.id_no = ao.id_no  and ao.artist_name = a.aname
GROUP by a.aname
HAVING count(a.aname ) > 2;
