provider "aws" {
region = var.region
}


resource "aws_instance" "web" {
ami = var.ami
instance_type = "t2.micro"
availability_zone = var.az
}

variable region {}

variable ami {}

variable az {}
