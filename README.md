# s3-ip-restapi
Rest API S3-IP

Details of this application can be found in my [portfolio](https://github.com/rubyfeller/s3-portfolio/blob/main/portfolio/portfolio.md#webapplicatie).

## Deployment instructions
Pull the Docker image
````
docker pull ghcr.io/rubyfeller/s3-ip-restapi:unique_tag
````

Run the application 
````
docker run --name s3-ip-restapi -p 80:8080 -d ghcr.io/rubyfeller/s3-ip-restapi:unique_tag
````