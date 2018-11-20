from flask import Flask, render_template, request
from flask_uploads import UploadSet, configure_uploads, IMAGES
from time import gmtime, strftime

from find_food import *
from database_controller import *


app = Flask(__name__)

photos = UploadSet('photos', IMAGES)

app.config['UPLOADED_PHOTOS_DEST'] = 'files'
configure_uploads(app, photos)

@app.route('/', methods=['GET'])
def index():
	return render_template('upload.html')

@app.route('/upload', methods=['POST'])
def upload():
#	filename = photos.save(request.files['photo'])
	img = request.get_data()
	current_time = gmtime()
	image_name = strftime("%Y-%m-%d-%H-%M-%S", current_time)
	f = open('/home/ubuntu/builds/testUpload/files/' + image_name,'w')
	f.write(img)
	f.close()
	print('Finished writing file')
	#return ai('/home/ubuntu/builds/testUpload/files/' + filename)
	result = ai('/home/ubuntu/builds/testUpload/files/' + image_name)
	addEntry(str(result[0,0]), str(result[0,1]), image_name, strftime("%Y-%m-%d %H:%M:%S", current_time))
	return str(result[0,0]) + ' ' + str(result[0,1])
	

if __name__ == '__main__':
	app.run(host='0.0.0.0', port='1030', debug=True)
