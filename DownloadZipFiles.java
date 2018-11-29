// Needed to have access to (external storage)(If needed); this is a request for a runtime permission

int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 100;
if (Build.VERSION.SDK_INT > 22) {
      ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
}



// The actual download code

StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
StrictMode.setThreadPolicy(policy);
File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/b.zip");
int count;
URL url;
try {
     int page_number = 2;
     url = new URL("http://18.224.124.230:1030/getFiles/" + page_number);
     URLConnection connection = url.openConnection();
     connection.connect();
     int LengthOfFile = connection.getContentLength();
     long total = 0;
     InputStream input = new BufferedInputStream(url.openStream());
     OutputStream output = new FileOutputStream(f);
     byte data[] = new byte[1024];
     while ((count = input.read(data)) != -1) {
          total += count;
          output.write(data, 0, count);
          }
     output.flush();
     output.close();
     input.close();
} catch (MalformedURLException e) {
     e.printStackTrace();
} catch (IOException e) {
     e.printStackTrace();
}
    
