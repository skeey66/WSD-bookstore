-- Seed data: 200+ rows distributed across tables (users/books/orders/reviews/cart/wishlist)

/* Users (includes one ADMIN and a known USER for Postman) */
INSERT INTO `user` (`password_hash`,`name`,`email`,`gender`,`birth_date`,`address`,`phone_number`,`created_at`,`updated_at`,`deleted_at`,`role`) VALUES
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','User One','user1@example.com','MALE','1998-05-10','Jeonju, Korea','010-1111-0001',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Admin One','admin1@example.com','FEMALE','1995-03-03','Seoul, Korea','010-1111-0002',NOW(),NOW(),NULL,'ADMIN'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 001','seed_user_001@example.com','MALE','1990-05-04','Korea, Address 001','010-9000-0001',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 002','seed_user_002@example.com','FEMALE','1990-09-04','Korea, Address 002','010-9000-0002',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 003','seed_user_003@example.com','MALE','1991-01-05','Korea, Address 003','010-9000-0003',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 004','seed_user_004@example.com','FEMALE','1991-05-08','Korea, Address 004','010-9000-0004',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 005','seed_user_005@example.com','MALE','1991-09-08','Korea, Address 005','010-9000-0005',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 006','seed_user_006@example.com','FEMALE','1992-01-09','Korea, Address 006','010-9000-0006',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 007','seed_user_007@example.com','MALE','1992-05-11','Korea, Address 007','010-9000-0007',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 008','seed_user_008@example.com','FEMALE','1992-09-11','Korea, Address 008','010-9000-0008',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 009','seed_user_009@example.com','MALE','1993-01-12','Korea, Address 009','010-9000-0009',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 010','seed_user_010@example.com','FEMALE','1993-05-15','Korea, Address 010','010-9000-0010',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 011','seed_user_011@example.com','MALE','1993-09-15','Korea, Address 011','010-9000-0011',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 012','seed_user_012@example.com','FEMALE','1994-01-16','Korea, Address 012','010-9000-0012',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 013','seed_user_013@example.com','MALE','1994-05-19','Korea, Address 013','010-9000-0013',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 014','seed_user_014@example.com','FEMALE','1994-09-19','Korea, Address 014','010-9000-0014',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 015','seed_user_015@example.com','MALE','1995-01-20','Korea, Address 015','010-9000-0015',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 016','seed_user_016@example.com','FEMALE','1995-05-23','Korea, Address 016','010-9000-0016',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 017','seed_user_017@example.com','MALE','1995-09-23','Korea, Address 017','010-9000-0017',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 018','seed_user_018@example.com','FEMALE','1996-01-24','Korea, Address 018','010-9000-0018',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 019','seed_user_019@example.com','MALE','1996-05-26','Korea, Address 019','010-9000-0019',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 020','seed_user_020@example.com','FEMALE','1996-09-26','Korea, Address 020','010-9000-0020',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 021','seed_user_021@example.com','MALE','1997-01-27','Korea, Address 021','010-9000-0021',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 022','seed_user_022@example.com','FEMALE','1997-05-30','Korea, Address 022','010-9000-0022',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 023','seed_user_023@example.com','MALE','1997-09-30','Korea, Address 023','010-9000-0023',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 024','seed_user_024@example.com','FEMALE','1998-01-31','Korea, Address 024','010-9000-0024',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 025','seed_user_025@example.com','MALE','1998-06-03','Korea, Address 025','010-9000-0025',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 026','seed_user_026@example.com','FEMALE','1998-10-04','Korea, Address 026','010-9000-0026',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 027','seed_user_027@example.com','MALE','1999-02-04','Korea, Address 027','010-9000-0027',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 028','seed_user_028@example.com','FEMALE','1999-06-07','Korea, Address 028','010-9000-0028',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 029','seed_user_029@example.com','MALE','1999-10-08','Korea, Address 029','010-9000-0029',NOW(),NOW(),NULL,'USER'),
  ('$2b$10$Rlh1QO/V292LKmxtl1eex.ch/Do/bMeKSsm62gnfWwiUlSGhXNHfC','Seed User 030','seed_user_030@example.com','FEMALE','2000-02-08','Korea, Address 030','010-9000-0030',NOW(),NOW(),NULL,'USER')
ON DUPLICATE KEY UPDATE `updated_at`=NOW(), `role`=VALUES(`role`), `password_hash`=VALUES(`password_hash`);

