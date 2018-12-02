import sqlite3
import zipfile
from shutil import copyfile
from shutil import make_archive
import os

def getLastEntry():
	connection = sqlite3.connect("/home/ubuntu/builds/testUpload/sqlDatabase.db")
	cursor = connection.cursor()
	cursor.execute("SELECT max(uniqueId) FROM picture")
	max = cursor.fetchone()[0]
	connection.commit()
	connection.close()
	return max - 1

def addEntry(conf1, conf2, path, current_date):
	connection = sqlite3.connect("/home/ubuntu/builds/testUpload/sqlDatabase.db")
	cursor = connection.cursor()
	
	#command = "INSERT INTO picture (uniqueId, confidence1, confidence2, picturePath, date) VALUES (NULL, " + conf1 + ", " + conf2 + ", " + path + ", " + current_date + ");"
	params = (conf1, conf2, path, current_date)
	cursor.execute("INSERT INTO picture VALUES (NULL, ?, ?, ?, ?)", params)
	
	
	#cursor.execute(command)
	connection.commit()
	connection.close()

def getFileList(page):
	connection = sqlite3.connect("/home/ubuntu/builds/testUpload/sqlDatabase.db")
	cursor = connection.cursor()
	end = (page * 10)
	beginning = end - 9
	
	cursor.execute("SELECT confidence1,confidence2,picturePath FROM picture WHERE uniqueId BETWEEN {b} AND {e}".\
		format(b=beginning, e=end)) 
	result = cursor.fetchall() 
	
	# Make confidences.txt file and select pictures that are to be sent
	confidenceFile = "/home/ubuntu/builds/testUpload/files_to_send/confidences.txt"
	f = open(confidenceFile, "a")
	for r in result:
		f.write(r[2] + "," + r[0] + "," + r[1] + "\n")
		
		copyfile("/home/ubuntu/builds/testUpload/files/" + r[2], "/home/ubuntu/builds/testUpload/files_to_send/" + r[2])

	# Remove previous zip file
	if os.path.exists("/home/ubuntu/builds/testUpload/zipFiles/tmp.zip"):
		os.remove("/home/ubuntu/builds/testUpload/zipFiles/tmp.zip")
	# Put files in zip file
	make_archive("/home/ubuntu/builds/testUpload/zipFiles/tmp", 'zip', "/home/ubuntu/builds/testUpload/files_to_send/")
	
	# Cleanup: Delete files made earlier
	for r in result:
		os.remove("/home/ubuntu/builds/testUpload/files_to_send/" + r[2])
	os.remove("/home/ubuntu/builds/testUpload/files_to_send/confidences.txt")
	
	connection.commit()
	connection.close()

def getPicture(page):
	max = getLastEntry()
	if page > max:
		return 1
	connection = sqlite3.connect("/home/ubuntu/builds/testUpload/sqlDatabase.db")
	cursor = connection.cursor()
	cursor.execute("SELECT picturePath FROM picture")
	result = cursor.fetchall()[page]
	copyfile("/home/ubuntu/builds/testUpload/files/" + result[0], "/home/ubuntu/builds/testUpload/files_to_send/pictureFile")
	connection.commit()
	connection.close()
	return 0

def getConfidence(c):
	max = getLastEntry()
	if c > max:
		return "error"
	connection = sqlite3.connect("/home/ubuntu/builds/testUpload/sqlDatabase.db")
	cursor = connection.cursor()
	
	cursor.execute("SELECT confidence1 FROM picture")
	result = cursor.fetchall()[c]
	sconf = ""
	#for r in result:
	sconf = str(result[0])
	cursor.execute("SELECT confidence2 FROM picture")
	result = cursor.fetchall()[c]
	sconf = sconf + " " + str(result[0])
	connection.commit()
	connection.close()
	return sconf
