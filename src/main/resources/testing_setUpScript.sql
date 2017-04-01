DROP TABLE brokers CASCADE CONSTRAINTS;
DROP TABLE users CASCADE CONSTRAINTS;
DROP TABLE companies CASCADE CONSTRAINTS;
DROP TABLE places CASCADE CONSTRAINTS;
DROP TABLE stock_exchanges CASCADE CONSTRAINTS;
DROP TABLE shares CASCADE CONSTRAINTS;
DROP TABLE currencies CASCADE CONSTRAINTS;
DROP TABLE trades CASCADE CONSTRAINTS;
DROP TABLE shares_prices CASCADE CONSTRAINTS;
DROP TABLE broker_stock_ex CASCADE CONSTRAINTS;

------------------------ CREATE THE SEQUENCES ------------------------

DROP SEQUENCE broker_id_seq;
CREATE SEQUENCE broker_id_seq MINVALUE 1 START WITH 1 INCREMENT BY 1;

DROP SEQUENCE user_id_seq;
CREATE SEQUENCE user_id_seq MINVALUE 1 START WITH 1 INCREMENT BY 1;

DROP SEQUENCE place_id_seq;
CREATE SEQUENCE place_id_seq MINVALUE 1 START WITH 1 INCREMENT BY 1;

DROP SEQUENCE company_id_seq;
CREATE SEQUENCE company_id_seq MINVALUE 1 START WITH 1 INCREMENT BY 1;

DROP SEQUENCE stock_ex_id_seq;
CREATE SEQUENCE stock_ex_id_seq MINVALUE 1 START WITH 1 INCREMENT BY 1;

DROP SEQUENCE share_id_seq;
CREATE SEQUENCE share_id_seq MINVALUE 1 START WITH 1 INCREMENT BY 1;

DROP SEQUENCE currency_id_seq;
CREATE SEQUENCE currency_id_seq MINVALUE 1 START WITH 1 INCREMENT BY 1;

DROP SEQUENCE trades_id_seq;
CREATE SEQUENCE trades_id_seq MINVALUE 1 START WITH 1 INCREMENT BY 1;

DROP SEQUENCE broker_stock_ex_id_seq;
CREATE SEQUENCE broker_stock_ex_id_seq MINVALUE 1 START WITH 1 INCREMENT BY 1;

------------------------ CREATE THE TABLES ------------------------

CREATE TABLE users (
      user_id   NUMBER(6) PRIMARY KEY,
      username  VARCHAR2(50),
      password  VARCHAR2(50),
      firstname VARCHAR2(50),
      lastname  VARCHAR2(50),
      email     VARCHAR2(50),
      status    VARCHAR2(20)
);

CREATE TABLE brokers (
  broker_id         NUMBER(6) PRIMARY KEY,
  user_fk           NUMBER(6),
  first_name        VARCHAR2(25),
  last_name         VARCHAR2(25),
  CONSTRAINT user_fk FOREIGN KEY (user_fk) REFERENCES users(user_id)
);

CREATE TABLE places (
  place_id          NUMBER(6),
  city              VARCHAR2(50),
  country           VARCHAR2(50),
  CONSTRAINT place_pk PRIMARY KEY (place_id)
);

CREATE TABLE companies (
  company_id NUMBER(6) PRIMARY KEY,
  company_name VARCHAR2 (50),
  place_id  NUMBER(6),
  FOREIGN KEY (place_id) REFERENCES places(place_id)
);

CREATE TABLE stock_exchanges (
  stock_ex_id       NUMBER(6),
  name              VARCHAR2(50),
  symbol            VARCHAR2(10),
  place_id          NUMBER(6),
  CONSTRAINT stock_ex_pk PRIMARY KEY (stock_ex_id),
  CONSTRAINT se_place_fk FOREIGN KEY (place_id) REFERENCES places(place_id)
);

