import sqlite3


def addEntry(conf1, conf2, path, current_date):
	connection = sqlite3.connect("/home/ubuntu/builds/testUpload/sqlDatabase.db")
	cursor = connection.cursor()
	
	#command = "INSERT INTO picture (uniqueId, confidence1, confidence2, picturePath, date) VALUES (NULL, " + conf1 + ", " + conf2 + ", " + path + ", " + current_date + ");"
	params = (conf1, conf2, path, current_date)
	cursor.execute("INSERT INTO picture VALUES (NULL, ?, ?, ?, ?)", params)
	
	
	#cursor.execute(command)
	connection.commit()
	connection.close()

