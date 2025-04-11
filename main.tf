provider "aws" {
region = "us-east-2"
}


resource "aws_instance" "web" {
ami = "ami-0100e595e1cc1ff7f"
instance_type = "t2.micro"
availability_zone = "us-east-2a"
}
