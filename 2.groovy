template = '''
apiVersion: v1
kind: Pod
metadata:
  labels:
    run: terraform
  name: terraform
spec:
  containers:
  - command:
    - sleep
    - "3600"
    image: hashicorp/terraform
    name: terraform
'''


tfvars = """
region="${params.region}"
ami="${params.ami}"
az="${params.az}"
"""

properties([
    parameters([
        choice(choices: ['apply', 'destroy'], description: 'Select action', name: 'action'),
        choice(choices: ['us-east-1', 'us-east-2', 'us-west-1', 'us-west-2'], description: 'Enter region', name: 'region'),
        string(description: 'Enter AZ', name: 'az', trim: true),
        string(description: 'Enter AMI ID', name: 'ami', trim: true)
        ])
])


podTemplate(cloud: 'kubernetes', label: 'terraform', yaml: template) {
    node ("terraform") {
        container ("terraform") {
    stage ("Checkout SCM") {
        git branch: 'main', url: 'https://github.com/Kairat-Z/jenkins-november-2024.git'
    }
    withCredentials([usernamePassword(credentialsId: 'aws-creds', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')]) {

    stage ("Terraform Init") {
        sh "terraform init -backend-config=key=${params.region}/${params.az}/terraform.tfstate"
    }


    if (params.action == "apply") {
    stage ("Terraform Apply") {
        writeFile file: 'kaizen.tfvars', text: tfvars
        sh "terraform apply -var-file kaizen.tfvars -auto-approve"
    }
    }
    else {
    stage ("Terraform Destroy") {
        writeFile file: 'kaizen.tfvars', text: tfvars
        sh "terraform destroy -var-file kaizen.tfvars -auto-approve"
    }
    }
}
}
}
}