/* Books */
INSERT INTO `book` (`isbn`,`publisher`,`title`,`summary`,`price`,`publication_year`,`created_at`,`updated_at`) VALUES
  ('978-1-4028-0001-1','BlueSky Press','Seed Book Title 001','Seed summary for book 001. Generated for assignment data seeding.',26.39,2001,NOW(),NOW()),
  ('978-1-4028-0002-2','O''Reilly','Seed Book Title 002','Seed summary for book 002. Generated for assignment data seeding.',170.49,2002,NOW(),NOW()),
  ('978-1-4028-0003-3','Packt','Seed Book Title 003','Seed summary for book 003. Generated for assignment data seeding.',101.44,2003,NOW(),NOW()),
  ('978-1-4028-0004-4','BlueSky Press','Seed Book Title 004','Seed summary for book 004. Generated for assignment data seeding.',367.41,2004,NOW(),NOW()),
  ('978-1-4028-0005-5','BlueSky Press','Seed Book Title 005','Seed summary for book 005. Generated for assignment data seeding.',396.98,2005,NOW(),NOW()),
  ('978-1-4028-0006-6','JPub','Seed Book Title 006','Seed summary for book 006. Generated for assignment data seeding.',30.82,2006,NOW(),NOW()),
  ('978-1-4028-0007-7','Acme Books','Seed Book Title 007','Seed summary for book 007. Generated for assignment data seeding.',71.4,2007,NOW(),NOW()),
  ('978-1-4028-0008-8','Packt','Seed Book Title 008','Seed summary for book 008. Generated for assignment data seeding.',162.47,2008,NOW(),NOW()),
  ('978-1-4028-0009-9','Manning','Seed Book Title 009','Seed summary for book 009. Generated for assignment data seeding.',404.53,2009,NOW(),NOW()),
  ('978-1-4028-0010-0','Acme Books','Seed Book Title 010','Seed summary for book 010. Generated for assignment data seeding.',377.81,2010,NOW(),NOW()),
  ('978-1-4028-0011-1','Packt','Seed Book Title 011','Seed summary for book 011. Generated for assignment data seeding.',435.9,2011,NOW(),NOW()),
  ('978-1-4028-0012-2','Manning','Seed Book Title 012','Seed summary for book 012. Generated for assignment data seeding.',284.93,2012,NOW(),NOW()),
  ('978-1-4028-0013-3','Packt','Seed Book Title 013','Seed summary for book 013. Generated for assignment data seeding.',304.39,2013,NOW(),NOW()),
  ('978-1-4028-0014-4','NoStarch','Seed Book Title 014','Seed summary for book 014. Generated for assignment data seeding.',192.31,2014,NOW(),NOW()),
  ('978-1-4028-0015-5','Acme Books','Seed Book Title 015','Seed summary for book 015. Generated for assignment data seeding.',114.63,2015,NOW(),NOW()),
  ('978-1-4028-0016-6','JPub','Seed Book Title 016','Seed summary for book 016. Generated for assignment data seeding.',232.98,2016,NOW(),NOW()),
  ('978-1-4028-0017-7','O''Reilly','Seed Book Title 017','Seed summary for book 017. Generated for assignment data seeding.',111.89,2017,NOW(),NOW()),
  ('978-1-4028-0018-8','Packt','Seed Book Title 018','Seed summary for book 018. Generated for assignment data seeding.',230.59,2018,NOW(),NOW()),
  ('978-1-4028-0019-9','BlueSky Press','Seed Book Title 019','Seed summary for book 019. Generated for assignment data seeding.',70.78,2019,NOW(),NOW()),
  ('978-1-4028-0020-0','JPub','Seed Book Title 020','Seed summary for book 020. Generated for assignment data seeding.',73.38,2020,NOW(),NOW()),
  ('978-1-4028-0021-1','Pearson','Seed Book Title 021','Seed summary for book 021. Generated for assignment data seeding.',235.41,2021,NOW(),NOW()),
  ('978-1-4028-0022-2','NoStarch','Seed Book Title 022','Seed summary for book 022. Generated for assignment data seeding.',183.35,2022,NOW(),NOW()),
  ('978-1-4028-0023-3','Acme Books','Seed Book Title 023','Seed summary for book 023. Generated for assignment data seeding.',311.08,2023,NOW(),NOW()),
  ('978-1-4028-0024-4','Manning','Seed Book Title 024','Seed summary for book 024. Generated for assignment data seeding.',91.8,2024,NOW(),NOW()),
  ('978-1-4028-0025-5','JPub','Seed Book Title 025','Seed summary for book 025. Generated for assignment data seeding.',61.64,2025,NOW(),NOW()),
  ('978-1-4028-0026-6','Manning','Seed Book Title 026','Seed summary for book 026. Generated for assignment data seeding.',202.13,2000,NOW(),NOW()),
  ('978-1-4028-0027-7','NoStarch','Seed Book Title 027','Seed summary for book 027. Generated for assignment data seeding.',247,2001,NOW(),NOW()),
  ('978-1-4028-0028-8','NoStarch','Seed Book Title 028','Seed summary for book 028. Generated for assignment data seeding.',136.01,2002,NOW(),NOW()),
  ('978-1-4028-0029-9','BlueSky Press','Seed Book Title 029','Seed summary for book 029. Generated for assignment data seeding.',40.03,2003,NOW(),NOW()),
  ('978-1-4028-0030-0','Packt','Seed Book Title 030','Seed summary for book 030. Generated for assignment data seeding.',199.65,2004,NOW(),NOW()),
  ('978-1-4028-0031-1','BlueSky Press','Seed Book Title 031','Seed summary for book 031. Generated for assignment data seeding.',162.56,2005,NOW(),NOW()),
  ('978-1-4028-0032-2','BlueSky Press','Seed Book Title 032','Seed summary for book 032. Generated for assignment data seeding.',259.11,2006,NOW(),NOW()),
  ('978-1-4028-0033-3','O''Reilly','Seed Book Title 033','Seed summary for book 033. Generated for assignment data seeding.',307.14,2007,NOW(),NOW()),
  ('978-1-4028-0034-4','Pearson','Seed Book Title 034','Seed summary for book 034. Generated for assignment data seeding.',116.59,2008,NOW(),NOW()),
  ('978-1-4028-0035-5','Pearson','Seed Book Title 035','Seed summary for book 035. Generated for assignment data seeding.',242.83,2009,NOW(),NOW()),
  ('978-1-4028-0036-6','Packt','Seed Book Title 036','Seed summary for book 036. Generated for assignment data seeding.',449.2,2010,NOW(),NOW()),
  ('978-1-4028-0037-7','O''Reilly','Seed Book Title 037','Seed summary for book 037. Generated for assignment data seeding.',434.69,2011,NOW(),NOW()),
  ('978-1-4028-0038-8','BlueSky Press','Seed Book Title 038','Seed summary for book 038. Generated for assignment data seeding.',409.2,2012,NOW(),NOW()),
  ('978-1-4028-0039-9','Hanbit Media','Seed Book Title 039','Seed summary for book 039. Generated for assignment data seeding.',360.05,2013,NOW(),NOW()),
  ('978-1-4028-0040-0','Packt','Seed Book Title 040','Seed summary for book 040. Generated for assignment data seeding.',117.08,2014,NOW(),NOW()),
  ('978-1-4028-0041-1','Springer','Seed Book Title 041','Seed summary for book 041. Generated for assignment data seeding.',258.67,2015,NOW(),NOW()),
  ('978-1-4028-0042-2','O''Reilly','Seed Book Title 042','Seed summary for book 042. Generated for assignment data seeding.',429.43,2016,NOW(),NOW()),
  ('978-1-4028-0043-3','Manning','Seed Book Title 043','Seed summary for book 043. Generated for assignment data seeding.',153.92,2017,NOW(),NOW()),
  ('978-1-4028-0044-4','Pearson','Seed Book Title 044','Seed summary for book 044. Generated for assignment data seeding.',46.65,2018,NOW(),NOW()),
  ('978-1-4028-0045-5','Packt','Seed Book Title 045','Seed summary for book 045. Generated for assignment data seeding.',31.03,2019,NOW(),NOW()),
  ('978-1-4028-0046-6','Pearson','Seed Book Title 046','Seed summary for book 046. Generated for assignment data seeding.',272.9,2020,NOW(),NOW()),
  ('978-1-4028-0047-7','O''Reilly','Seed Book Title 047','Seed summary for book 047. Generated for assignment data seeding.',53.37,2021,NOW(),NOW()),
  ('978-1-4028-0048-8','Packt','Seed Book Title 048','Seed summary for book 048. Generated for assignment data seeding.',381.7,2022,NOW(),NOW()),
  ('978-1-4028-0049-9','Pearson','Seed Book Title 049','Seed summary for book 049. Generated for assignment data seeding.',149.34,2023,NOW(),NOW()),
  ('978-1-4028-0050-0','Springer','Seed Book Title 050','Seed summary for book 050. Generated for assignment data seeding.',269.28,2024,NOW(),NOW()),
  ('978-1-4028-0051-1','Springer','Seed Book Title 051','Seed summary for book 051. Generated for assignment data seeding.',103.63,2025,NOW(),NOW()),
  ('978-1-4028-0052-2','O''Reilly','Seed Book Title 052','Seed summary for book 052. Generated for assignment data seeding.',101.5,2000,NOW(),NOW()),
  ('978-1-4028-0053-3','Packt','Seed Book Title 053','Seed summary for book 053. Generated for assignment data seeding.',377.89,2001,NOW(),NOW()),
  ('978-1-4028-0054-4','Manning','Seed Book Title 054','Seed summary for book 054. Generated for assignment data seeding.',182.19,2002,NOW(),NOW()),
  ('978-1-4028-0055-5','NoStarch','Seed Book Title 055','Seed summary for book 055. Generated for assignment data seeding.',290.77,2003,NOW(),NOW()),
  ('978-1-4028-0056-6','NoStarch','Seed Book Title 056','Seed summary for book 056. Generated for assignment data seeding.',271.75,2004,NOW(),NOW()),
  ('978-1-4028-0057-7','Pearson','Seed Book Title 057','Seed summary for book 057. Generated for assignment data seeding.',153.73,2005,NOW(),NOW()),
  ('978-1-4028-0058-8','Hanbit Media','Seed Book Title 058','Seed summary for book 058. Generated for assignment data seeding.',343.92,2006,NOW(),NOW()),
  ('978-1-4028-0059-9','Springer','Seed Book Title 059','Seed summary for book 059. Generated for assignment data seeding.',69.57,2007,NOW(),NOW()),
  ('978-1-4028-0060-0','Acme Books','Seed Book Title 060','Seed summary for book 060. Generated for assignment data seeding.',81.85,2008,NOW(),NOW()),
  ('978-1-4028-0061-1','Hanbit Media','Seed Book Title 061','Seed summary for book 061. Generated for assignment data seeding.',421.2,2009,NOW(),NOW()),
  ('978-1-4028-0062-2','Hanbit Media','Seed Book Title 062','Seed summary for book 062. Generated for assignment data seeding.',286.66,2010,NOW(),NOW()),
  ('978-1-4028-0063-3','NoStarch','Seed Book Title 063','Seed summary for book 063. Generated for assignment data seeding.',51.63,2011,NOW(),NOW()),
  ('978-1-4028-0064-4','JPub','Seed Book Title 064','Seed summary for book 064. Generated for assignment data seeding.',260.09,2012,NOW(),NOW()),
  ('978-1-4028-0065-5','NoStarch','Seed Book Title 065','Seed summary for book 065. Generated for assignment data seeding.',316.74,2013,NOW(),NOW()),
  ('978-1-4028-0066-6','Manning','Seed Book Title 066','Seed summary for book 066. Generated for assignment data seeding.',174.76,2014,NOW(),NOW()),
  ('978-1-4028-0067-7','Manning','Seed Book Title 067','Seed summary for book 067. Generated for assignment data seeding.',17.52,2015,NOW(),NOW()),
  ('978-1-4028-0068-8','BlueSky Press','Seed Book Title 068','Seed summary for book 068. Generated for assignment data seeding.',361.9,2016,NOW(),NOW()),
  ('978-1-4028-0069-9','O''Reilly','Seed Book Title 069','Seed summary for book 069. Generated for assignment data seeding.',430.06,2017,NOW(),NOW()),
  ('978-1-4028-0070-0','Pearson','Seed Book Title 070','Seed summary for book 070. Generated for assignment data seeding.',83.1,2018,NOW(),NOW()),
  ('978-1-4028-0071-1','O''Reilly','Seed Book Title 071','Seed summary for book 071. Generated for assignment data seeding.',294.92,2019,NOW(),NOW()),
  ('978-1-4028-0072-2','Hanbit Media','Seed Book Title 072','Seed summary for book 072. Generated for assignment data seeding.',307.35,2020,NOW(),NOW()),
  ('978-1-4028-0073-3','Acme Books','Seed Book Title 073','Seed summary for book 073. Generated for assignment data seeding.',182.61,2021,NOW(),NOW()),
  ('978-1-4028-0074-4','Manning','Seed Book Title 074','Seed summary for book 074. Generated for assignment data seeding.',127.08,2022,NOW(),NOW()),
  ('978-1-4028-0075-5','Manning','Seed Book Title 075','Seed summary for book 075. Generated for assignment data seeding.',79.73,2023,NOW(),NOW()),
  ('978-1-4028-0076-6','O''Reilly','Seed Book Title 076','Seed summary for book 076. Generated for assignment data seeding.',428.74,2024,NOW(),NOW()),
  ('978-1-4028-0077-7','Manning','Seed Book Title 077','Seed summary for book 077. Generated for assignment data seeding.',409.09,2025,NOW(),NOW()),
  ('978-1-4028-0078-8','Packt','Seed Book Title 078','Seed summary for book 078. Generated for assignment data seeding.',110.16,2000,NOW(),NOW()),
  ('978-1-4028-0079-9','Pearson','Seed Book Title 079','Seed summary for book 079. Generated for assignment data seeding.',115.87,2001,NOW(),NOW()),
  ('978-1-4028-0080-0','Manning','Seed Book Title 080','Seed summary for book 080. Generated for assignment data seeding.',357.57,2002,NOW(),NOW()),
  ('978-1-4028-0081-1','Acme Books','Seed Book Title 081','Seed summary for book 081. Generated for assignment data seeding.',402.52,2003,NOW(),NOW()),
  ('978-1-4028-0082-2','Pearson','Seed Book Title 082','Seed summary for book 082. Generated for assignment data seeding.',330.21,2004,NOW(),NOW()),
  ('978-1-4028-0083-3','Acme Books','Seed Book Title 083','Seed summary for book 083. Generated for assignment data seeding.',83.31,2005,NOW(),NOW()),
  ('978-1-4028-0084-4','Pearson','Seed Book Title 084','Seed summary for book 084. Generated for assignment data seeding.',211.53,2006,NOW(),NOW()),
  ('978-1-4028-0085-5','Packt','Seed Book Title 085','Seed summary for book 085. Generated for assignment data seeding.',47.96,2007,NOW(),NOW()),
  ('978-1-4028-0086-6','Packt','Seed Book Title 086','Seed summary for book 086. Generated for assignment data seeding.',381.82,2008,NOW(),NOW()),
  ('978-1-4028-0087-7','BlueSky Press','Seed Book Title 087','Seed summary for book 087. Generated for assignment data seeding.',66.13,2009,NOW(),NOW()),
  ('978-1-4028-0088-8','Springer','Seed Book Title 088','Seed summary for book 088. Generated for assignment data seeding.',55.35,2010,NOW(),NOW()),
  ('978-1-4028-0089-9','Manning','Seed Book Title 089','Seed summary for book 089. Generated for assignment data seeding.',92.41,2011,NOW(),NOW()),
  ('978-1-4028-0090-0','Hanbit Media','Seed Book Title 090','Seed summary for book 090. Generated for assignment data seeding.',442.37,2012,NOW(),NOW()),
  ('978-1-4028-0091-1','Springer','Seed Book Title 091','Seed summary for book 091. Generated for assignment data seeding.',370.31,2013,NOW(),NOW()),
  ('978-1-4028-0092-2','Hanbit Media','Seed Book Title 092','Seed summary for book 092. Generated for assignment data seeding.',183.7,2014,NOW(),NOW()),
  ('978-1-4028-0093-3','Manning','Seed Book Title 093','Seed summary for book 093. Generated for assignment data seeding.',407.53,2015,NOW(),NOW()),
  ('978-1-4028-0094-4','JPub','Seed Book Title 094','Seed summary for book 094. Generated for assignment data seeding.',148.8,2016,NOW(),NOW()),
  ('978-1-4028-0095-5','Manning','Seed Book Title 095','Seed summary for book 095. Generated for assignment data seeding.',141.82,2017,NOW(),NOW()),
  ('978-1-4028-0096-6','O''Reilly','Seed Book Title 096','Seed summary for book 096. Generated for assignment data seeding.',271.48,2018,NOW(),NOW()),
  ('978-1-4028-0097-7','Pearson','Seed Book Title 097','Seed summary for book 097. Generated for assignment data seeding.',297.11,2019,NOW(),NOW()),
  ('978-1-4028-0098-8','Manning','Seed Book Title 098','Seed summary for book 098. Generated for assignment data seeding.',305.88,2020,NOW(),NOW()),
  ('978-1-4028-0099-9','BlueSky Press','Seed Book Title 099','Seed summary for book 099. Generated for assignment data seeding.',172.46,2021,NOW(),NOW()),
  ('978-1-4028-0100-0','Packt','Seed Book Title 100','Seed summary for book 100. Generated for assignment data seeding.',51.96,2022,NOW(),NOW()),
  ('978-1-4028-0101-1','Pearson','Seed Book Title 101','Seed summary for book 101. Generated for assignment data seeding.',23.78,2023,NOW(),NOW()),
  ('978-1-4028-0102-2','NoStarch','Seed Book Title 102','Seed summary for book 102. Generated for assignment data seeding.',373.01,2024,NOW(),NOW()),
  ('978-1-4028-0103-3','Packt','Seed Book Title 103','Seed summary for book 103. Generated for assignment data seeding.',395.64,2025,NOW(),NOW()),
  ('978-1-4028-0104-4','Packt','Seed Book Title 104','Seed summary for book 104. Generated for assignment data seeding.',14.71,2000,NOW(),NOW()),
  ('978-1-4028-0105-5','BlueSky Press','Seed Book Title 105','Seed summary for book 105. Generated for assignment data seeding.',423.59,2001,NOW(),NOW()),
  ('978-1-4028-0106-6','Acme Books','Seed Book Title 106','Seed summary for book 106. Generated for assignment data seeding.',160.03,2002,NOW(),NOW()),
  ('978-1-4028-0107-7','BlueSky Press','Seed Book Title 107','Seed summary for book 107. Generated for assignment data seeding.',30.58,2003,NOW(),NOW()),
  ('978-1-4028-0108-8','Pearson','Seed Book Title 108','Seed summary for book 108. Generated for assignment data seeding.',56.43,2004,NOW(),NOW()),
  ('978-1-4028-0109-9','Manning','Seed Book Title 109','Seed summary for book 109. Generated for assignment data seeding.',165.97,2005,NOW(),NOW()),
  ('978-1-4028-0110-0','O''Reilly','Seed Book Title 110','Seed summary for book 110. Generated for assignment data seeding.',448.42,2006,NOW(),NOW()),
  ('978-1-4028-0111-1','Springer','Seed Book Title 111','Seed summary for book 111. Generated for assignment data seeding.',150.4,2007,NOW(),NOW()),
  ('978-1-4028-0112-2','Manning','Seed Book Title 112','Seed summary for book 112. Generated for assignment data seeding.',96.71,2008,NOW(),NOW()),
  ('978-1-4028-0113-3','NoStarch','Seed Book Title 113','Seed summary for book 113. Generated for assignment data seeding.',387.62,2009,NOW(),NOW()),
  ('978-1-4028-0114-4','Springer','Seed Book Title 114','Seed summary for book 114. Generated for assignment data seeding.',169.25,2010,NOW(),NOW()),
  ('978-1-4028-0115-5','Springer','Seed Book Title 115','Seed summary for book 115. Generated for assignment data seeding.',276.77,2011,NOW(),NOW()),
  ('978-1-4028-0116-6','Packt','Seed Book Title 116','Seed summary for book 116. Generated for assignment data seeding.',71.81,2012,NOW(),NOW()),
  ('978-1-4028-0117-7','BlueSky Press','Seed Book Title 117','Seed summary for book 117. Generated for assignment data seeding.',441.87,2013,NOW(),NOW()),
  ('978-1-4028-0118-8','JPub','Seed Book Title 118','Seed summary for book 118. Generated for assignment data seeding.',242.19,2014,NOW(),NOW()),
  ('978-1-4028-0119-9','JPub','Seed Book Title 119','Seed summary for book 119. Generated for assignment data seeding.',279.41,2015,NOW(),NOW()),
  ('978-1-4028-0120-0','Springer','Seed Book Title 120','Seed summary for book 120. Generated for assignment data seeding.',45.5,2016,NOW(),NOW()),
  ('978-1-4028-0121-1','BlueSky Press','Seed Book Title 121','Seed summary for book 121. Generated for assignment data seeding.',49.72,2017,NOW(),NOW()),
  ('978-1-4028-0122-2','JPub','Seed Book Title 122','Seed summary for book 122. Generated for assignment data seeding.',232.36,2018,NOW(),NOW()),
  ('978-1-4028-0123-3','BlueSky Press','Seed Book Title 123','Seed summary for book 123. Generated for assignment data seeding.',172.95,2019,NOW(),NOW()),
  ('978-1-4028-0124-4','Packt','Seed Book Title 124','Seed summary for book 124. Generated for assignment data seeding.',134.65,2020,NOW(),NOW()),
  ('978-1-4028-0125-5','Manning','Seed Book Title 125','Seed summary for book 125. Generated for assignment data seeding.',304,2021,NOW(),NOW()),
  ('978-1-4028-0126-6','Hanbit Media','Seed Book Title 126','Seed summary for book 126. Generated for assignment data seeding.',286.48,2022,NOW(),NOW()),
  ('978-1-4028-0127-7','Hanbit Media','Seed Book Title 127','Seed summary for book 127. Generated for assignment data seeding.',192.54,2023,NOW(),NOW()),
  ('978-1-4028-0128-8','Springer','Seed Book Title 128','Seed summary for book 128. Generated for assignment data seeding.',173.71,2024,NOW(),NOW()),
  ('978-1-4028-0129-9','BlueSky Press','Seed Book Title 129','Seed summary for book 129. Generated for assignment data seeding.',300.41,2025,NOW(),NOW()),
  ('978-1-4028-0130-0','Manning','Seed Book Title 130','Seed summary for book 130. Generated for assignment data seeding.',74.16,2000,NOW(),NOW()),
  ('978-1-4028-0131-1','Acme Books','Seed Book Title 131','Seed summary for book 131. Generated for assignment data seeding.',437.38,2001,NOW(),NOW()),
  ('978-1-4028-0132-2','Manning','Seed Book Title 132','Seed summary for book 132. Generated for assignment data seeding.',19.67,2002,NOW(),NOW()),
  ('978-1-4028-0133-3','BlueSky Press','Seed Book Title 133','Seed summary for book 133. Generated for assignment data seeding.',164.91,2003,NOW(),NOW()),
  ('978-1-4028-0134-4','Hanbit Media','Seed Book Title 134','Seed summary for book 134. Generated for assignment data seeding.',276.34,2004,NOW(),NOW()),
  ('978-1-4028-0135-5','Springer','Seed Book Title 135','Seed summary for book 135. Generated for assignment data seeding.',325.46,2005,NOW(),NOW()),
  ('978-1-4028-0136-6','Packt','Seed Book Title 136','Seed summary for book 136. Generated for assignment data seeding.',272.82,2006,NOW(),NOW()),
  ('978-1-4028-0137-7','Acme Books','Seed Book Title 137','Seed summary for book 137. Generated for assignment data seeding.',117.89,2007,NOW(),NOW()),
  ('978-1-4028-0138-8','JPub','Seed Book Title 138','Seed summary for book 138. Generated for assignment data seeding.',11.41,2008,NOW(),NOW()),
  ('978-1-4028-0139-9','JPub','Seed Book Title 139','Seed summary for book 139. Generated for assignment data seeding.',183.8,2009,NOW(),NOW()),
  ('978-1-4028-0140-0','Springer','Seed Book Title 140','Seed summary for book 140. Generated for assignment data seeding.',196.94,2010,NOW(),NOW()),
  ('978-1-4028-0141-1','JPub','Seed Book Title 141','Seed summary for book 141. Generated for assignment data seeding.',374.22,2011,NOW(),NOW()),
  ('978-1-4028-0142-2','Springer','Seed Book Title 142','Seed summary for book 142. Generated for assignment data seeding.',111.44,2012,NOW(),NOW()),
  ('978-1-4028-0143-3','Packt','Seed Book Title 143','Seed summary for book 143. Generated for assignment data seeding.',204.45,2013,NOW(),NOW()),
  ('978-1-4028-0144-4','Packt','Seed Book Title 144','Seed summary for book 144. Generated for assignment data seeding.',48.32,2014,NOW(),NOW()),
  ('978-1-4028-0145-5','NoStarch','Seed Book Title 145','Seed summary for book 145. Generated for assignment data seeding.',365.33,2015,NOW(),NOW()),
  ('978-1-4028-0146-6','Acme Books','Seed Book Title 146','Seed summary for book 146. Generated for assignment data seeding.',215.52,2016,NOW(),NOW()),
  ('978-1-4028-0147-7','Acme Books','Seed Book Title 147','Seed summary for book 147. Generated for assignment data seeding.',42.86,2017,NOW(),NOW()),
  ('978-1-4028-0148-8','NoStarch','Seed Book Title 148','Seed summary for book 148. Generated for assignment data seeding.',322.46,2018,NOW(),NOW()),
  ('978-1-4028-0149-9','Manning','Seed Book Title 149','Seed summary for book 149. Generated for assignment data seeding.',358.07,2019,NOW(),NOW()),
  ('978-1-4028-0150-0','Hanbit Media','Seed Book Title 150','Seed summary for book 150. Generated for assignment data seeding.',47.27,2020,NOW(),NOW())
