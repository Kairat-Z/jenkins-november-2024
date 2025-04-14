properties([
    parameters([
        string(description: 'ENTER IP', name: 'IP', trim: true)
    ])
])

node {
    stage("Hello") {
        withCredentials([sshUserPrivateKey(credentialsId: 'master-creds', keyFileVariable: 'SSH_PRIVATE_KEY', usernameVariable: 'SSH_USER')]) {
            sh "ssh -o StrictHostKeyChecking=no -i ${SSH_PRIVATE_KEY} ${SSH_USER}@${params.IP} sudo apt update"
        }
    }
}
