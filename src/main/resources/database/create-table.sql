DROP TABLE IF EXISTS RATES;
CREATE TABLE MONO_RATES (
                      City_code INT AUTO_INCREMENT  PRIMARY KEY,
                      city_name VARCHAR(50) NOT NULL,
                      city_pincode INT(8) NOT NULL
);

CREATE TABLE GOVBANK_RATES (
                            City_code INT AUTO_INCREMENT  PRIMARY KEY,
                            city_name VARCHAR(50) NOT NULL,
                            city_pincode INT(8) NOT NULL
);

CREATE TABLE PRIVAT_RATES (
                            City_code INT AUTO_INCREMENT  PRIMARY KEY,
                            city_name VARCHAR(50) NOT NULL,
                            city_pincode INT(8) NOT NULL
);