ON DUPLICATE KEY UPDATE `updated_at`=NOW(), `price`=VALUES(`price`), `publication_year`=VALUES(`publication_year`);

/* Carts: one cart per seeded user */
INSERT IGNORE INTO `carts` (`user_id`,`created_at`,`updated_at`,`deleted_at`)
SELECT u.`user_id`, NOW(), NOW(), NULL
FROM `user` u
WHERE u.`email` IN ('user1@example.com', 'admin1@example.com', 'seed_user_001@example.com', 'seed_user_002@example.com', 'seed_user_003@example.com', 'seed_user_004@example.com', 'seed_user_005@example.com', 'seed_user_006@example.com', 'seed_user_007@example.com', 'seed_user_008@example.com', 'seed_user_009@example.com', 'seed_user_010@example.com', 'seed_user_011@example.com', 'seed_user_012@example.com', 'seed_user_013@example.com', 'seed_user_014@example.com', 'seed_user_015@example.com', 'seed_user_016@example.com', 'seed_user_017@example.com', 'seed_user_018@example.com', 'seed_user_019@example.com', 'seed_user_020@example.com', 'seed_user_021@example.com', 'seed_user_022@example.com', 'seed_user_023@example.com', 'seed_user_024@example.com', 'seed_user_025@example.com', 'seed_user_026@example.com', 'seed_user_027@example.com', 'seed_user_028@example.com', 'seed_user_029@example.com', 'seed_user_030@example.com');

