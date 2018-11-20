import sqlite3
connection = sqlite3.connect("/home/ubuntu/builds/testUpload/sqlDatabase.db")
cursor = connection.cursor()

command = """
CREATE TABLE picture (
uniqueId INTEGER PRIMARY KEY,
confidence1 VARCHAR(11),
confidence2 VARCHAR(11),
picturePath VARCHAR(24),
date VARCHAR(19));"""

cursor.execute(command)
connection.commit()
connection.close()
