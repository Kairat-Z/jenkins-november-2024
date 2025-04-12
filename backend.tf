terraform {
   backend "s3" {
     bucket = "kaizen-kairat"
     key = "terraform.tfstate"
     region = "us-east-2"
   }
}