/* Cart items: 2 items per cart (uses modulo mapping to pick existing books) */
INSERT IGNORE INTO `cart_items` (`cart_id`,`book_id`,`unit_price`,`quantity`)
SELECT c.`cart_id`, pb.`book_id`, b.`price`, 1 + MOD(c.`cart_id`, 3)
FROM `carts` c
JOIN (
  SELECT MIN(`book_id`) AS `book_id`, MOD(`book_id`, 50) AS grp
  FROM `book`
  GROUP BY grp
) pb ON pb.grp = MOD(c.`cart_id` + 0, 50)
JOIN `book` b ON b.`book_id` = pb.`book_id`;

INSERT IGNORE INTO `cart_items` (`cart_id`,`book_id`,`unit_price`,`quantity`)
SELECT c.`cart_id`, pb.`book_id`, b.`price`, 1 + MOD(c.`cart_id`, 3)
FROM `carts` c
JOIN (
  SELECT MIN(`book_id`) AS `book_id`, MOD(`book_id`, 50) AS grp
  FROM `book`
  GROUP BY grp
) pb ON pb.grp = MOD(c.`cart_id` + 17, 50)
JOIN `book` b ON b.`book_id` = pb.`book_id`;

/* Wishlist: 2 wished books per seeded user */
INSERT IGNORE INTO `wishlist` (`user_id`,`book_id`,`created_at`)
SELECT u.`user_id`, pb.`book_id`, NOW()
FROM `user` u
JOIN (
  SELECT MIN(`book_id`) AS `book_id`, MOD(`book_id`, 60) AS grp
  FROM `book`
  GROUP BY grp
) pb ON pb.grp = MOD(u.`user_id` + 0, 60)
WHERE u.`email` IN ('user1@example.com', 'admin1@example.com', 'seed_user_001@example.com', 'seed_user_002@example.com', 'seed_user_003@example.com', 'seed_user_004@example.com', 'seed_user_005@example.com', 'seed_user_006@example.com', 'seed_user_007@example.com', 'seed_user_008@example.com', 'seed_user_009@example.com', 'seed_user_010@example.com', 'seed_user_011@example.com', 'seed_user_012@example.com', 'seed_user_013@example.com', 'seed_user_014@example.com', 'seed_user_015@example.com', 'seed_user_016@example.com', 'seed_user_017@example.com', 'seed_user_018@example.com', 'seed_user_019@example.com', 'seed_user_020@example.com', 'seed_user_021@example.com', 'seed_user_022@example.com', 'seed_user_023@example.com', 'seed_user_024@example.com', 'seed_user_025@example.com', 'seed_user_026@example.com', 'seed_user_027@example.com', 'seed_user_028@example.com', 'seed_user_029@example.com', 'seed_user_030@example.com');

