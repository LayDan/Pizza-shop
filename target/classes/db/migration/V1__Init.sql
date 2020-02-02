create table user_profile (
    id bigint generated by default as identity,
    active boolean,
    bonus integer,
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255),
    username varchar(255),
    activation_code varchar(255),
    mail varchar(255),
    primary key (id)
);
create table user_profile_roles (
    user_profile_id bigint not null,
    roles varchar(255),
    foreign key (user_profile_id) references user_profile(id)
);
create table type_product(
    id bigint generated by default as identity,
    type varchar(255),
    primary key(id)
);

create table product (
    id bigint generated by default as identity,
    code bigint,
    image_path varchar(255),
    description varchar(255),
    price Double,
    stock Double,
    promotional_item boolean,
    name varchar(255),
    type varchar(255),
    primary key (id),
    foreign key (type) references type_product(type)
);

create table basket(
    id bigint generated by default as identity,
    key varchar(255),
    quantity integer,
    product bigint,
    user bigint,
    primary key (id),
    foreign key (product) references product(id),
    foreign key (user) references user_profile(id)
);

create table user_profile_basket(
    user_profile_id bigint not null,
    basket_id bigint not null,
    foreign key (basket_id) references basket(id),
    foreign key (user_profile_id) references user_profile(id)
);

create table product_price_from_size (
    product_id bigint not null,
    price_from_size double,
    price_from_size_key varchar(255),
    primary key (product_id, price_from_size_key),
    foreign key (product_id) references product(id)
);
