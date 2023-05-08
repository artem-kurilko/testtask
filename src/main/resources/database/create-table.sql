DROP TABLE IF EXISTS RATES;
CREATE TABLE MONO_RATES (
                      symbol VARCHAR(50) PRIMARY KEY,
                      price FLOAT NOT NULL,
                      date DATE NOT NULL,
                      bankName VARCHAR(50) NOT NULL
);