INSERT IGNORE INTO `wishlist` (`user_id`,`book_id`,`created_at`)
SELECT u.`user_id`, pb.`book_id`, NOW()
FROM `user` u
JOIN (
  SELECT MIN(`book_id`) AS `book_id`, MOD(`book_id`, 60) AS grp
  FROM `book`
  GROUP BY grp
) pb ON pb.grp = MOD(u.`user_id` + 23, 60)
WHERE u.`email` IN ('user1@example.com', 'admin1@example.com', 'seed_user_001@example.com', 'seed_user_002@example.com', 'seed_user_003@example.com', 'seed_user_004@example.com', 'seed_user_005@example.com', 'seed_user_006@example.com', 'seed_user_007@example.com', 'seed_user_008@example.com', 'seed_user_009@example.com', 'seed_user_010@example.com', 'seed_user_011@example.com', 'seed_user_012@example.com', 'seed_user_013@example.com', 'seed_user_014@example.com', 'seed_user_015@example.com', 'seed_user_016@example.com', 'seed_user_017@example.com', 'seed_user_018@example.com', 'seed_user_019@example.com', 'seed_user_020@example.com', 'seed_user_021@example.com', 'seed_user_022@example.com', 'seed_user_023@example.com', 'seed_user_024@example.com', 'seed_user_025@example.com', 'seed_user_026@example.com', 'seed_user_027@example.com', 'seed_user_028@example.com', 'seed_user_029@example.com', 'seed_user_030@example.com');

