INSERT IGNORE INTO users (id, email, password) VALUES (1, "teste@email.com", "12345678")
INSERT IGNORE INTO listas (id, idUser, title, description) VALUES (1, 1, "Lista de teste", "Primeira lista")
INSERT IGNORE INTO listas (id, idUser, title, description) VALUES (2, 1, "Lista de teste 2", "Segunda lista")
INSERT IGNORE INTO listas (id, idUser, title, description) VALUES (3, 1, "Lista de teste 3", "Terceira lista")
INSERT IGNORE INTO listas (id, idUser, title, description) VALUES (4, 1, "Lista de teste 4", "Quarta lista")
INSERT IGNORE INTO tasks (id, idUser, title, description, status, dtLimit, dtComplete, idLista) VALUES (1, 1, "Fazer Compras", "Ir ao mercado mais longe", 0, null, null, null)
INSERT IGNORE INTO tasks (id, idUser, title, description, status, dtLimit, dtComplete, idLista) VALUES (2, 1, "Comprar Roupas", "Ir ao shopping", 1, "2022-10-31 10:00", null, 1)
INSERT IGNORE INTO tasks (id, idUser, title, description, status, dtLimit, dtComplete, idLista) VALUES (3, 1, "Trabalhar", "Ir ao trabalho", 1, "2022-10-27 11:00", null, null)
INSERT IGNORE INTO tasks (id, idUser, title, description, status, dtLimit, dtComplete, idLista) VALUES (4, 1, "Fazer academia", "Ir a academia", 0, "2022-10-20 18:00", null, 2)
INSERT IGNORE INTO tags (id, name, color, userId) VALUES (1, "Importante", "2c02e8", 1)
INSERT IGNORE INTO tags (id, name, color, userId) VALUES (2, "Urgente", "d00ad3", 1)
INSERT IGNORE INTO tags (id, name, color, userId) VALUES (3, "Suavidade", "09e24e", 1)
INSERT IGNORE INTO tags (id, name, color, userId) VALUES (4, "Ferrou", "e80206", 1)