CREATE TABLE broker_stock_ex (
  broker_id         NUMBER(6),
  stock_ex_id       NUMBER(6),
  CONSTRAINT bse_pk PRIMARY KEY (broker_id,stock_ex_id),
  CONSTRAINT bse_broker_fk FOREIGN KEY (broker_id) REFERENCES brokers(broker_id),
  CONSTRAINT bse_se_fk FOREIGN KEY (stock_ex_id) REFERENCES stock_exchanges(stock_ex_id)
);

CREATE TABLE currencies (
  currency_id       NUMBER(6),
  symbol            VARCHAR2(5),
  name              VARCHAR2(50),
  CONSTRAINT currency_pk PRIMARY KEY (currency_id)
);

CREATE TABLE shares (
  share_id          NUMBER(6),
  company_id        NUMBER(6),
  currency_id       NUMBER(6),
  code              VARCHAR(15),
  stock_ex_id       NUMBER(6),
  CONSTRAINT share_pk PRIMARY KEY (share_id),
  CONSTRAINT share_company_fk FOREIGN KEY (company_id) REFERENCES companies(company_id),
  CONSTRAINT share_currency_fk FOREIGN KEY (currency_id) REFERENCES currencies(currency_id),
  CONSTRAINT share_stock_ex_fk FOREIGN KEY (stock_ex_id) REFERENCES stock_exchanges(stock_ex_id)
);

CREATE TABLE trades (
  trade_id          NUMBER(9),
  share_id          NUMBER(6),
  broker_id         NUMBER(6),
  user_id           NUMBER(6),
  stock_ex_id       NUMBER(6),
  transaction_time  DATE,
  share_amount      NUMBER(9),
  price_total       FLOAT,
  CONSTRAINT trade_pk PRIMARY KEY (trade_id),
  CONSTRAINT trade_share_fk FOREIGN KEY (share_id) REFERENCES shares(share_id),
  CONSTRAINT trade_broker_fk FOREIGN KEY (broker_id) REFERENCES brokers(broker_id),
  CONSTRAINT trade_user_fk FOREIGN KEY (user_id) REFERENCES users(user_id),
  CONSTRAINT trade_stock_ex_fk FOREIGN KEY (stock_ex_id) REFERENCES stock_exchanges(stock_ex_id)
);

CREATE TABLE shares_prices (
  share_id          NUMBER(6),
  price             NUMBER(12,4),
  time_start        NUMBER(30),
  CONSTRAINT shares_prices_pk PRIMARY KEY (share_id,time_start),
  CONSTRAINT shares_prices_fk FOREIGN KEY (share_id) REFERENCES shares(share_id)
);

------------------------ FILL WITH DATA ------------------------

