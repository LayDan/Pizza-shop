insert into user_profile (id,active, bonus ,first_name,last_name,password,username) values (1,1,0,'a','a','$2a$08$giBOFT9QDoGaFdag43Mgf.HSWCSoS5WRXxTjLLi0glMnqNUnaCUFS', 'admin');
insert into user_profile_roles (user_profile_id, roles ) values (1,'USER');
insert into user_profile_roles (user_profile_id, roles ) values (1,'ADMIN');

insert into user_profile (id,active, bonus ,first_name,last_name,password,username, mail) values (2,1,0,'a','a','$2a$08$giBOFT9QDoGaFdag43Mgf.HSWCSoS5WRXxTjLLi0glMnqNUnaCUFS', 'u', 'k6vgc@vmani.com');
insert into user_profile_roles (user_profile_id, roles ) values (2,'ADMIN');