/* Reviews: 60 reviews (2 per seed_user_*) */
INSERT INTO `review` (`user_id`,`book_id`,`rating`,`content`,`likes_count`,`created_at`,`updated_at`,`deleted_at`)
SELECT u.`user_id`, pb.`book_id`, 3 + MOD(u.`user_id` + n.n, 3) AS rating,
       CONCAT('Seed review ', u.`email`, ' #', n.n) AS content,
       0 AS likes_count, NOW(), NOW(), NULL
FROM `user` u
JOIN (SELECT 1 AS n UNION ALL SELECT 2) n
JOIN (
  SELECT MIN(`book_id`) AS `book_id`, MOD(`book_id`, 80) AS grp
  FROM `book`
  GROUP BY grp
) pb ON pb.grp = MOD(u.`user_id` + (n.n * 7), 80)
WHERE u.`email` LIKE 'seed_user_%'
LIMIT 60;

/* Review likes: 80 likes spread */
INSERT IGNORE INTO `review_likes` (`review_id`,`user_id`,`created_at`)
SELECT r.`review_id`, u.`user_id`, NOW()
FROM `review` r
JOIN `user` u ON MOD(r.`review_id`, 20) = MOD(u.`user_id`, 20)
WHERE u.`email` LIKE 'seed_user_%'
LIMIT 80;