INSERT INTO users VALUES (user_id_seq.nextval, 'jacksond80', 'password', 'Darryl', 'Jackson', 'darryl.jackson@fdmgroup.com', 'Admin');
INSERT INTO users VALUES (user_id_seq.nextval, 'ml404', 'password', 'Matt', 'Layton', 'matt.layton@fdmgroup.com', 'Admin');
INSERT INTO users VALUES (user_id_seq.nextval, 'liban.mahamed', 'password', 'Liban', 'Mahamed', 'liban.mahamed@fdmgroup.com', 'Admin');
INSERT INTO users VALUES (user_id_seq.nextval, 'jake.laver', 'password', 'Jake', 'Laver', 'jake.laver@fdmgroup.com', 'Admin');
INSERT INTO users VALUES (user_id_seq.nextval, 'patrick.rode', 'password', 'Patrick', 'Rode', 'patrick.rode@fdmgroup.com', 'Admin');
INSERT INTO users VALUES (user_id_seq.nextval, 'yu.ji', 'password', 'Yu', 'Ji', 'yu.ji@fdmgroup.com', 'Admin');
INSERT INTO users VALUES (user_id_seq.nextval, 'john.smith', 'password', 'John', 'Smith', 'john.smith@email.com', 'Broker');
INSERT INTO users VALUES (user_id_seq.nextval, 'herbert.jackson', 'password', 'Herbert', 'Jackson', 'herbert.jackson@email.com', 'Broker');
INSERT INTO users VALUES (user_id_seq.nextval, 'richard.bradley', 'password', 'Richard', 'Bradley', 'richard.bradley@email.com', 'Broker');
INSERT INTO users VALUES (user_id_seq.nextval, 'frank.denzel', 'password', 'Frank', 'Denzel', 'frank.denzel@email.com', 'Broker');
INSERT INTO users VALUES (user_id_seq.nextval, 'elric.crofton', 'password', 'Elric', 'Crofton', 'elric.crofton@email.com', 'Broker');
INSERT INTO users VALUES (user_id_seq.nextval, 'ted.gore', 'password', 'Ted', 'Gore', 'ted.gore@email.com', 'Broker');
INSERT INTO users VALUES (user_id_seq.nextval, 'john.bush', 'password', 'John', 'Bush', 'john.bush@email.com', 'Broker');
INSERT INTO users VALUES (user_id_seq.nextval, 'andre.sinclair', 'password', 'Andre', 'Sinclair', 'andre.sinclair@email.com', 'Broker');
INSERT INTO users VALUES (user_id_seq.nextval, 'danielle.perety', 'password', 'Danielle', 'Perety', 'danielle.perety@email.com', 'Broker');
INSERT INTO users VALUES (user_id_seq.nextval, 'arabella.volvitz', 'password', 'Arabella', 'Volvitz', 'arabella.volvitz@email.com', 'Broker');
INSERT INTO users VALUES (user_id_seq.nextval, 'parker.hamilton', 'password', 'Parker', 'Hamilton', 'parker.hamilton@email.com', 'Broker');
INSERT INTO users VALUES (user_id_seq.nextval, 'andrew.wallace', 'password', 'Andrew', 'Wallace', 'andrew.wallace@email.com', 'Broker');
INSERT INTO users VALUES (user_id_seq.nextval, 'bruce.smith', 'password', 'Bruce', 'Smith', 'bruce.smith@email.com', 'Broker');
INSERT INTO users VALUES (user_id_seq.nextval, 'tommy.mack', 'password', 'Tommy', 'Mack', 'tommy.mack@email.com', 'Broker');
INSERT INTO users VALUES (user_id_seq.nextval, 'frederick.raven', 'password', 'Frederick', 'Raven', 'frederick.raven@email.com', 'Broker');

INSERT INTO brokers (broker_id,user_fk,first_name,last_name) VALUES (broker_id_seq.nextval,7,'John','Smith');
INSERT INTO brokers (broker_id,user_fk,first_name,last_name) VALUES (broker_id_seq.nextval,8,'Herbert','Jackson');
INSERT INTO brokers (broker_id,user_fk,first_name,last_name) VALUES (broker_id_seq.nextval,9,'Richard','Bradley');
INSERT INTO brokers (broker_id,user_fk,first_name,last_name) VALUES (broker_id_seq.nextval,10,'Frank','Denzel');
INSERT INTO brokers (broker_id,user_fk,first_name,last_name) VALUES (broker_id_seq.nextval,11,'Elric','Crofton');
INSERT INTO brokers (broker_id,user_fk,first_name,last_name) VALUES (broker_id_seq.nextval,12,'Ted','Gore');
INSERT INTO brokers (broker_id,user_fk,first_name,last_name) VALUES (broker_id_seq.nextval,13,'John','Bush');
INSERT INTO brokers (broker_id,user_fk,first_name,last_name) VALUES (broker_id_seq.nextval,14,'Andre','Sinclair');
INSERT INTO brokers (broker_id,user_fk,first_name,last_name) VALUES (broker_id_seq.nextval,15,'Danielle','Perety');
INSERT INTO brokers (broker_id,user_fk,first_name,last_name) VALUES (broker_id_seq.nextval,16,'Arabella','Volvitz');
INSERT INTO brokers (broker_id,user_fk,first_name,last_name) VALUES (broker_id_seq.nextval,17,'Parker','Hamilton');
INSERT INTO brokers (broker_id,user_fk,first_name,last_name) VALUES (broker_id_seq.nextval,18,'Andrew','Wallace');
INSERT INTO brokers (broker_id,user_fk,first_name,last_name) VALUES (broker_id_seq.nextval,19,'Bruce','Smith');
INSERT INTO brokers (broker_id,user_fk,first_name,last_name) VALUES (broker_id_seq.nextval,20,'Tommy','Mack');
INSERT INTO brokers (broker_id,user_fk,first_name,last_name) VALUES (broker_id_seq.nextval,21,'Frederick','Raven');

