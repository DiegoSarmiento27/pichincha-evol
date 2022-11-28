INSERT INTO type_currency (name, code, value_currency, created_at, update_at) VALUES ('Sol Peruano', 'PEN', 1.00, LOCALTIMESTAMP(3), NULL);
INSERT INTO type_currency (name, code, value_currency, created_at, update_at) VALUES ('Euro', 'EUR', 4.01, LOCALTIMESTAMP(3), NULL);
INSERT INTO type_currency (name, code, value_currency, created_at, update_at) VALUES ('Dólar estadounidense', 'USD', 3.85, LOCALTIMESTAMP(3), NULL);
INSERT INTO type_currency (name, code, value_currency, created_at, update_at) VALUES ('Peso chileno', 'CLP', 0.0042, LOCALTIMESTAMP(3), NULL);

INSERT INTO audit (user_name, id_currency, uri, method, change_value, change_at) VALUES ('Admin', 1, 'sql_execute', 'sql', 'TypeCurrency [id=1, name=Sol Peruano, code=PEN, valueCurrency=1.00]', LOCALTIMESTAMP(3));
INSERT INTO audit (user_name, id_currency, uri, method, change_value, change_at) VALUES ('Admin', 2, 'sql_execute', 'sql', 'TypeCurrency [id=2, name=Euro, code=EUR, valueCurrency=4.01]', LOCALTIMESTAMP(3));
INSERT INTO audit (user_name, id_currency, uri, method, change_value, change_at) VALUES ('Admin', 3, 'sql_execute', 'sql', 'TypeCurrency [id=3, name=Dólar estadounidense, code=USD, valueCurrency=3.85]', LOCALTIMESTAMP(3));
INSERT INTO audit (user_name, id_currency, uri, method, change_value, change_at) VALUES ('Admin', 4, 'sql_execute', 'sql', 'TypeCurrency [id=4, name=Peso chileno, code=CLP, valueCurrency=0.0042]', LOCALTIMESTAMP(3));