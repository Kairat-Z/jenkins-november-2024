template = '''
apiVersion: v1
kind: Pod
metadata:
  labels:
    run: docker
  name: docker
spec:
  volumes:
  - name: docker
    hostPath:
      path: /var/run/docker.sock
  containers:
  - command:
    - sleep
    - "3600"
    image: hashicorp/docker
    name: docker
    volumemounts:
    - name: docker
    - mountPath: /var/run/docker.sock
'''


podTemplate(cloud: 'kubernetes', label: 'docker', yaml: template) {
    node ("docker") {
        container ("docker") {
    stage ("Checkout SCM") {
        git branch: 'main', url: 'https://github.com/Kairat-Z/jenkins-november-2024.git'
    }
    stage ("Docker") {
        sh "docker version"
    }
        }
    }
}