INSERT INTO currencies (currency_id,symbol,name) VALUES (currency_id_seq.NEXTVAL,'USD','Dollar');
INSERT INTO currencies (currency_id,symbol,name) VALUES (currency_id_seq.NEXTVAL,'EUR','Euro');
INSERT INTO currencies (currency_id,symbol,name) VALUES (currency_id_seq.NEXTVAL,'GBP','British Pound'); -- 3
INSERT INTO currencies (currency_id,symbol,name) VALUES (currency_id_seq.NEXTVAL,'GBX','British Pence');
INSERT INTO currencies (currency_id,symbol,name) VALUES (currency_id_seq.NEXTVAL,'JPY','Yen');
INSERT INTO currencies (currency_id,symbol,name) VALUES (currency_id_seq.NEXTVAL,'CHF','Swiss Francs'); -- 6
INSERT INTO currencies (currency_id,symbol,name) VALUES (currency_id_seq.NEXTVAL,'KRW','South Korean Won');

INSERT INTO places (place_id,city,country) VALUES (place_id_seq.NEXTVAL,'London','United Kingdom');
INSERT INTO places (place_id,city,country) VALUES (place_id_seq.NEXTVAL,'Paris','France');
INSERT INTO places (place_id,city,country) VALUES (place_id_seq.NEXTVAL,'New York','USA'); -- 3
INSERT INTO places (place_id,city,country) VALUES (place_id_seq.NEXTVAL,'Tokyo','Japan');
INSERT INTO places (place_id,city,country) VALUES (place_id_seq.NEXTVAL,'Mountain View','USA');
INSERT INTO places (place_id,city,country) VALUES (place_id_seq.NEXTVAL,'Zurich','Switzerland'); -- 6
INSERT INTO places (place_id,city,country) VALUES (place_id_seq.NEXTVAL,'Frankfurt','Germany');
INSERT INTO places (place_id,city,country) VALUES (place_id_seq.NEXTVAL,'Cupertino','USA');
INSERT INTO places (place_id,city,country) VALUES (place_id_seq.NEXTVAL,'Bentonville','USA'); -- 9
INSERT INTO places (place_id,city,country) VALUES (place_id_seq.NEXTVAL,'Suwon','South Korea');
INSERT INTO places (place_id,city,country) VALUES (place_id_seq.NEXTVAL,'Seoul','South Korea');
INSERT INTO places (place_id,city,country) VALUES (place_id_seq.NEXTVAL,'Stuttgart','Germany'); -- 12
INSERT INTO places (place_id,city,country) VALUES (place_id_seq.NEXTVAL,'Dallas','USA');
INSERT INTO places (place_id,city,country) VALUES (place_id_seq.NEXTVAL,'Munich','Germany');
INSERT INTO places (place_id,city,country) VALUES (place_id_seq.NEXTVAL,'Dearborn','USA'); -- 15
INSERT INTO places (place_id,city,country) VALUES (place_id_seq.NEXTVAL,'Sydney','Australia');
INSERT INTO places (place_id,city,country) VALUES (place_id_seq.NEXTVAL,'Moscow','Russia');
INSERT INTO places (place_id,city,country) VALUES (place_id_seq.NEXTVAL,'Rome','Italy');
INSERT INTO places (place_id,city,country) VALUES (place_id_seq.NEXTVAL,'Naples','Italy');