/* Orders: 40 orders (2 per seed_user_*) */
INSERT INTO `orders` (`user_id`,`status`,`total_amount`,`created_at`,`updated_at`,`deleted_at`)
SELECT u.`user_id`,
       CASE MOD(u.`user_id` + n.n, 5)
         WHEN 0 THEN 'CREATED'
         WHEN 1 THEN 'PAID'
         WHEN 2 THEN 'SHIPPED'
         WHEN 3 THEN 'DELIVERED'
         ELSE 'CANCELLED'
       END AS status,
       0.00 AS total_amount,
       NOW(), NOW(), NULL
FROM `user` u
JOIN (SELECT 1 AS n UNION ALL SELECT 2) n
WHERE u.`email` LIKE 'seed_user_%'
LIMIT 40;

/* Order items: 2 items per order */
INSERT INTO `order_items` (`order_id`,`book_id`,`unit_price`,`quantity`)
SELECT o.`id`, pb.`book_id`, b.`price`, 1 + MOD(o.`id`, 2)
FROM `orders` o
JOIN (
  SELECT MIN(`book_id`) AS `book_id`, MOD(`book_id`, 90) AS grp
  FROM `book`
  GROUP BY grp
) pb ON pb.grp = MOD(o.`id` + 0, 90)
JOIN `book` b ON b.`book_id` = pb.`book_id`;

INSERT INTO `order_items` (`order_id`,`book_id`,`unit_price`,`quantity`)
SELECT o.`id`, pb.`book_id`, b.`price`, 1 + MOD(o.`id`, 2)
FROM `orders` o
JOIN (
  SELECT MIN(`book_id`) AS `book_id`, MOD(`book_id`, 90) AS grp
  FROM `book`
  GROUP BY grp
) pb ON pb.grp = MOD(o.`id` + 29, 90)
JOIN `book` b ON b.`book_id` = pb.`book_id`;

/* Update orders.total_amount based on items */
UPDATE `orders` o
JOIN (
  SELECT `order_id`, SUM(`unit_price` * `quantity`) AS total
  FROM `order_items`
  GROUP BY `order_id`
) s ON s.`order_id` = o.`id`
SET o.`total_amount` = s.total;
