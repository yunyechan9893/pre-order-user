
USE msa;

CREATE TABLE `member` (
      `id`	        bigint	NOT NULL auto_increment PRIMARY KEY,
      `user_id`	    varchar(255)	NOT NULL,
      `password`	  varchar(255)	NOT NULL,
      `email`	      varchar(255)	NOT NULL,
      `phone`	      varchar(255)	NOT NULL,
      `address`	    varchar(255)	NOT NULL,
      `created_at`	datetime	NOT NULL DEFAULT NOW(),
      `modified_at`	datetime	NOT NULL DEFAULT NOW()
);