INSERT INTO stock_exchanges (stock_ex_id,name,symbol,place_id) VALUES (stock_ex_id_seq.NEXTVAL,'London Stock Exchange','LSE',1);
INSERT INTO stock_exchanges (stock_ex_id,name,symbol,place_id) VALUES (stock_ex_id_seq.NEXTVAL,'New York Stock Exchange','NYSE',3);
INSERT INTO stock_exchanges (stock_ex_id,name,symbol,place_id) VALUES (stock_ex_id_seq.NEXTVAL,'Nasdaq Stock Market','NASDAQ',3); -- 3
INSERT INTO stock_exchanges (stock_ex_id,name,symbol,place_id) VALUES (stock_ex_id_seq.NEXTVAL,'Euronext Paris','EP',2);
INSERT INTO stock_exchanges (stock_ex_id,name,symbol,place_id) VALUES (stock_ex_id_seq.NEXTVAL,'SIX Swiss Exchange','SIX',6);
INSERT INTO stock_exchanges (stock_ex_id,name,symbol,place_id) VALUES (stock_ex_id_seq.NEXTVAL,'Frankfurt Stock Exchange','FWB',7); -- 6
INSERT INTO stock_exchanges (stock_ex_id,name,symbol,place_id) VALUES (stock_ex_id_seq.NEXTVAL,'Tokyo Stock Exchange','TSE',4);
INSERT INTO stock_exchanges (stock_ex_id,name,symbol,place_id) VALUES (stock_ex_id_seq.NEXTVAL,'Moscow Stock Exchange','MSE',6);
INSERT INTO stock_exchanges (stock_ex_id,name,symbol,place_id) VALUES (stock_ex_id_seq.NEXTVAL,'Korea Exchange','KRX',11); -- 9

INSERT INTO companies (company_id,company_name,place_id) VALUES (company_id_seq.nextval,'International Consolidated Airlines Group',1);
INSERT INTO companies (company_id,company_name,place_id) VALUES (company_id_seq.nextval,'The Goldman Sachs Group, Inc.',3);
INSERT INTO companies (company_id,company_name,place_id) VALUES (company_id_seq.nextval,'Toyota Motor Corporation',4); -- 3
INSERT INTO companies (company_id,company_name,place_id) VALUES (company_id_seq.nextval,'BNP Paribas',2);
INSERT INTO companies (company_id,company_name,place_id) VALUES (company_id_seq.nextval,'Electricite de France',2);
INSERT INTO companies (company_id,company_name,place_id) VALUES (company_id_seq.nextval,'Tesco Corporation',1); -- 6
INSERT INTO companies (company_id,company_name,place_id) VALUES (company_id_seq.nextval,'International Business Machines Corporation',3);
INSERT INTO companies (company_id,company_name,place_id) VALUES (company_id_seq.nextval,'Alphabet Inc.',5);
INSERT INTO companies (company_id,company_name,place_id) VALUES (company_id_seq.nextval,'Apple Inc.',8); -- 9
INSERT INTO companies (company_id,company_name,place_id) VALUES (company_id_seq.nextval,'FDM Group',1);
INSERT INTO companies (company_id,company_name,place_id) VALUES (company_id_seq.nextval,'Wal-Mart Stores, Inc.',9);
INSERT INTO companies (company_id,company_name,place_id) VALUES (company_id_seq.nextval,'Samsung Electronics Co. Ltd.',10); -- 12
INSERT INTO companies (company_id,company_name,place_id) VALUES (company_id_seq.nextval,'Porsche Automobil Holding SE',12);
INSERT INTO companies (company_id,company_name,place_id) VALUES (company_id_seq.nextval,'AT'||chr(38)||'T Inc.',13);
INSERT INTO companies (company_id,company_name,place_id) VALUES (company_id_seq.nextval,'Allianz',14); -- 15
INSERT INTO companies (company_id,company_name,place_id) VALUES (company_id_seq.nextval,'Ford Motor Company',15);

