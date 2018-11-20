from flask import Flask, render_template, request
from flask_uploads import UploadSet, configure_uploads, IMAGES

class uploadTest:

	app = Flask(__name__)

	photos = UploadSet('photos', IMAGES)

	app.config['UPLOADED_PHOTOS_DEST'] = 'files'
	configure_uploads(app, photos)

	@app.route('/', methods=['GET'])
	def index():
		return render_template('upload.html')

	@app.route('/upload', methods=['POST'])
	def upload():
		filename = photos.save(request.files['photo'])
		return filename
	
	if __name__ == '__main__':
		app.run(host='0.0.0.0', port='1030', debug=True)
