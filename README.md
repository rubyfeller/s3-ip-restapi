# s3-ip-restapi
Rest API S3-IP

Details of this application can be found in my [portfolio](https://github.com/rubyfeller/s3-portfolio/blob/main/portfolio/portfolio.md#webapplicatie).

## Deployment instructions
Pull the Docker image:
````
docker pull ghcr.io/rubyfeller/s3-ip-restapi:master
````

Note: master tag will pull the latest version. It's also possible to use older versions with the SHA as a tag.

Run the multi-container application using **Docker compose**:
````
docker-compose up
````

Or manage settings manually by running:
````
docker container run --network docker-mysql --name restapi-jdbc-container -p 8080:8080 -d ghcr.io/rubyfeller/s3-ip-restapi
````

The application will automaticly run (Rest API: port 8080, MySQL database: port 3306).

### Issues

If you get the following error:
```
Error response from daemon: Head "https://ghcr.io/v2/rubyfeller/s3-ip-restapi/manifests/master": denied: denied
```

Use the following command, in order to remove stored credentials:
````
docker logout ghcr.io
````

This seems to be a bug in GHCR public images/packages.