INSERT INTO shares (share_id,company_id,currency_id,code,stock_ex_id) VALUES (share_id_seq.NEXTVAL,1,4,'IAG.L',1);
INSERT INTO shares (share_id,company_id,currency_id,code,stock_ex_id) VALUES (share_id_seq.NEXTVAL,2,1,'GS',2);
INSERT INTO shares (share_id,company_id,currency_id,code,stock_ex_id) VALUES (share_id_seq.NEXTVAL,3,1,'TM',2); -- 3
INSERT INTO shares (share_id,company_id,currency_id,code,stock_ex_id) VALUES (share_id_seq.NEXTVAL,4,2,'BNP.PA',4);
INSERT INTO shares (share_id,company_id,currency_id,code,stock_ex_id) VALUES (share_id_seq.NEXTVAL,5,2,'EDF.PA',4);
INSERT INTO shares (share_id,company_id,currency_id,code,stock_ex_id) VALUES (share_id_seq.NEXTVAL,6,4,'TSCO.L',1); -- 6
INSERT INTO shares (share_id,company_id,currency_id,code,stock_ex_id) VALUES (share_id_seq.NEXTVAL,7,1,'IBM',2);
INSERT INTO shares (share_id,company_id,currency_id,code,stock_ex_id) VALUES (share_id_seq.NEXTVAL,8,6,'GOOGL.SW',5);
INSERT INTO shares (share_id,company_id,currency_id,code,stock_ex_id) VALUES (share_id_seq.NEXTVAL,8,2,'ABEA.F',6); -- 9
INSERT INTO shares (share_id,company_id,currency_id,code,stock_ex_id) VALUES (share_id_seq.NEXTVAL,8,1,'GOOG',3);
INSERT INTO shares (share_id,company_id,currency_id,code,stock_ex_id) VALUES (share_id_seq.NEXTVAL,9,1,'AAPL',3);
INSERT INTO shares (share_id,company_id,currency_id,code,stock_ex_id) VALUES (share_id_seq.NEXTVAL,10,4,'FDM.L',1); -- 12
INSERT INTO shares (share_id,company_id,currency_id,code,stock_ex_id) VALUES (share_id_seq.NEXTVAL,11,1,'WMT',2);
INSERT INTO shares (share_id,company_id,currency_id,code,stock_ex_id) VALUES (share_id_seq.NEXTVAL,12,7,'005930.KS',9);
INSERT INTO shares (share_id,company_id,currency_id,code,stock_ex_id) VALUES (share_id_seq.NEXTVAL,13,2,'PAH3.F',6); -- 15
INSERT INTO shares (share_id,company_id,currency_id,code,stock_ex_id) VALUES (share_id_seq.NEXTVAL,14,1,'T',2);
INSERT INTO shares (share_id,company_id,currency_id,code,stock_ex_id) VALUES (share_id_seq.NEXTVAL,15,2,'ALV.DE',6);
INSERT INTO shares (share_id,company_id,currency_id,code,stock_ex_id) VALUES (share_id_seq.NEXTVAL,16,1,'F',2); -- 18

INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (13,2);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (15,4);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (2,2);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (6,2);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (14,4);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (15,2);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (14,3);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (7,2);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (12,3);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (8,3);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (1,1);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (9,1);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (1,4);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (13,4);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (1,2);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (11,4);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (1,3);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (3,1);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (10,1);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (4,4);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (11,2);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (5,3);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (12,1);
INSERT INTO broker_stock_ex (broker_id,stock_ex_id) VALUES (15,3);

INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,1,1,7,3,SYSDATE,20000,19400000);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,3,1,7,3,SYSDATE -3,4223,27069.43);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,1,1,1,3,SYSDATE -5,150,71305.5);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,7,1,2,1,SYSDATE -11,854,312564);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,3,1,5,3,SYSDATE -13,5000,42500);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,1,1,6,3,SYSDATE -19,3000,1395000);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,8,1,3,1,SYSDATE -24,10,1200);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,5,1,7,2,SYSDATE -28,6049,317572.5);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,1,3,9,3,SYSDATE -31,1000,478000);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,1,1,6,3,SYSDATE -35,1250,662500);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,4,1,4,3,SYSDATE -40,344,29240);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,1,1,2,3,SYSDATE -45,10230,4557190);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,1,3,1,3,SYSDATE -49,500,290000);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,2,1,1,1,SYSDATE -53,444,89910);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,1,1,7,3,SYSDATE -56,75000,35090000);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,2,2,8,1,SYSDATE -60,25000,6350000);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,8,2,8,1,SYSDATE -64,25000,3000000);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,8,2,8,1,SYSDATE -70,1543,185468.6);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,7,2,8,1,SYSDATE -75,8523,3272832);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,7,2,4,1,SYSDATE -81,45600,18652680);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,2,2,3,1,SYSDATE -84,2342,519104.3);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,1,1,2,3,SYSDATE -89,20000,9965800);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,3,1,4,3,SYSDATE -95,4223,33338);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,1,1,5,3,SYSDATE -98,150,73950);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,7,1,6,1,SYSDATE -102,854,326236.54);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,3,1,5,3,SYSDATE -105,5000,46550);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,1,1,6,3,SYSDATE -110,3000,1552920);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,8,1,5,1,SYSDATE -113,10,1200);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,5,1,6,2,SYSDATE -119,6049,324226.4);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,1,3,9,3,SYSDATE -123,1000,551270);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,1,1,6,3,SYSDATE -126,1250,692500);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,4,1,1,3,SYSDATE -130,344,27864);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,1,1,1,3,SYSDATE -132,10230,5657190);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,1,3,3,3,SYSDATE -133,500,290000);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,2,1,3,1,SYSDATE -137,444,189910);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,1,1,1,3,SYSDATE -140,75000,43390000);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,2,2,2,1,SYSDATE -141,24000,4950000);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,8,2,2,1,SYSDATE -144,25000,3203750);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,8,2,3,1,SYSDATE -147,1543,198468.6);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,7,3,5,1,SYSDATE -151,8523,3772832);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,7,3,6,1,SYSDATE -154,45600,19352680);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,2,2,5,1,SYSDATE -158,2342,644980);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,2,4,10,1,SYSDATE -161,50000,10116000);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,8,2,4,1,SYSDATE -165,25000,3235000);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,8,4,10,1,SYSDATE -168,1543,198468.6);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,7,2,5,1,SYSDATE -171,8523,3572832);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,7,5,11,1,SYSDATE -172,45600,18696680);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,2,1,4,1,SYSDATE -175,2342,449980);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,1,3,3,3,SYSDATE -178,3452,2063913.6);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,3,4,3,3,SYSDATE -180,84260,1047116.8);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,1,1,7,3,SYSDATE -183,256,153672.3);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,3,2,8,3,SYSDATE -186,9531,116863.7);
INSERT INTO trades (trade_id,share_id,broker_id,user_id,stock_ex_id,transaction_time,share_amount,price_total) VALUES (trades_id_seq.nextval,1,5,12,3,SYSDATE -190,1024,636309.1);

INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1483056000000,440.9);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1482969600000,443.3);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1482883200000,444.6);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1482796800000,457.2);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1482710400000,457.2);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1482451200000,457.2);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1482364800000,450.9);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1482278400000,450.4);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1482192000000,452.4);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1482105600000,454.7);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1481846400000,459.8);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1481760000000,450.7);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1481673600000,433.9);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1481587200000,446.5);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1481500800000,432.5);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1481241600000,441.4);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1481155200000,448.5);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1481068800000,434.7);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1480982400000,414.9);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1480896000000,414.0);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1480636800000,416.0);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1480550400000,422.4);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1480464000000,433.5);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1480377600000,444.5);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1480291200000,436.9);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1480032000000,440.1);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1479945600000,440.5);
INSERT INTO shares_prices (share_id,time_start,price) VALUES (1,1479859200000,450.6);