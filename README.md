# spring-boot-image-gallery-app

### Things todo list

1. Clone this repository: `git clone https://github.com/hendisantika/spring-boot-image-gallery-app.git`
2. Navigate to the folder: `cd spring-boot-image-gallery-app`
3. Make sure your MySQL is UP & RUNNING
4. Change your MySQL credentials in application.properties file
5. Run the application: `mvn clean spring-boot:run`
6. Open your favorite browser: http://localhost:8080

### Image Screenshot

Home Page

![Home Page](img/home.png "Home Page")

Details Image

![Details Image](img/details.png "Details Image")

**log transfer from filebeat to elasticsearch via logstash**

filebeat.exe -e -c filebeat.yml

logstash.bat -f ./config/logstash-sample.conf --config.reload.automatic

# Read input from filebeat by listening to port 5044 on which filebeat will send the data

input {
beats {
#type => "test"
port => "5044"
}
}

output {

stdout {
codec => rubydebug
}

# Sending properly parsed log events to elasticsearch

elasticsearch {
hosts => ["https://localhost:9200/"]
    index => "logfromfilebeat-%{+YYYY.MM.dd}"
user => "elastic"
password => "NlfGhKDDy=36FpvM*o0l"
ssl => true
ssl_certificate_verification => false
}
}

filebeat.inputs:

- type: filestream
  id: my-filestream-id
  paths:
    - c:\Tools\logs\*.log

output:
logstash:
hosts: ["localhost:5044"]
